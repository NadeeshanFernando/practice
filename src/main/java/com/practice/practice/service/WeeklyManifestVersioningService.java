package com.practice.practice.service;

import com.practice.practice.model.entity.WeeklyCalculationStatus;
import com.practice.practice.model.entity.WeeklyManifestVersion;
import com.practice.practice.model.enums.CalculationStatusType;
import com.practice.practice.repo.WeeklyCalculationStatusRepository;
import com.practice.practice.repo.WeeklyManifestVersionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WeeklyManifestVersioningService {

    private final WeeklyCalculationStatusRepository statusRepo;
    private final WeeklyManifestVersionRepository versionRepo;

    public WeeklyManifestVersioningService(WeeklyCalculationStatusRepository statusRepo,
                                           WeeklyManifestVersionRepository versionRepo) {
        this.statusRepo = statusRepo;
        this.versionRepo = versionRepo;
    }

    @Transactional
    public String downloadWeeklyManifest(String siteId, int fy, int fw) {

        WeeklyCalculationStatus latestCompleted =
                statusRepo.findFirstBySiteIdAndFiscalYearAndFiscalWeekAndStatusOrderByEndTimeDesc(
                                siteId, fy, fw, CalculationStatusType.COMPLETED)
                        .orElseThrow(() -> new IllegalStateException("No COMPLETED calculation. Download not allowed."));

        WeeklyCalculationStatus latestStatus =
                statusRepo.findFirstBySiteIdAndFiscalYearAndFiscalWeekOrderByEndTimeDesc(siteId, fy, fw)
                        .orElseThrow(() -> new IllegalStateException("No status found."));

        boolean isApproved = latestStatus.getStatus() == CalculationStatusType.APPROVED;

        WeeklyManifestVersion weeklyManifestVersion = versionRepo.findForUpdate(siteId, fy, fw).orElse(null);

        if (weeklyManifestVersion == null) {
            weeklyManifestVersion = new WeeklyManifestVersion();
            weeklyManifestVersion.setSiteId(siteId);
            weeklyManifestVersion.setFiscalYear(fy);
            weeklyManifestVersion.setFiscalWeek(fw);
            weeklyManifestVersion.setDraftVersionNo(1);
            weeklyManifestVersion.setFinalVersionNo(0);
            weeklyManifestVersion.setLastCalcCompletedTime(latestCompleted.getEndTime());
        } else {
            if (latestCompleted.getEndTime() > safe(weeklyManifestVersion.getLastCalcCompletedTime())) {
                weeklyManifestVersion.setDraftVersionNo(weeklyManifestVersion.getDraftVersionNo() + 1);
                weeklyManifestVersion.setLastCalcCompletedTime(latestCompleted.getEndTime());
                weeklyManifestVersion.setDraftFileTimestamp(null);
                weeklyManifestVersion.setFinalFileTimestamp(null);
            }
        }

        if (!isApproved) {
            if (weeklyManifestVersion.getDraftFileTimestamp() == null) {
                weeklyManifestVersion.setDraftFileTimestamp(System.currentTimeMillis());
            }
            versionRepo.save(weeklyManifestVersion);
            return buildFileName(siteId, fy, fw, weeklyManifestVersion.getDraftFileTimestamp(), "DRAFTv" + weeklyManifestVersion.getDraftVersionNo());
        }

        // approved => final
        if (weeklyManifestVersion.getFinalFileTimestamp() == null) {
            weeklyManifestVersion.setFinalVersionNo(weeklyManifestVersion.getFinalVersionNo() + 1);
            weeklyManifestVersion.setFinalFileTimestamp(System.currentTimeMillis());
        }
        versionRepo.save(weeklyManifestVersion);
        return buildFileName(siteId, fy, fw, weeklyManifestVersion.getFinalFileTimestamp(), "FINALv" + weeklyManifestVersion.getFinalVersionNo());
    }

    private static long safe(Long x) { return x == null ? 0L : x; }

    private static String buildFileName(String siteId, int fy, int fw, long ts, String label) {
        return String.format("DIPFusionWeekly-%dw%02d-%s-%d-rpt-All-%s.xlsx", fy, fw, siteId, ts, label);
    }
}