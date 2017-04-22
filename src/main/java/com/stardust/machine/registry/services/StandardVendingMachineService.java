package com.stardust.machine.registry.services;


import com.stardust.machine.registry.dao.MachineRepository;
import com.stardust.machine.registry.dao.PackageRepository;
import com.stardust.machine.registry.exceptions.InvalidSNException;
import com.stardust.machine.registry.exceptions.RecordNotFoundException;
import com.stardust.machine.registry.models.MachineRegistry;
import com.stardust.machine.registry.models.Package;
import com.stardust.machine.registry.models.SellerMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class StandardVendingMachineService implements VendingMachineService {

    @Autowired
    MachineRepository machineRepository;

    @Autowired
    PackageRepository packageRepository;

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public MachineRegistry registerMachine(SellerMachine machine) {
        SellerMachine exists = machineRepository.getMachineBySn(machine.getSn());
        if (exists != null
                || machine.getSn() == null
                || machine.getSn().isEmpty()) {
            throw new InvalidSNException(machine);
        }

        MachineRegistry registry = new MachineRegistry();
        registry.setToken(generateToken());
        registry.setMachine(machine);
        registry.setStatus(MachineRegistry.MachineRegistryStatus.ACTIVATED);
        registry.setRegisterTime(Calendar.getInstance().getTime());

        machine.setRegistry(registry);
        machineRepository.save(machine);

        return registry;
    }

    @Override
    public boolean validateMachineToken(String machineSN, String token) {
        SellerMachine exists = machineRepository.getMachineByToken(token);
        if (exists != null
                && exists.getSn().equals(machineSN)) {
            return true;
        }
        return false;
    }

    @Override
    public Package registerPackage(String machineSN, Package packageProduct) {
        SellerMachine machine = machineRepository.getMachineBySn(machineSN);
        if (machine != null) {
            Package exists = packageRepository.getPackageBySn(packageProduct.getSn());
            if (exists != null
                    || packageProduct.getSn() == null
                    || packageProduct.getSn().isEmpty()) {
                throw new InvalidSNException(exists);
            } else {
                packageProduct.setMachine(machine);
                packageRepository.save(packageProduct);
            }
        } else {
            throw new RecordNotFoundException();
        }

        return packageProduct;
    }

    @Override
    public Package sellPackage(String packageSN) {
        return null;
    }

    @Override
    public Package withdrawPackage(String packageSN) {
        return null;
    }
}
