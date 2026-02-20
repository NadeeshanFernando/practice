package com.practice.practice.repo;

import java.util.Optional;

import com.practice.practice.model.entity.WeeklyManifestVersion;
import com.practice.practice.model.entity.WeeklyManifestVersionId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface WeeklyManifestVersionRepository extends JpaRepository<WeeklyManifestVersion, WeeklyManifestVersionId> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    select v from WeeklyManifestVersion v
    where v.siteId = :siteId and v.fiscalYear = :fy and v.fiscalWeek = :fw
  """)
    Optional<WeeklyManifestVersion> findForUpdate(
            @Param("siteId") String siteId,
            @Param("fy") Integer fy,
            @Param("fw") Integer fw
    );
}
