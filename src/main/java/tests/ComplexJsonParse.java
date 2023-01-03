package tests;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

    public static void main(String[] args) {

        JsonPath json  = new JsonPath(payload.CoursePrice());
        System.out.println(payload.CoursePrice());

// Print No of courses
        System.out.println(">>>>>>>>> Print No of courses: ");
        int countCourses = json.getInt("courses.size()");       // count items inside the array
        System.out.println(countCourses);

// Print Purchase Amount
        System.out.println(">>>>>>>>> Print Purchase Amount: ");
        int purchaseAmount = json.getInt(("dashboard.purchaseAmount"));
        System.out.println(purchaseAmount);

//Print Title of the first course
        System.out.println(">>>>>>>>> Print Title of the first course: ");
        String courseTitle = json.get("courses[0].title");
        System.out.println(courseTitle);

// Print All course titles and their respective Prices
        System.out.println(">>>>>>>>> Print All course titles and their respective Prices: ");
        for(int i=0; i<countCourses; i++){
            String courseTitles = json.get("courses["+i+"].title");     //["+i+"] is used inside the string to be recognized
            String coursePrices = json.get("courses["+i+"].price").toString();      // price is int, so we convert toString()
            System.out.println(courseTitles +": "+ coursePrices);
        }
// Print no of copies sold by RPA Course
        System.out.println(">>>>>>>>> Print no of copies sold by RPA Course: ");
        for(int i=0; i<countCourses; i++) {
            String courseTitles = json.get("courses["+i+"].title");
            if(courseTitles.equalsIgnoreCase("RPA")){
                int copies = json.get("courses["+i+"].copies");
                System.out.println(copies);
                break;
            }
        }
    }
}
