import example.Courier;
import example.CourierDataGenerator;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import example.ApiSteps;
import example.CourierLogin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import io.qameta.allure.junit4.DisplayName;

public class CourierLoginTest {
    private Courier courier;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        courier = CourierDataGenerator.getRandom();
        ApiSteps.createNewCourier(courier);
    }

    @Test
    @DisplayName("Авторизация с корректными данными")
    public void loginCourierSuccess() {
        CourierLogin login = new CourierLogin(courier.getLogin(), courier.getPassword() );
        Response response = ApiSteps.loginCourier(login);
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Авторизация без пароля")
    public void loginCourierNoPassword() {
        CourierLogin login = new CourierLogin(courier.getLogin(), "");
        Response response = ApiSteps.loginCourier(login);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
    @Test
    @DisplayName("Авторизация без логина")
    public void loginCourierNoLogin() {
        CourierLogin login = new CourierLogin("", courier.getPassword());
        Response response = ApiSteps.loginCourier(login);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Неправильно указан логин")
    public void loginCourierNonExistentUser() {
        CourierLogin login = new CourierLogin( "LOGIN_NON_EXISTENT", courier.getPassword());
        Response response = ApiSteps.loginCourier(login);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Неправильно указан пароль")
    public void loginCourierWrongPassword() {
        CourierLogin login = new CourierLogin(courier.getLogin(), "PASSWORD_NON_EXISTENT");
        Response response = ApiSteps.loginCourier(login);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @After
    public void cleanUp() {
        int id = ApiSteps.getCourierIDbyLogin(courier.getLogin(), courier.getPassword());
        ApiSteps.deleteCourierByID(id);
    }
}