package com.stardust.machine.registry.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stardust.machine.registry.MachineRegistryServiceApplication;
import com.stardust.machine.registry.models.*;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MachineRegistryServiceApplication.class)
@WebAppConfiguration
public class CouponControllerTest {

    @Autowired
    private WebApplicationContext context;

    private static Coupon coupon;

    private Activity activity;

    private Product product;

    @Before
    public void setup() throws ParseException {
        RestAssuredMockMvc.webAppContextSetup(context);
        activity = new Activity();
        activity.setExpireDate(new SimpleDateFormat("yyyy-MM-dd").parse("2999-12-31"));
        activity.setAuid("auid");
        activity.setName("activity");
        product = new Product();
        product.setName("product");
        product.setPrice(20.0);
        if (coupon == null) {
            coupon = new Coupon();
            coupon.setActivity(activity);
            coupon.setProduct(product);
            coupon.setCode("test_coupon");
            coupon.setDiscountAmount(10.0);
        }
    }

    @After
    public void teardown() {
        RestAssuredMockMvc.reset();
    }

    @Test
    public void couponServiceShouldRegisterCoupon() {
        String response = RestAssuredMockMvc
                .given()
                .contentType("application/json; charset=UTF-8")
                .body(this.coupon)
                .when()
                .post("/api/v101/coupons/o2o")
                .asString();

        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        Coupon coupon = gson.fromJson(response, Coupon.class);
        Assert.assertNotNull(coupon);
        Assert.assertTrue(coupon.getId() > 0);
    }

    @Test
    public void couponServiceShouldReturn400WhenDuplicatedRegistry() {
        RestAssuredMockMvc
                .given()
                .contentType("application/json; charset=UTF-8")
                .body(coupon)
                .when()
                .post("/api/v101/coupons/o2o")
                .then().assertThat().statusCode(400);
    }

    @Test
    public void couponServiceShouldReturn404WhenCouponNotExists() {
        RestAssuredMockMvc
                .given()
                .contentType("application/json; charset=UTF-8")
                .body(coupon)
                .when()
                .get("/api/v101/coupons/o2o/available/foo")
                .then().assertThat().statusCode(404);
    }
}
