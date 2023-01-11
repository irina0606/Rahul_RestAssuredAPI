package tests;

import files.Properties;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class Jira_E2E_Test {
    String commentBase = "Don`t trouble troubles until trouble troubles you";

    Properties properties = new Properties();

    @Test
    public void E2E_issues() {
        RestAssured.baseURI = properties.url;
        SessionFilter session = new SessionFilter();
        String respSession = properties.getRespSession(session);

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
                        .log().all().filter(session)
                        .when().post("rest/api/2/issue")
                        .then().log().all()
                        .assertThat().statusCode(201)
                        .extract().asString();

        JsonPath addIssue = new JsonPath(respAddIssue);
        String firstIssueId = addIssue.getString("id");
        System.out.println("+++++++++++++    " + firstIssueId);

        String respAddComment =
                given()
                        .header("Content-Type", "application/json")
                        .pathParam("issueId", firstIssueId)
                        .body("{\n" +
                                "    \"body\": \"" + commentBase + "\",\n" +
                                "    \"visibility\": {\n" +
                                "        \"type\": \"role\",\n" +
                                "        \"value\": \"Administrators\"\n" +
                                "    }\n" +
                                "}")
                        .log().all().filter(session)
                        .when().post("rest/api/2/issue/{issueId}/comment")
                        .then().log().all()
                        .assertThat().statusCode(201)
                        .extract().asString();

        JsonPath addComment = new JsonPath(respAddComment);
        String createdCommentId = addComment.getString("id");
        String commentBody = addComment.getString("body");

        String respAttachment =
                given()
                        .header("Content-Type", "multipart/form-data").header("X-Atlassian-Token", "no-check")
                        .pathParam("issueId", firstIssueId)
                        .multiPart("file", new File("jira.txt"))
                        .log().all().filter(session)
                        .when().post("rest/api/2/issue/{issueId}/attachments")
                        .then().log().all()
                        .assertThat().statusCode(403)
                        .extract().asString();

        String respIssueDetails =
                given()
                        .pathParam("issueId", firstIssueId)
                        .queryParam("fields", "comment")
                        .log().all().filter(session)
                        .when().get("rest/api/2/issue/{issueId}")
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().asString();

        JsonPath getIssueDetails = new JsonPath(respIssueDetails);
        int countComments = getIssueDetails.getInt("fields.comment.comments.size()");
        for (int i = 0; i < countComments; i++) {
            String responseCommentsId = getIssueDetails.get("fields.comment.comments[" + i + "].id");
            if (responseCommentsId.equalsIgnoreCase(createdCommentId)) {
                String body = getIssueDetails.get("fields.comment.comments[" + i + "].body").toString();
                System.out.println(" +++++++++++\n" + "   All Comments body:  " + body);
                Assert.assertEquals(body, commentBody);
            }
            System.out.println(" +++++++++++\n" + "   All Comments Id:  " + responseCommentsId);

        }

        System.out.println(" +++++++++++\n" + "   Get session:   " + respSession);
        System.out.println(" +++++++++++\n" + "   Add issue:   " + respAddIssue);
        System.out.println(" +++++++++++\n" + "   Add comment:   " + respAddComment);
        System.out.println(" +++++++++++\n" + "   Add attachment:   " + respAttachment);
        System.out.println(" +++++++++++\n" + "   Get Issue Details:   " + respIssueDetails);
        System.out.println(" +++++++++++\n" + "   Count Comments:   " + countComments);

        String respDeleteIssue =
                given()
                        .pathParam("issueId", firstIssueId)
                        .queryParam("fields", "comment")
                        .log().all().filter(session)
                        .when().delete("rest/api/2/issue/{issueId}")
                        .then().log().all()
                        .assertThat().statusCode(204)
                        .extract().asString();

        System.out.println("++++++++++++     " + respDeleteIssue.length());
        System.out.println("++++++++++++     " + respDeleteIssue);
    }
}
