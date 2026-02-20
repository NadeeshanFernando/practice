package com.practice.practice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "weekly_manifest_version")
@IdClass(WeeklyManifestVersionId.class)
@Getter @Setter
public class WeeklyManifestVersion {

    @Id
    @Column(name = "site_id", nullable = false, length = 45)
    private String siteId;

    @Id
    @Column(name = "fiscal_year", nullable = false)
    private Integer fiscalYear;

    @Id
    @Column(name = "fiscal_week", nullable = false)
    private Integer fiscalWeek;

    @Column(name = "draft_version_no", nullable = false)
    private Integer draftVersionNo = 0;

    @Column(name = "final_version_no", nullable = false)
    private Integer finalVersionNo = 0;

    // Used to detect a new completed recalculation cycle
    @Column(name = "last_calc_completed_time")
    private Long lastCalcCompletedTime;

    @Column(name = "draft_file_timestamp")
    private Long draftFileTimestamp;

    @Column(name = "final_file_timestamp")
    private Long finalFileTimestamp;
}
