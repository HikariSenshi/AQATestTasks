package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TinyApiTests {

    RequestSpecification freeGeoIpRequest;

    @BeforeTest
    public void prepareRequest(){
        freeGeoIpRequest = RestAssured.given();
        freeGeoIpRequest
                .baseUri("http://api.ipstack.com")
                .header("Content-Type", "application/json")
                .param("access_key", "1302ba4c2857325e7bd753e1af3fd159");
    }
    @Test
    public void freeGeoIPTestPassing() {

        Response response = freeGeoIpRequest.get("213.109.232.193");
        Assert.assertEquals(response.getStatusCode(),200);
        double latitude = response.body().jsonPath().getDouble("latitude");
        double longitude = response.body().jsonPath().getDouble("longitude");
        Assert.assertTrue( 48.92 <= latitude && latitude <= 48.93);
        Assert.assertTrue( 24.70 <= longitude && longitude <= 24.71);

    }

    @Test
    public void freeGeoIPTestFailing() {

        Response response = freeGeoIpRequest.get("139.201.250.155");
        Assert.assertEquals(response.getStatusCode(),200);
        double latitude = response.body().jsonPath().getDouble("latitude");
        double longitude = response.body().jsonPath().getDouble("longitude");
        Assert.assertTrue( 48.92 <= latitude && latitude <= 48.93);
        Assert.assertTrue( 24.70 <= longitude && longitude <= 24.71);

    }
    @Test(enabled = false)
    public void loginPostTest() {
        //if need we can use https validation to avoid errors
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        //add JSON parameters
        JSONObject args = new JSONObject();
        args.put("login", "Some_User");
        args.put("password", "12345678");
        request.body(args.toJSONString());
        //check result
        System.out.println(request.post("https://test.teddyclub.shop/").getStatusCode());
        Assert.assertTrue(request.post("https://test.teddyclub.shop/")
                        .getStatusCode() < 299,
                "Server has return error code");


    }

}
