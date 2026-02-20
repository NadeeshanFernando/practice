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
        WeeklyCalculationStatus s = new WeeklyCalculationStatus();
        s.setRequestId(UUID.randomUUID().toString());
        s.setSiteId(siteId);
        s.setFiscalYear(fy);
        s.setFiscalWeek(fw);
        s.setSavedBy(userId);

        s.setStatus(CalculationStatusType.PROCESSING);
        s.setStartTime(now());
        s.setEndTime(now());
        statusRepo.save(s);

        return s.getRequestId();
    }

    @Transactional
    public WeeklyCalculationStatus completeCalculation(String requestId, boolean success) {
        WeeklyCalculationStatus s = statusRepo.findByRequestId(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid requestId"));

        s.setStatus(success ? CalculationStatusType.COMPLETED : CalculationStatusType.FAILED);
        s.setEndTime(now());
        return statusRepo.save(s);
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
