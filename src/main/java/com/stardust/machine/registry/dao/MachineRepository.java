package com.stardust.machine.registry.dao;

import com.stardust.machine.registry.models.SellerMachine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends DataRepository<SellerMachine, Long> {
    SellerMachine getMachineBySn(String sn);

    @Query("SELECT machine FROM SellerMachine machine JOIN machine.registry registry WHERE registry.token = :token")
    SellerMachine getMachineByToken(@Param("token") String token);
}