package tests;

import files.payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.nullValue;

public class Basics {
    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String postResponse = given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(payload.AddPlace())
        .when().post("/maps/api/place/add/json")
        .then().log().all()
        .assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.41 (Ubuntu)")
                .extract().response().asString();

        JsonPath json = new JsonPath(postResponse);
        String placeId = json.getString("place_id");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(placeId);

        String newAddress = "1515 Summer Walk, Poland";
        String putResponse = given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeId+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("/maps/api/place/update/json")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("msg", equalTo("Address successfully updated"))
                .extract().response().asString();

        JsonPath json2 = new JsonPath(putResponse);
        String msg = json2.getString("msg");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(msg);


        String getPlaceResponse = given().log().all()
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("/maps/api/place/get/json")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath json1 = ReUsableMethods.rawToJson(getPlaceResponse);
        String actualAddress = json1.getString("address");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(newAddress);
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress, newAddress);    // to use this assertion download testng jar from maven repo and in Project structure-Modules-Dependencies add this jar




    }
}
