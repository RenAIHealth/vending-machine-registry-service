package com.stardust.machine.registry.controllers;

import com.stardust.machine.registry.dao.MachineRepository;
import com.stardust.machine.registry.models.SellerMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/machines"})
@EnableAutoConfiguration
public class VendingMachineController {
    @Autowired
    private MachineRepository repository;

    @RequestMapping(value = "/{machineSN}", method={RequestMethod.GET})
    public Object getMachine(@PathVariable String machineSN) {
        SellerMachine machine = new SellerMachine();
        machine.setProvince("1");
        machine.setCity("1");
        machine.setDistrict("1");
        machine.setSn("1");
        machine.setLocation("1");
        return repository.save(machine);
    }
}
