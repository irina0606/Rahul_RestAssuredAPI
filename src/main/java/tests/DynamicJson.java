package tests;


import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle){
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String addResponse = given().header("Content-Type", "application/json")
                .body(payload.AddBook(isbn, aisle))
                .when().post("Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath json = ReUsableMethods.rawToJson(addResponse);
        String id = json.get("ID");
        String msg = json.get("Msg");
        System.out.println(id + " " + msg);

    }

    @DataProvider(name="BooksData")
    public Object[][] addData(){
        return new Object[][]{{"t", "11"}, {"t", "22"}, {"t", "33"}};
    }

    // delete to avoid the error

    @Test(dataProvider = "BooksData1")
    public void deleteBook(String ID) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String deleteResponse = given().header("Content-Type", "application/json")
                .body(payload.DeleteBook(ID))
                .when().delete("Library/DeleteBook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath json = ReUsableMethods.rawToJson(deleteResponse);
        String msg1 = json.get("msg");
        System.out.println(msg1);

    }

        @DataProvider(name="BooksData1")
    public Object[][] deleteData(){
        return new Object[][]{{"t11"}, {"t22"}, {"t33"}};
    }


}
