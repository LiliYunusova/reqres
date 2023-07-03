package tests;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import reqrest.DataList;
import reqrest.User;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;

public class ReqresTest {

    @Test
    public void postCreateUserTest() {
        User user = User.builder()
                .name("morpheus")
                .job("leader")
                .build();
        Response response = given()
                .body(user)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), HTTP_CREATED);
    }

    @Test
    public void getSingleUserTest() {
        Response response = given()
                .log().all()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void getSingleUserNotFoundTest() {
        Response response = given()
                .log().all()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_NOT_FOUND);
    }

    @Test
    public void getListResourceTest() {
        Response response = given()
                .log().all()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void getSingleResourceTest() {
        Response response = given()
                .log().all()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void getSingleResourceNotFoundTest() {
        Response response = given()
                .log().all()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_NOT_FOUND);
    }

    @Test
    public void putUpdateUserTest() {
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        Response response = given()
                .body(user)
                .log().all()
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void patchUpdateUserTest() {
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        Response response = given()
                .body(user)
                .log().all()
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void deleteUserTest() {
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        Response response = given()
                .body(user)
                .log().all()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_NO_CONTENT);
    }

    @Test
    public void postRegisterSuccessfulTest() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void postRegisterUnSuccessfulTest() {
        User user = User.builder()
                .email("sydney@fife")
                .build();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_BAD_REQUEST);
    }

    @Test
    public void postLoginSuccessfulTest() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void postLoginUnSuccessfulTest() {
        User user = User.builder()
                .email("peter@klaven")
                .build();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_BAD_REQUEST);
    }

    @Test
    public void getDelayedResponseTest() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void getUserFirstNameTest() {
        String body = given()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .extract().body().asString();

        DataList dataList = new Gson().fromJson(body, DataList.class);
        String firstname = dataList.getData().get(1).getFirstName();
        Assert.assertEquals(firstname, "Janet");
    }
}
