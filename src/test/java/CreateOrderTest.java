import io.restassured.RestAssured;
import io.restassured.response.Response;
import example.NewOrder;
import example.ApiSteps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import io.qameta.allure.junit4.DisplayName;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    @Before
    public void setUp() {
        RestAssured.baseURI = ApiSteps.baseURL;
    }

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation,
                           String phone, int rentTime, String deliveryDate, String comment,
                           String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address =address;
        this.metroStation = metroStation;
        this.phone = phone;
        this. rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color =color;
    }

    @Parameterized.Parameters(name = "Данные для заказа. Тестовые данные: {0} {1} {2}")
    public static Object[][] getDataForm() {
        String[] oneColor = new String[] {"BLACK"};
        String[] twoColors = new String[] {"BLACK", "GREY"};
        String[] noColor = new String[] {};

        return new Object[][] {
                {"Сергей", "Денисов", "Улица Советская 3", "1", "88002353535", 3, "2023-07-22", "Да", oneColor},
                {"Игорь", "Штенге", "Улица Ленина 2", "2", "+72128506000", 2, "2023-07-23", "Меня люди ждут", twoColors},
                {"Раиса", "Сметанина", "Улица Коммунистическая 1", "3", "+77777777777", 1, "2023-07-24", "", noColor},
        };
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrderOneColorSuccess() {
        NewOrder newOrder = new NewOrder(firstName, lastName, address, metroStation, phone, rentTime,
                deliveryDate, comment, color);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(newOrder)
                        .when()
                        .post("/api/v1/orders");

        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }
}
