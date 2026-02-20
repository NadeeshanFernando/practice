package com.practice.practice.repo;

import java.util.Optional;
import java.util.UUID;

import com.practice.practice.model.entity.WeeklyCalculationStatus;
import com.practice.practice.model.enums.CalculationStatusType;
import org.springframework.data.jpa.repository.*;

public interface WeeklyCalculationStatusRepository extends JpaRepository<WeeklyCalculationStatus, UUID> {

    Optional<WeeklyCalculationStatus> findByRequestId(String requestId);

    // latest COMPLETED (single row)
    Optional<WeeklyCalculationStatus>
    findFirstBySiteIdAndFiscalYearAndFiscalWeekAndStatusOrderByEndTimeDesc(
            String siteId, Integer fy, Integer fw, CalculationStatusType status);

    // latest status overall (single row)
    Optional<WeeklyCalculationStatus>
    findFirstBySiteIdAndFiscalYearAndFiscalWeekOrderByEndTimeDesc(
            String siteId, Integer fy, Integer fw);
}