package example;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
public class ApiSteps {
    public static Response createNewCourier(Courier courier) {

        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }
    public static Response loginCourier(CourierLogin login) {
        return given()
                .header("Content-type", "application/json")
                .body(login)
                .when()
                .post("/api/v1/courier/login");
    }
    public static int getCourierIDbyLogin(String login, String password) {
        CourierLogin auth = new CourierLogin(login, password);
        return loginCourier(auth).then().extract().path("id");
    }
    public static void deleteCourierByID(int id) {
        given()
                .header("Content-type", "application/json")
                .body(id)
                .when()
                .post("/api/v1/courier/:id");
    }
}
