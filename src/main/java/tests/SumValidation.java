package tests;

import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {

    @Test
    public void totalPriceAllCourses(){

        JsonPath json = new JsonPath(payload.CoursePrice());
        int countCourses = json.getInt("courses.size()");
        int priceForAllCourses = 0;
        int purchaseAmount = json.getInt("dashboard.purchaseAmount");


        for(int i=0; i<countCourses; i++){
            int price = json.getInt("courses["+i+"].price");
            int copies = json.getInt("courses["+i+"].copies");
            int eachCoursePrice = price * copies;
            System.out.println("Total price for course "+i+" is " + eachCoursePrice);
            priceForAllCourses = priceForAllCourses +eachCoursePrice;
        }
        System.out.println("Sum for all courses is " + priceForAllCourses);
        System.out.println("Purchase Amount is " + purchaseAmount);
        Assert.assertEquals(priceForAllCourses, purchaseAmount);
    }
}
