import example.Courier;
import example.CourierDataGenerator;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import example.ApiSteps;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.CoreMatchers.is;
import io.qameta.allure.junit4.DisplayName;
public class CreateCourierTest {
    private Courier courier;

    @Before
    public void setUp() {
        RestAssured.baseURI = ApiSteps.baseURL;
        courier = CourierDataGenerator.getRandom();
    }

    @Test
    @DisplayName("Создание курьера с корректными данными")
    public void createCourierSuccess() {
        Response response = ApiSteps.createNewCourier(courier);
        response.then().assertThat().statusCode(201)
                .and()
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Создание курьера с одинаковым логином")
    public void createCourierDuplicate() {
        ApiSteps.createNewCourier(courier);
        Response response = ApiSteps.createNewCourier(courier);
        // Похоже в документации старые данные, при ошибке приходит сообщение: "Этот логин уже используется. Попробуйте другой."
        response.then().assertThat().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    public void createCourierNoLogin() {
        courier.setLogin(null);
        Response response = ApiSteps.createNewCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    public void createCourierNoPassword() {
        courier.setPassword(null);
        Response response = ApiSteps.createNewCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void cleanUp() {
        if (courier.getLogin() != null && courier.getPassword() != null) {
            int id = ApiSteps.getCourierIDbyLogin(courier.getLogin(), courier.getPassword());
            ApiSteps.deleteCourierByID(id);
        }
    }
}
