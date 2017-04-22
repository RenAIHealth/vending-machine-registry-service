package com.stardust.machine.registry.dao;


import com.stardust.machine.registry.MachineRegistryServiceApplication;
import com.stardust.machine.registry.models.MachineRegistry;
import com.stardust.machine.registry.models.Package;
import com.stardust.machine.registry.models.SellerMachine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MachineRegistryServiceApplication.class)
@WebAppConfiguration
public class PackageRepositoryTest {

    @Autowired
    private PackageRepository subject;

    @Autowired
    private MachineRepository machineRepository;

    private static Package packageProduct;

    @Before
    public void setup() {
        if (packageProduct == null) {
            SellerMachine machine = new SellerMachine();
            machine.setProvince("foo_province");
            machine.setCity("foo_city");
            machine.setDistrict("foo_district");
            machine.setLocation("foo_location");
            machine.setSn("foo_package_machine_sn");
            machineRepository.save(machine);

            packageProduct = new Package();
            packageProduct.setMachine(machine);
            packageProduct.setSn("foo_sn");
            packageProduct.setName("foo_name");
            packageProduct.setStatus(Package.PackageStatus.AVAILABLE);
            subject.save(packageProduct);
        }
    }

    @Test
    public void packageRepositoryShouldFindPackageBySN() {
        Package exists = subject.getPackageBySn("foo_sn");
        Assert.assertNotNull(exists);
        Assert.assertEquals("foo_sn", exists.getSn());
    }
}
