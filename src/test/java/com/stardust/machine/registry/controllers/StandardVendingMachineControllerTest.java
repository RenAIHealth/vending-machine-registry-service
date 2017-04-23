package com.stardust.machine.registry.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stardust.machine.registry.MachineRegistryServiceApplication;
import com.stardust.machine.registry.models.MachineRegistry;
import com.stardust.machine.registry.models.Package;
import com.stardust.machine.registry.models.SellerMachine;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MachineRegistryServiceApplication.class)
@WebAppConfiguration
public class StandardVendingMachineControllerTest {

    @Autowired
    private WebApplicationContext context;

    private static SellerMachine machine;

    private static MachineRegistry registry;

    private static Package packageProduct;

    @Before
    public void setup() {
        RestAssuredMockMvc.webAppContextSetup(context);
        if (machine == null) {
            machine = new SellerMachine();
            machine.setSn("machine_sn");
            machine.setProvince("machine_province");
            machine.setCity("machine_city");
            machine.setDistrict("machine_district");
            machine.setLocation("machine_location");

            packageProduct = new Package();
            packageProduct.setSn("package_sn");
            packageProduct.setName("package_name");
        }
    }

    @After
    public void teardown() {
        RestAssuredMockMvc.reset();
    }

    @Test
    public void machineServiceShouldRegisterMachine() {
        String response = RestAssuredMockMvc
                .given()
                .contentType("application/json; charset=UTF-8")
                .body(machine)
                .when()
                .post("/api/machines")
                .asString();

        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        registry = gson.fromJson(response, MachineRegistry.class);
        Assert.assertNotNull(registry);
        Assert.assertNotNull(registry.getMachine());
        Assert.assertEquals(machine.getSn(), registry.getMachine().getSn());
        Assert.assertNotNull(registry.getToken());
    }

    @Test
    public void machineServiceShouldRegisterPackage() {
        String response = RestAssuredMockMvc
                .given()
                .contentType("application/json; charset=UTF-8")
                .body(packageProduct)
                .when()
                .post("/api/machines/machine_sn/packages/stock?token=" + registry.getToken())
                .asString();

        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        Package product = gson.fromJson(response, Package.class);
        Assert.assertNotNull(product);
        Assert.assertNotNull(product.getMachine());
        Assert.assertEquals("machine_sn", product.getMachine().getSn());
    }

    @Test
    public void machineServiceShouldReturn400WhenTokenInvalid() {
        RestAssuredMockMvc
                .given()
                .contentType("application/json; charset=UTF-8")
                .body(packageProduct)
                .when()
                .post("/api/machines/machine_sn_another/packages/stock?token=" + registry.getToken())
                .then().assertThat().statusCode(400);
    }

    @Test
    public void machineServiceShouldRegisterSoldPackage() {
        String response = RestAssuredMockMvc
                .given()
                .contentType("application/json; charset=UTF-8")
                .body(packageProduct)
                .when()
                .post("/api/machines/machine_sn/packages/sold?token=" + registry.getToken())
                .asString();

        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        Package product = gson.fromJson(response, Package.class);
        Assert.assertNotNull(product);
        Assert.assertEquals(Package.PackageStatus.SOLD, product.getStatus());
    }
}
