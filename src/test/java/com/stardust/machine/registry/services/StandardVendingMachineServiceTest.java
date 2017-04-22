package com.stardust.machine.registry.services;


import com.stardust.machine.registry.MachineRegistryServiceApplication;
import com.stardust.machine.registry.dao.MachineRepository;
import com.stardust.machine.registry.dao.PackageRepository;
import com.stardust.machine.registry.exceptions.InvalidSNException;
import com.stardust.machine.registry.exceptions.RecordNotFoundException;
import com.stardust.machine.registry.models.Package;
import com.stardust.machine.registry.models.SellerMachine;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MachineRegistryServiceApplication.class)
@WebAppConfiguration
public class StandardVendingMachineServiceTest {

    private VendingMachineService subject;

    private MachineRepository machineRepository;

    private PackageRepository packageRepository;

    @Before
    public void setup() {
        machineRepository = EasyMock.createMock(MachineRepository.class);
        packageRepository = EasyMock.createMock(PackageRepository.class);
        subject = new StandardVendingMachineService(machineRepository, packageRepository);
    }

    @Test
    public void testIfMachineServiceCanRegisterMachineWhenSNIsValid() {
        SellerMachine machine = new SellerMachine();
        machine.setSn("foo_sn");

        EasyMock.expect(machineRepository.getMachineBySn("foo_sn")).andReturn(null).once();
        EasyMock.expect(machineRepository.save(machine)).andReturn(machine).once();
        EasyMock.replay(machineRepository);

        subject.registerMachine(machine);

        Assert.assertNotNull(machine.getRegistry());
    }

    @Test(expected = InvalidSNException.class)
    public void testIfMachineServiceThrowExceptionMachineWhenSNIsInvalid() {
        SellerMachine machine = new SellerMachine();
        machine.setSn("foo_sn");

        EasyMock.expect(machineRepository.getMachineBySn("foo_sn")).andReturn(machine).once();
        EasyMock.replay(machineRepository);

        subject.registerMachine(machine);
    }

    @Test
    public void testIfMachineServiceValidateTokenReturnsTrueWhenTokenIsValid() {
        SellerMachine machine = new SellerMachine();
        machine.setSn("foo_sn");

        EasyMock.expect(machineRepository.getMachineByToken("foo_token")).andReturn(machine).once();
        EasyMock.replay(machineRepository);
        Assert.assertTrue(subject.validateMachineToken("foo_sn", "foo_token"));
    }

    @Test
    public void testIfMachineServiceValidateTokenReturnsFalseWhenTokenNotMatchSN() {
        SellerMachine machine = new SellerMachine();
        machine.setSn("foo_sn");

        EasyMock.expect(machineRepository.getMachineByToken("foo_token")).andReturn(machine).once();
        EasyMock.replay(machineRepository);
        Assert.assertFalse(subject.validateMachineToken("foo_sn_another", "foo_token"));
    }

    @Test
    public void testIfMachineServiceValidateTokenReturnsFalseWhenTokenIsInvalid() {
        SellerMachine machine = new SellerMachine();
        machine.setSn("foo_sn");

        EasyMock.expect(machineRepository.getMachineByToken("foo_token")).andReturn(null).once();
        EasyMock.replay(machineRepository);
        Assert.assertFalse(subject.validateMachineToken("foo_sn", "foo_token"));
    }

    @Test
    public void testIfMachineServicePackageCanRegisterWhenMachineSNIsValid() {
        SellerMachine machine = new SellerMachine();
        machine.setSn("foo_sn");
        Package packageProduct = new Package();
        packageProduct.setSn("foo_sn");


        EasyMock.expect(machineRepository.getMachineBySn("foo_sn")).andReturn(machine).once();
        EasyMock.expect(packageRepository.getPackageBySn("foo_sn")).andReturn(null).once();
        EasyMock.expect(packageRepository.save(packageProduct)).andReturn(packageProduct).once();
        EasyMock.replay(machineRepository, packageRepository);

        subject.registerPackage("foo_sn", packageProduct);
    }

    @Test(expected = RecordNotFoundException.class)
    public void testIfMachineServicePackageCanRegisterWhenMachineSNIsInvalid() {
        Package packageProduct = new Package();
        packageProduct.setSn("foo_sn");

        EasyMock.expect(machineRepository.getMachineBySn("foo_sn")).andReturn(null).once();
        EasyMock.replay(machineRepository);

        subject.registerPackage("foo_sn", packageProduct);
    }

    @Test(expected = InvalidSNException.class)
    public void testIfMachineServicePackageCanRegisterWhenPackageSNIsInvalid() {
        SellerMachine machine = new SellerMachine();
        machine.setSn("foo_sn");
        Package packageProduct = new Package();
        packageProduct.setSn("foo_sn");

        EasyMock.expect(machineRepository.getMachineBySn("foo_sn")).andReturn(machine).once();
        EasyMock.expect(packageRepository.getPackageBySn("foo_sn")).andReturn(packageProduct).once();
        EasyMock.replay(machineRepository, packageRepository);

        subject.registerPackage("foo_sn", packageProduct);
    }

    @Test
    public void testIfMachineServiceSellPackageCorrectly() {
        SellerMachine machine = new SellerMachine();
        machine.setSn("machine_sn");
        Package packageProduct = new Package();
        packageProduct.setSn("package_sn");
        packageProduct.setMachine(machine);

        EasyMock.expect(packageRepository.getPackageBySn("machine_sn", "package_sn")).andReturn(packageProduct).once();
        EasyMock.expect(packageRepository.save(packageProduct)).andReturn(packageProduct).once();
        EasyMock.replay(machineRepository, packageRepository);

        subject.sellPackage("machine_sn", "package_sn");
        Assert.assertEquals(Package.PackageStatus.SOLD, packageProduct.getStatus());
    }

    @Test(expected = RecordNotFoundException.class)
    public void testIfMachineServiceSellPackageFailedIfMachineSNInvalid() {
        EasyMock.expect(machineRepository.getMachineBySn("machine_sn")).andReturn(null).once();
        EasyMock.replay(machineRepository);
        subject.sellPackage("machine_sn", "package_sn");
    }

    @Test(expected = RecordNotFoundException.class)
    public void testIfMachineServiceSellPackageFailedIfPackageSNInvalid() {
        SellerMachine machine = new SellerMachine();
        machine.setSn("machine_sn");

        EasyMock.expect(packageRepository.getPackageBySn("machine_sn", "package_sn")).andReturn(null).once();
        EasyMock.replay(machineRepository, packageRepository);
        subject.sellPackage("machine_sn", "package_sn");
    }

    @Test
    public void testIfMachineServiceDeletePackageCorrectly() {
        SellerMachine machine = new SellerMachine();
        machine.setSn("machine_sn");
        Package packageProduct = new Package();
        packageProduct.setSn("package_sn");
        packageProduct.setMachine(machine);

        EasyMock.expect(machineRepository.getMachineBySn("machine_sn")).andReturn(machine).once();
        EasyMock.expect(packageRepository.getPackageBySn("package_sn")).andReturn(packageProduct).once();
        EasyMock.expectLastCall();
        packageRepository.delete(packageProduct);
        EasyMock.replay(machineRepository, packageRepository);

        subject.withdrawPackage("machine_sn", "package_sn");
    }

    @Test(expected = RecordNotFoundException.class)
    public void testIfMachineServiceDeletePackageFailedIfMachineSNInvalid() {
        EasyMock.expect(machineRepository.getMachineBySn("machine_sn")).andReturn(null).once();
        EasyMock.replay(machineRepository);
        subject.withdrawPackage("machine_sn", "package_sn");
    }

    @Test(expected = RecordNotFoundException.class)
    public void testIfMachineServiceDeletePackageFailedIfPackageSNInvalid() {
        SellerMachine machine = new SellerMachine();
        machine.setSn("machine_sn");

        EasyMock.expect(machineRepository.getMachineBySn("machine_sn")).andReturn(machine).once();
        EasyMock.expect(packageRepository.getPackageBySn("package_sn")).andReturn(null).once();
        EasyMock.replay(machineRepository, packageRepository);
        subject.withdrawPackage("machine_sn", "package_sn");
    }

    @Test(expected = RecordNotFoundException.class)
    public void testIfMachineServiceDeletePackageFailedIfPackageSNAndMachineSNNotMatch() {
        SellerMachine machine = new SellerMachine();
        machine.setSn("machine_sn");

        SellerMachine machine2 = new SellerMachine();
        machine2.setSn("machine_sn2");
        Package packageProduct = new Package();
        packageProduct.setSn("package_sn");
        packageProduct.setMachine(machine2);

        EasyMock.expect(machineRepository.getMachineBySn("machine_sn")).andReturn(machine).once();
        EasyMock.expect(packageRepository.getPackageBySn("package_sn")).andReturn(packageProduct).once();
        EasyMock.replay(machineRepository, packageRepository);
        subject.withdrawPackage("machine_sn", "package_sn");
    }
}
