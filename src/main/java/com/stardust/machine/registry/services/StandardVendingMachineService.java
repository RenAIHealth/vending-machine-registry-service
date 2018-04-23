package com.stardust.machine.registry.services;


import com.stardust.machine.registry.components.VendingMachineReceiptBuilder;
import com.stardust.machine.registry.dao.CouponRepository;
import com.stardust.machine.registry.dao.MachineRepository;
import com.stardust.machine.registry.dao.PackageRepository;
import com.stardust.machine.registry.dao.ReceiptRepository;
import com.stardust.machine.registry.exceptions.InvalidSNException;
import com.stardust.machine.registry.exceptions.InvalidStatusException;
import com.stardust.machine.registry.exceptions.RecordNotFoundException;
import com.stardust.machine.registry.models.*;
import com.stardust.machine.registry.models.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StandardVendingMachineService implements VendingMachineService {

    MachineRepository machineRepository;

    PackageRepository packageRepository;

    ReceiptRepository receiptRepository;

    CouponRepository couponRepository;

    VendingMachineReceiptBuilder receiptBuilder;

    @Autowired
    public StandardVendingMachineService(MachineRepository machineRepository,
                                         PackageRepository packageRepository,
                                         VendingMachineReceiptBuilder receiptBuilder,
                                         ReceiptRepository receiptRepository,
                                         CouponRepository couponRepository) {
        this.machineRepository = machineRepository;
        this.packageRepository = packageRepository;
        this.receiptBuilder = receiptBuilder;
        this.receiptRepository = receiptRepository;
        this.couponRepository = couponRepository;
    }

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
    @Transactional
    public Package sellPackage(String machineSN, String packageSN) {
        Package exists = packageRepository.getPackageBySn(machineSN, packageSN);
        if (exists != null) {
            if (!exists.getStatus().equals(Package.PackageStatus.AVAILABLE)) {
                throw new InvalidStatusException();
            }
            exists.setStatus(Package.PackageStatus.SOLD);
            exists.setSoldTime(Calendar.getInstance().getTime());
            packageRepository.save(exists);
            return exists;
        } else {
            throw new RecordNotFoundException();
        }
    }

    @Override
    @Transactional
    public Package withdrawPackage(String machineSN, String packageSN) {
        SellerMachine machine = machineRepository.getMachineBySn(machineSN);
        if (machine != null) {
            Package exists = packageRepository.getPackageBySn(packageSN);
            if (exists != null
                    && exists.getMachine().getSn().equals(machine.getSn())) {
                if (!exists.getStatus().equals(Package.PackageStatus.AVAILABLE)) {
                    throw new InvalidStatusException();
                }
                packageRepository.delete(exists);
                exists.setStatus(Package.PackageStatus.DELETED);
                return exists;
            } else {
                throw new RecordNotFoundException();
            }
        } else {
            throw new RecordNotFoundException();
        }
    }

    @Override
    @Transactional
    public Receipt proceedOrder(VendorMachineOrder order) {
        Receipt receipt = this.receiptBuilder.createFromOrder(order);
        if (order.getCouponCode() != null) {
            Coupon coupon = couponRepository.getCouponByCode(order.getCouponCode());
            coupon.getActivity().setUsed(coupon.getActivity().getUsed() + 1);
            if (coupon.getActivity().getUsed() >= coupon.getActivity().getMaxUsed()) {
                coupon.setStatus(Coupon.CouponStatus.OUT_OF_USAGE);
            }
            couponRepository.save(coupon);
        }
        receiptRepository.save(receipt);
        return receipt;
    }
}
