package com.stardust.machine.registry.dao;


import com.stardust.machine.registry.MachineRegistryServiceApplication;
import com.stardust.machine.registry.models.MachineRegistry;
import com.stardust.machine.registry.models.SellerMachine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.Assert;

import java.util.Calendar;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MachineRegistryServiceApplication.class)
@WebAppConfiguration
public class MachineRepositoryTest {

    @Autowired
    private MachineRepository subject;

    private static SellerMachine machine;

    private static MachineRegistry registry;

    @Before
    public void setup() {
        if (machine == null) {
            registry = new MachineRegistry();
            registry.setRegisterTime(Calendar.getInstance().getTime());
            registry.setStatus(MachineRegistry.MachineRegistryStatus.ACTIVATED);
            registry.setToken("foo_token");

            machine = new SellerMachine();
            machine.setProvince("foo_province");
            machine.setCity("foo_city");
            machine.setDistrict("foo_district");
            machine.setLocation("foo_location");
            machine.setSn("foo_sn");
            machine.setRegistry(registry);
            registry.setMachine(machine);

            subject.save(machine);
        }
    }



    @Test
    public void machineRepositoryShouldFindMachineBySN() {
        SellerMachine exists = subject.getMachineBySn("foo_sn");
        Assert.assertNotNull(exists);
        Assert.assertEquals("foo_sn", exists.getSn());
        Assert.assertNotNull(exists.getRegistry());
    }

    @Test
    public void machineRepositoryShouldFindMachineByToken() {
        SellerMachine exists = subject.getMachineByToken("foo_token");
        Assert.assertNotNull(exists);
        Assert.assertEquals("foo_sn", exists.getSn());
        Assert.assertNotNull(exists.getRegistry());
    }
}
