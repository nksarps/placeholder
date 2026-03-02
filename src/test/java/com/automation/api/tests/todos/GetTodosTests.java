package com.automation.api.tests.todos;

import com.automation.api.base.SetUp;
import com.automation.api.config.ApiConfig;
import com.automation.api.utils.Endpoints;
import com.automation.api.resources.TestData;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GET Todos API Tests")
public class GetTodosTests extends SetUp {

    @Test
    @DisplayName("Validate API returns all todos")
    public void testGetAllTodos() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.TODOS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("Validate API returns all todos with headers validation")
    public void testGetAllTodosWithHeaders() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.TODOS)
        .then()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", notNullValue())
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("Validate API returns single todo with expected fields")
    public void testGetSingleTodo() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.todoById(TestData.DEFAULT_TODO_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_TODO_ID))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("completed", notNullValue());
    }

    @Test
    @DisplayName("Validate API returns todos for a specific user ID")
    public void testGetTodosByUserId() {
        given()
                .spec(requestSpec)
                .queryParam("userId", TestData.DEFAULT_TODO_USER_ID)
        .when()
                .get(Endpoints.TODOS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(TestData.DEFAULT_TODO_USER_ID)));
    }

    @Test
    @DisplayName("Validate API handles invalid todo requests gracefully")
    public void testGetInvalidTodo() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.todoById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid todo, got: " + statusCode);
    }

    @Test
    @DisplayName("Validate API Content-Type header and charset")
    public void testValidateContentType() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.todoById(TestData.DEFAULT_TODO_ID))
        .then()
                .statusCode(200)
                .extract()
                .response();

        String contentType = resp.getHeader("Content-Type");
        assertNotNull(contentType, "Content-Type header should be present");
        assertTrue(contentType.toLowerCase().contains(ApiConfig.getContentType()), "Expected Content-Type to contain 'application/json' but was: " + contentType);
        if (contentType.toLowerCase().contains("charset")) {
            assertTrue(contentType.toLowerCase().contains(ApiConfig.getCharset()), "Expected charset to be UTF-8 when present, but was: " + contentType);
        }
    }
}
