package com.stardust.machine.registry.services;


import com.stardust.machine.registry.MachineRegistryServiceApplication;
import com.stardust.machine.registry.dao.MachineRepository;
import com.stardust.machine.registry.models.MachineRegistry;
import com.stardust.machine.registry.models.SellerMachine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Calendar;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MachineRegistryServiceApplication.class)
@WebAppConfiguration
public class StandardVendingMachineServiceTest {

    @Autowired
    private VendingMachineService subject;

    @Before
    public void setup() {

    }

    @Test
    public void machineRepositoryShouldFindMachineBySN() {

    }
}
