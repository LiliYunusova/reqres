package tests;

import com.google.gson.Gson;
import headhunter.VacanciesList;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class HeadHunterTest {
    //https://api.hh.ru/
    @Test
    public void getVacanclesTest(){
        given()
                .when()
                .get("https://api.hh.ru/vacancies?text= QA automation")
                .then()
                .log().all()
                .statusCode(HTTP_OK);
    }
    @Test
    public void vacancyTest(){
        String body = given()
                .when()
                .get("https://api.hh.ru/vacancies?text=QA automation")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .extract().body().asString();

        VacanciesList vacanciesList = new Gson().fromJson(body,VacanciesList.class);
        int salaryTo = vacanciesList.getItems().get(3).getSalary().getTo();
        System.out.println(salaryTo);
    }

}
