import example.CourierDataGenerator;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import example.ApiSteps;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import io.qameta.allure.junit4.DisplayName;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = ApiSteps.baseURL;
        }


    @Test
    @DisplayName("Список заказов")
    public void ordersListRequestNoParametersSuccess() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .get("/api/v1/orders");

        response.then().assertThat().statusCode(200)
                .and()
                .body("orders", notNullValue());
    }
}