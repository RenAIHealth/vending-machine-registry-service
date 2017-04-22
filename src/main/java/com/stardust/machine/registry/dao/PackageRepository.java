package com.stardust.machine.registry.dao;

import com.stardust.machine.registry.models.Package;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends DataRepository<Package, Long> {
    Package getPackageBySn(String sn);

    @Query("SELECT package FROM Package package JOIN package.machine machine WHERE machine.sn = :machineSN and package.sn = :packageSN")
    Package getPackageBySn(@Param("machineSN") String machineSN, @Param("packageSN") String packageSN);
}