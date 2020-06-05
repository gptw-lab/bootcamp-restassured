package com.restassured.tests;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class JuiceShopTest {


    @BeforeClass
    public void setup(){
        RestAssured.baseURI = "http://juice-shop:3000";
    }

    @Test
    public void newUserCreation(){
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .when()
                .body("{\n" +
                        "    \"email\": \"" + email + "\",\n" +
                        "    \"password\": \"Password@123\",\n" +
                        "    \"passwordRepeat\": \"Password@123\",\n" +
                        "    \"securityQuestion\": {\n" +
                        "        \"id\": 1,\n" +
                        "        \"question\": \"Your eldest siblings middle name?\", \n" +
                        "        \"createdAt\": \"2020-06-03T14:06:01.000Z\",\n" +
                        "        \"updatedAt\": \"2020-06-03T14:06:01.000Z\"\n" +
                        "    },\n" +
                        "    \"securityAnswer\": \"asdf\"\n" +
                        "}").log().all()
                .post("/api/Users/")
                .then()
                .assertThat().statusCode(201)
                .extract().response();

        String resp = response.asString();

        JsonPath jsonPath = new JsonPath(resp);
        System.out.println(jsonPath.get("data.role").toString());
    }

    @Test
    public void search(){
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .when()
                .get("/api/Quantitys/")
                .then()
                .extract().response();

        String resp = response.asString();
        System.out.println(resp);

    }

}
