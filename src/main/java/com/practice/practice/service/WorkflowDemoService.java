package com.practice.practice.service;

import java.util.UUID;

import com.practice.practice.model.entity.WeeklyCalculationStatus;
import com.practice.practice.model.enums.CalculationStatusType;
import com.practice.practice.repo.WeeklyCalculationStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkflowDemoService {

    private final WeeklyCalculationStatusRepository statusRepo;

    public WorkflowDemoService(WeeklyCalculationStatusRepository statusRepo) {
        this.statusRepo = statusRepo;
    }

    @Transactional
    public String calculate(String siteId, int fy, int fw, String userId) {
        WeeklyCalculationStatus weeklyCalculationStatus = new WeeklyCalculationStatus();
        weeklyCalculationStatus.setRequestId(UUID.randomUUID().toString());
        weeklyCalculationStatus.setSiteId(siteId);
        weeklyCalculationStatus.setFiscalYear(fy);
        weeklyCalculationStatus.setFiscalWeek(fw);
        weeklyCalculationStatus.setSavedBy(userId);

        weeklyCalculationStatus.setStatus(CalculationStatusType.PROCESSING);
        weeklyCalculationStatus.setStartTime(now());
        weeklyCalculationStatus.setEndTime(now());
        statusRepo.save(weeklyCalculationStatus);

        return weeklyCalculationStatus.getRequestId();
    }

    @Transactional
    public WeeklyCalculationStatus completeCalculation(String requestId, boolean success) {
        WeeklyCalculationStatus weeklyCalculationStatus = statusRepo.findByRequestId(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid requestId"));

        weeklyCalculationStatus.setStatus(success ? CalculationStatusType.COMPLETED : CalculationStatusType.FAILED);
        weeklyCalculationStatus.setEndTime(now());
        return statusRepo.save(weeklyCalculationStatus);
    }

    @Transactional
    public WeeklyCalculationStatus approve(String siteId, int fy, int fw, String userId) {
        WeeklyCalculationStatus approved = new WeeklyCalculationStatus();
        approved.setRequestId(UUID.randomUUID().toString()); // new record per approval
        approved.setSiteId(siteId);
        approved.setFiscalYear(fy);
        approved.setFiscalWeek(fw);
        approved.setSavedBy(userId);

        approved.setStatus(CalculationStatusType.APPROVED);
        approved.setStartTime(now());
        approved.setEndTime(now());
        return statusRepo.save(approved);
    }

    private static long now() { return System.currentTimeMillis(); }
}
