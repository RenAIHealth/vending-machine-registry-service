package com.stardust.machine.registry.dao;

import com.stardust.machine.registry.models.Package;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends DataRepository<Package, Long> {
    Package getPackageBySn(String sn);
}