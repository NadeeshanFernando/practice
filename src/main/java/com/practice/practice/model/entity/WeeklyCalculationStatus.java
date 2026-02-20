package com.practice.practice.model.entity;

import com.practice.practice.model.enums.CalculationStatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "weekly_calculation_status",
        indexes = {
                @Index(name = "idx_week_end_time", columnList = "site_id,fiscal_year,fiscal_week,end_time")
        }
)
@Getter @Setter
public class WeeklyCalculationStatus {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "request_id", nullable = false, length = 45)
    private String requestId;

    @Column(name = "site_id", nullable = false, length = 45)
    private String siteId;

    @Column(name = "fiscal_year", nullable = false)
    private Integer fiscalYear;

    @Column(name = "fiscal_week", nullable = false)
    private Integer fiscalWeek;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private CalculationStatusType status;

    @Column(name = "start_time")
    private Long startTime;

    @Column(name = "end_time")
    private Long endTime;

    @Column(name = "saved_by", length = 45)
    private String savedBy;
}
