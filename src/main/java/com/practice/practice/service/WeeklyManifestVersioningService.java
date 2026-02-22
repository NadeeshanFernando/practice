package com.practice.practice.service;

import com.practice.practice.exception.ResourceNotFoundException;
import com.practice.practice.model.entity.WeeklyCalculationStatus;
import com.practice.practice.model.entity.WeeklyManifestVersion;
import com.practice.practice.model.enums.CalculationStatusType;
import com.practice.practice.repo.WeeklyCalculationStatusRepository;
import com.practice.practice.repo.WeeklyManifestVersionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WeeklyManifestVersioningService {

    public static final String FINALV = "FINALv";
    public static final String DIPFUSION_WEEKLY_DW_02_D_S_D_RPT_ALL_S_XLSX = "DIPFusionWeekly-%dw%02d-%s-%d-rpt-All-%s.xlsx";
    public static final String DRAFTV = "DRAFTv";
    public static final String NO_COMPLETED_CALCULATION_DOWNLOAD_NOT_ALLOWED = "No COMPLETED calculation. Download not allowed.";
    public static final String NO_STATUS_FOUND = "No status found.";
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
                        .orElseThrow(() -> new ResourceNotFoundException(NO_COMPLETED_CALCULATION_DOWNLOAD_NOT_ALLOWED));

        WeeklyCalculationStatus latestStatus =
                statusRepo.findFirstBySiteIdAndFiscalYearAndFiscalWeekOrderByEndTimeDesc(siteId, fy, fw)
                        .orElseThrow(() -> new ResourceNotFoundException(NO_STATUS_FOUND));

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
            return buildFileName(siteId, fy, fw, weeklyManifestVersion.getDraftFileTimestamp(), DRAFTV + weeklyManifestVersion.getDraftVersionNo());
        }

        // approved => final
        if (weeklyManifestVersion.getFinalFileTimestamp() == null) {
            weeklyManifestVersion.setFinalVersionNo(weeklyManifestVersion.getFinalVersionNo() + 1);
            weeklyManifestVersion.setFinalFileTimestamp(System.currentTimeMillis());
        }
        versionRepo.save(weeklyManifestVersion);
        return buildFileName(siteId, fy, fw, weeklyManifestVersion.getFinalFileTimestamp(), FINALV + weeklyManifestVersion.getFinalVersionNo());
    }

    private static long safe(Long x) {
        return x == null ? 0L : x;
    }

    private static String buildFileName(String siteId, int fy, int fw, long ts, String label) {
        return String.format(DIPFUSION_WEEKLY_DW_02_D_S_D_RPT_ALL_S_XLSX, fy, fw, siteId, ts, label);
    }
}