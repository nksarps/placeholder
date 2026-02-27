package com.automation.api.tests;

import com.automation.api.base.SetUp;
import com.automation.api.config.Endpoints;
import com.automation.api.config.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Todos API GET Tests")
public class TodosTests extends SetUp {

    @Test
    @DisplayName("GET /todos - should return all todos")
    public void testGetAllTodos() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.TODOS)
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", greaterThan(0));
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
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(TestData.DEFAULT_TODO_USER_ID)));
    }
}
