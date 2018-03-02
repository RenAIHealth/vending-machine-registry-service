package com.stardust.machine.registry.services;


import com.stardust.machine.registry.models.*;
import com.stardust.machine.registry.models.Package;

public interface VendingMachineService {
    MachineRegistry registerMachine(SellerMachine machine);

    boolean validateMachineToken(String machineSN, String token);

    Package registerPackage(String machineSN, Package packageProduct);

    Package sellPackage(String machineSN, String packageSN);

    Package withdrawPackage(String machineSN, String packageSN);

    Receipt proceedOrder(VendorMachineOrder order);
}
