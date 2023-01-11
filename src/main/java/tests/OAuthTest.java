package tests;

        import files.Properties;
        import io.restassured.parsing.Parser;
        import io.restassured.path.json.JsonPath;
        import org.openqa.selenium.By;
        import org.openqa.selenium.Keys;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.chrome.ChromeDriver;

        import java.util.concurrent.TimeUnit;

        import static io.restassured.RestAssured.given;

public class OAuthTest {
    public static  void main(String[] args) throws InterruptedException {

// TODO Auto-generated method stub
        String googleAccountUrl ="https://accounts.google.com/o/oauth2/v2/auth/identifier?access_type=offline&client_id=587594460880-u53ikl5ast2sup28098ofsm9iku8vvm6.apps.googleusercontent.com&code_challenge=K8-KOFTeI_PJjAvZp2ULAlmwKCL5Fo8PeSff9vBZ_Ek&code_challenge_method=S256&include_granted_scopes=true&prompt=select_account%20consent&redirect_uri=https%3A%2F%2Fsso.teachable.com%2Fidentity%2Fcallbacks%2Fgoogle%2Fcallback&response_type=code&scope=email%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile%20openid%20profile&state=eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJnb29nbGUiLCJpYXQiOjE2NzMzOTAyMTksImV4cCI6MTY3MzM5MjAxOSwianRpIjoiMjFjMGNlZGEtNmMwZC00MDI5LTg2OTktOGYzN2Y5ZTE2NTVjIiwiaXNzIjoic2tfeno4dHc2ZGciLCJzdWIiOiJmcDlMbnRnRHo2dDQ0Q1lmeGtJNTJuU0IzVnlOenlzUlBfSjRXaEVfZ2ZyZkxON0hsRzlTMG10YlA5dm85aGJDTHljY1dkbV9UTG9Sdks3MGZaR2ZrUSJ9.57huK1SafQunal3UQkFQ9UFql9y8d5VH9ZeHjbeKvXQ&service=lso&o2v=2&flowName=GeneralOAuthFlow";

        WebDriver driver =  new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(googleAccountUrl);

        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Properties.testEmail);
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Properties.testPW);
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);

        String url = driver.getCurrentUrl();
        String partialcode=url.split("code=")[1];
        String code=partialcode.split("&scope")[0];
        System.out.println(code);


//        String response =
//                given().urlEncodingEnabled(false)
//                        .queryParams("code",code)
//                        .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
//                        .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
//                        .queryParams("grant_type", "authorization_code")
//                        .queryParams("state", "verifyfjdss")
//                        .queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
//                        // .queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")
//                        .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
//                        .when().log().all()
//                        .post("https://www.googleapis.com/oauth2/v4/token").asString();
//
//// System.out.println(response);
//
//        JsonPath jsonPath = new JsonPath(response);
//        String accessToken = jsonPath.getString("access_token");
//        System.out.println(accessToken);
//        String r2=  given().contentType("application/json")
//                .queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
//                .when()
//                .get("https://rahulshettyacademy.com/getCourse.php")
//                .asString();
//        System.out.println(r2);
    }
}