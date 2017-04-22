package com.stardust.machine.registry.services;


import com.stardust.machine.registry.models.MachineRegistry;
import com.stardust.machine.registry.models.Package;
import com.stardust.machine.registry.models.SellerMachine;

public interface VendingMachineService {
    MachineRegistry registerMachine(SellerMachine machine);

    boolean validateMachineToken(String machineSN, String token);

    Package registerPackage(String machineSN, Package packageProduct);

    Package sellPackage(String machineSN, String packageSN);

    Package withdrawPackage(String machineSN, String packageSN);
}
