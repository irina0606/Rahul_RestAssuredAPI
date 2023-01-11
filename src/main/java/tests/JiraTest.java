package tests;

import files.Properties;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;


public class JiraTest {

    String createdIssueId = "";
    Properties properties = new Properties();

    @Test
    public void createIssue() {

        RestAssured.baseURI = properties.url;
        SessionFilter session = new SessionFilter();
        properties.getRespSession(session);

        String respAddIssue =
                given()
                        .header("Content-Type", "application/json")
                        .body("{\n" +
                                "    \"fields\": {\n" +
                                "    \"project\": {\n" +
                                "        \"key\": \"RES\"\n" +
                                "    },\n" +
                                "    \"summary\": \"Rest Assured Automation\",\n" +
                                "    \"issuetype\": {\n" +
                                "        \"description\": \"We will automate api\",\n" +
                                "        \"name\": \"Story\"\n" +
                                "    }\n" +
                                "    }\n" +
                                "}")
                        .filter(session)
                        .when().post("rest/api/2/issue")
                        .then()
                        .assertThat().statusCode(201)
                        .extract().asString();

        JsonPath addIssue = new JsonPath(respAddIssue);
        createdIssueId = addIssue.getString("id");
        System.out.println("+++++++++++++    " + createdIssueId);
    }

    @Test
    public void createFewComments() {

        RestAssured.baseURI = properties.url;
        SessionFilter session = new SessionFilter();
        properties.getRespSession(session);

        createIssue();

        List<String> comment = new ArrayList<>();
        comment.add("Jan");
        comment.add("Feb");
        comment.add("Mar");
        comment.add("Apr");
        comment.add("May");

        for (int i = 0; i < 5; i++) {

            String respAddFewComments =
                    given()
                            .header("Content-Type", "application/json")
                            .pathParam("issueId", createdIssueId)
                            .body("{\n" +
                                    "    \"body\": \"" + comment.get(i) + "\",\n" +
                                    "    \"visibility\": {\n" +
                                    "        \"type\": \"role\",\n" +
                                    "        \"value\": \"Administrators\"\n" +
                                    "    }\n" +
                                    "}")
                            .filter(session)
                            .when().post("rest/api/2/issue/{issueId}/comment")
                            .then()
                            .assertThat().statusCode(201)
                            .extract().asString();

            JsonPath json = new JsonPath(respAddFewComments);
            String getId = json.getString("id");
            System.out.println(getId);
        }
    }

    @Test
    public void getAllComments1() {
        SessionFilter session = new SessionFilter();
        properties.getRespSession(session);
        RestAssured.baseURI = properties.url;
        createFewComments();

        String respIssueDetails =
                given()
                        .pathParam("issueId", createdIssueId)
                        .queryParam("fields", "comment")
                        .log().all().filter(session)
                        .when().get("rest/api/2/issue/{issueId}")
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().asString();

        JsonPath getIssueComments = new JsonPath(respIssueDetails);
        int countComments = getIssueComments.getInt("fields.comment.comments.size()");
        System.out.println(" +++++++++++\n" + "   Issue ID:  " + createdIssueId);
        System.out.println(" +++++++++++\n" + "   Count comments:  " + countComments);

        List<String> commentIds = new ArrayList<>();
        List<String> commentBodies = new ArrayList<>();
        for (int i = 0; i < countComments; i++) {
            commentIds.add(getIssueComments.get("fields.comment.comments[" + i + "].id"));
            commentBodies.add(getIssueComments.get("fields.comment.comments[" + i + "].body").toString());
        }

        System.out.println(" +++++++++++\n" + "   All Comments body:  " + commentBodies);
        System.out.println(" +++++++++++\n" + "   All Comments Id:  " + commentIds);
    }


    @Test
    public void createFewIssues() {

        RestAssured.baseURI = properties.url;
        SessionFilter session = new SessionFilter();
        properties.getRespSession(session);

        for (int i = 0; i < 5; i++) {
            String respAddIssue =
                    given()
                            .header("Content-Type", "application/json")
                            .body("{\n" +
                                    "    \"fields\": {\n" +
                                    "    \"project\": {\n" +
                                    "        \"key\": \"RES\"\n" +
                                    "    },\n" +
                                    "    \"summary\": \"Rest Assured Automation\",\n" +
                                    "    \"issuetype\": {\n" +
                                    "        \"description\": \"We will automate api\",\n" +
                                    "        \"name\": \"Story\"\n" +
                                    "    }\n" +
                                    "    }\n" +
                                    "}")
                            .filter(session)
                            .when().post("rest/api/2/issue")
                            .then()
                            .assertThat().statusCode(201)
                            .extract().asString();

            JsonPath getAllIssues = new JsonPath(respAddIssue);
            String getIds = getAllIssues.getString("id");
            System.out.println(getIds);
        }
    }
}

