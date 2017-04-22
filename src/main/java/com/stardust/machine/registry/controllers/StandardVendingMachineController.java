package com.stardust.machine.registry.controllers;

import com.stardust.machine.registry.exceptions.InvalidTokenException;
import com.stardust.machine.registry.models.Package;
import com.stardust.machine.registry.models.SellerMachine;
import com.stardust.machine.registry.services.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/machines"})
@EnableAutoConfiguration
public class StandardVendingMachineController {
    @Autowired
    private VendingMachineService service;

    @RequestMapping(method={RequestMethod.POST})
    public Object registerMachine(@RequestBody SellerMachine machine) {
        return service.registerMachine(machine);
    }

    @RequestMapping(value = "/{machineSN}/packages/stock", method={RequestMethod.POST})
    public Object registerPackage(@PathVariable String machineSN,
                                  @RequestBody Package packageProduct,
                                  @RequestParam String token) {
        if (!service.validateMachineToken(machineSN, token)) {
            throw new InvalidTokenException(token, machineSN);
        }
        return service.registerPackage(machineSN, packageProduct);
    }

    @RequestMapping(value = "/{machineSN}/packages/sold", method={RequestMethod.POST})
    public Object sellPackage(@PathVariable String machineSN,
                              @RequestBody Package packageProduct,
                              @RequestParam String token) {
        if (!service.validateMachineToken(machineSN, token)) {
            throw new InvalidTokenException(token, machineSN);
        }
        return service.sellPackage(machineSN, packageProduct.getSn());
    }

    @RequestMapping(value = "/{machineSN}/packages/stock/{packageSN}", method={RequestMethod.DELETE})
    public Object withdrawPackage(@PathVariable String machineSN,
                              @PathVariable String packageSN,
                              @RequestParam String token) {
        if (!service.validateMachineToken(machineSN, token)) {
            throw new InvalidTokenException(token, machineSN);
        }
        return service.withdrawPackage(machineSN, packageSN);
    }
}
