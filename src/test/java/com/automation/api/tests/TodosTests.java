package com.automation.api.tests;

import com.automation.api.base.SetUp;
import com.automation.api.config.Endpoints;
import com.automation.api.config.TestData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Todos API Tests")
public class TodosTests extends SetUp {

    @Test
    @DisplayName("GET /todos - should return all todos")
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
    @DisplayName("GET /todos/1 - should return single todo with expected fields")
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
    @DisplayName("GET /todos?userId=1 - should return todos for userId=1")
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
    @DisplayName("GET /todos/99999 - invalid todo should return empty object or 404")
    public void testGetInvalidTodo() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.todoById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();
        String body = resp.asString().trim();

        assertTrue(statusCode == 200 || statusCode == 404,
                "Expected status 200 or 404 for invalid todo, got: " + statusCode);

        if (statusCode == 200) {
            boolean isEmptyObject = "{}".equals(body) || body.isEmpty();
            assertTrue(isEmptyObject, "Expected empty object or empty body for non-existent todo, got: " + body);
        }
    }

    @Test
    @DisplayName("GET /todos/1 - validate Content-Type header and charset")
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
        assertTrue(contentType.toLowerCase().contains("application/json"), "Expected Content-Type to contain 'application/json' but was: " + contentType);
        if (contentType.toLowerCase().contains("charset")) {
            assertTrue(contentType.toLowerCase().contains("utf-8"), "Expected charset to be UTF-8 when present, but was: " + contentType);
        }
    }

    @Test
    @DisplayName("POST /todos - should create a new todo with status 201")
    public void testCreateTodo() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"userId\": " + TestData.DEFAULT_TODO_USER_ID + ",\n" +
                        "  \"title\": \"" + TestData.TODO_TITLE + "\",\n" +
                        "  \"completed\": " + TestData.TODO_COMPLETED + "\n" +
                        "}")
        .when()
                .post(Endpoints.TODOS)
        .then()
                .statusCode(201)
                .body("userId", equalTo(TestData.DEFAULT_TODO_USER_ID))
                .body("title", equalTo(TestData.TODO_TITLE))
                .body("completed", equalTo(TestData.TODO_COMPLETED))
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("PUT /todos/1 - should update an existing todo with status 200")
    public void testUpdateTodo() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"userId\": " + TestData.DEFAULT_TODO_USER_ID + ",\n" +
                        "  \"id\": " + TestData.DEFAULT_TODO_ID + ",\n" +
                        "  \"title\": \"" + TestData.UPDATED_TODO_TITLE + "\",\n" +
                        "  \"completed\": " + TestData.UPDATED_TODO_COMPLETED + "\n" +
                        "}")
        .when()
                .put(Endpoints.todoById(TestData.DEFAULT_TODO_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_TODO_ID))
                .body("userId", equalTo(TestData.DEFAULT_TODO_USER_ID))
                .body("title", equalTo(TestData.UPDATED_TODO_TITLE))
                .body("completed", equalTo(TestData.UPDATED_TODO_COMPLETED));
    }

    @Test
    @DisplayName("DELETE /todos/1 - should delete todo and return status 200 or 204")
    public void testDeleteTodo() {
        given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.todoById(TestData.DEFAULT_TODO_ID))
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }
}
