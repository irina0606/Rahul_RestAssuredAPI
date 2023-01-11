package files;

import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.given;

public class Properties {

    public static String url = "http://localhost:8080/";
    public static String jiraUsername = "irzhik1975";
    public static String jiraPW = "Jira1975";

    public static String testEmail = "ira.test1975@gmail.com";
    public static String testPW = "Project@Test";


    public String getRespSession(SessionFilter session) {

        String respSession = given().relaxedHTTPSValidation()           //  relaxedHTTPSValidation() allows avoid the problems with security using https protocol
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"username\": \""+ jiraUsername +"\",\n" +
                        "    \"password\": \""+ jiraPW +"\"\n" +
                        "}")
                .log().all().filter(session)
                .when().post("rest/auth/1/session")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().asString();
        return respSession;
    }
}
