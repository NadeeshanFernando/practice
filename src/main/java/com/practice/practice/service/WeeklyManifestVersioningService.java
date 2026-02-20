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

        WeeklyManifestVersion v = versionRepo.findForUpdate(siteId, fy, fw).orElse(null);

        if (v == null) {
            v = new WeeklyManifestVersion();
            v.setSiteId(siteId);
            v.setFiscalYear(fy);
            v.setFiscalWeek(fw);
            v.setDraftVersionNo(1);
            v.setFinalVersionNo(0);
            v.setLastCalcCompletedTime(latestCompleted.getEndTime());
        } else {
            if (latestCompleted.getEndTime() > safe(v.getLastCalcCompletedTime())) {
                v.setDraftVersionNo(v.getDraftVersionNo() + 1);
                v.setLastCalcCompletedTime(latestCompleted.getEndTime());
                v.setDraftFileTimestamp(null);
                v.setFinalFileTimestamp(null);
            }
        }

        if (!isApproved) {
            if (v.getDraftFileTimestamp() == null) {
                v.setDraftFileTimestamp(System.currentTimeMillis());
            }
            versionRepo.save(v);
            return buildFileName(siteId, fy, fw, v.getDraftFileTimestamp(), "DRAFTv" + v.getDraftVersionNo());
        }

        // approved => final
        if (v.getFinalFileTimestamp() == null) {
            v.setFinalVersionNo(v.getFinalVersionNo() + 1);
            v.setFinalFileTimestamp(System.currentTimeMillis());
        }
        versionRepo.save(v);
        return buildFileName(siteId, fy, fw, v.getFinalFileTimestamp(), "FINALv" + v.getFinalVersionNo());
    }

    private static long safe(Long x) { return x == null ? 0L : x; }

    private static String buildFileName(String siteId, int fy, int fw, long ts, String label) {
        return String.format("DIPFusionWeekly-%dw%02d-%s-%d-rpt-All-%s.xlsx", fy, fw, siteId, ts, label);
    }
}