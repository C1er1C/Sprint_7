package example;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;
public class ApiSteps {
    public static String baseURL = "http://qa-scooter.praktikum-services.ru/";
    public static String courierPath = "/api/v1/courier";
    public static String courierLoginPath = "/api/v1/courier/login";
    public static String courierIdPath = "/api/v1/courier/:id";
    @Step("Создание курьера")
    public static Response createNewCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(courierPath);
    }
    @Step("Логин курьера")
    public static Response loginCourier(CourierLogin login) {
        return given()
                .header("Content-type", "application/json")
                .body(login)
                .when()
                .post(courierLoginPath);
    }
    @Step("Получить ID курьера")
    public static int getCourierIDbyLogin(String login, String password) {
        CourierLogin auth = new CourierLogin(login, password);
        return loginCourier(auth).then().extract().path("id");
    }
    @Step("Удалить курьера по ID")
    public static void deleteCourierByID(int id) {
        given()
                .header("Content-type", "application/json")
                .body(id)
                .when()
                .post(courierIdPath);
    }
}
