package tests;

import static io.restassured.RestAssured.given;


import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;


import io.restassured.RestAssured;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;


public class StaticJson {
@Test
    public void addBook() throws IOException {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String resp = given()
                .queryParam("key", "qaclick123")

                .header("Content-Type","application/json")

                .body(GenerateStringFromResource("C:\\Users\\iryna.kalynychenko\\Documents\\Learning\\RestAssured Udemy\\location.json"))
                .when().post("/maps/api/place/add/json")

                .then().assertThat().statusCode(200)//.body("scope", equalTo("APP")).header("server", "Apache/2.4.41 (Ubuntu)")

                .extract().response().asString();

        System.out.println(resp);
        JsonPath json = new JsonPath(resp);
        String placeId = json.getString("place_id");
        System.out.println(placeId);

    }

    public static String GenerateStringFromResource(String path) throws IOException {

        return new String(Files.readAllBytes(Paths.get(path)));
    }

}

