package com.practice.practice.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class WeeklyManifestVersionId implements Serializable {
    private String siteId;
    private Integer fiscalYear;
    private Integer fiscalWeek;

    public WeeklyManifestVersionId() {}

    public WeeklyManifestVersionId(String siteId, Integer fiscalYear, Integer fiscalWeek) {
        this.siteId = siteId;
        this.fiscalYear = fiscalYear;
        this.fiscalWeek = fiscalWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeeklyManifestVersionId that)) return false;
        return Objects.equals(siteId, that.siteId)
                && Objects.equals(fiscalYear, that.fiscalYear)
                && Objects.equals(fiscalWeek, that.fiscalWeek);
    }

    @Override
    public int hashCode() {
        return Objects.hash(siteId, fiscalYear, fiscalWeek);
    }
}
