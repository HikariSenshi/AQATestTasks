package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class ApiTest {

    @Test
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
