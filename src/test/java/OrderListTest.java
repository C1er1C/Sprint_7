import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import io.qameta.allure.junit4.DisplayName;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Список заказов")
    public void ordersListRequestNoParametersSuccess() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .get("/api/v1/orders");

        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}