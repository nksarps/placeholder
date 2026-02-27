# API Testing Automation Project - Section Breakdown

## Project Overview
This project automates API testing for JSONPlaceholder (https://jsonplaceholder.typicode.com/) using REST Assured, JUnit 5, and Allure Reports. The project is already configured with Maven and all necessary dependencies are in place.

---

## Section 1: Create Test Configuration & Base Classes

### Objective
Set up a reusable configuration and base test class structure for all API tests.

### What You'll Learn
- How to create configuration classes for API endpoints
- How to set up base test classes that other tests extend
- How to configure REST Assured for all tests globally

### Prompt for GitHub Copilot

```
Create a configuration class and base test class for REST Assured API testing.

Requirements:
1. Create a new file: src/test/java/com/automation/api/config/ApiConfig.java
   - Define the base URI as a constant: "https://jsonplaceholder.typicode.com"
   - Create a method to get the base URI
   - Add methods for common headers (Content-Type: application/json)

2. Create a new file: src/test/java/com/automation/api/base/BaseTest.java
   - This class should be the parent class for all test classes
   - Initialize REST Assured's base path and base URI using the ApiConfig class
   - Add a @BeforeEach method that logs test execution details
   - Add a @AfterEach method for cleanup (can be empty for now)
   - Use JUnit 5 annotations

Target Framework: REST Assured with JUnit 5
```

---

## Section 2: Create Test Cases for GET Requests

### Objective
Write comprehensive test cases for GET requests to validate status codes, response bodies, and headers.

### What You'll Learn
- How to perform GET requests with REST Assured
- How to validate status codes and response headers
- How to extract and validate data from JSON responses
- How to validate response times

### Prompt for GitHub Copilot

```
Create comprehensive GET request test cases for JSONPlaceholder API.

Requirements:
1. Create a new file: src/test/java/com/automation/api/tests/GetRequestTests.java
   - Extend the BaseTest class
   - Add the following test cases using @Test annotation:
   
   a) testGetAllPosts()
      - GET request to /posts endpoint
      - Validate status code is 200
      - Validate response contains array of posts
      - Validate response time is less than 2000ms
   
   b) testGetSinglePost()
      - GET request to /posts/1 endpoint
      - Validate status code is 200
      - Validate response contains id, title, body, userId fields
      - Validate the returned id equals 1
   
   c) testGetPostsByUserId()
      - GET request to /posts?userId=1 endpoint
      - Validate status code is 200
      - Validate response contains multiple posts
      - Validate all posts have userId=1
   
   d) testGetInvalidPost()
      - GET request to /posts/99999 endpoint
      - Validate status code is 200 (JSONPlaceholder returns empty object)
      - Validate response is an empty object or has no body

   e) testValidateContentType()
      - GET request to /posts/1 endpoint
      - Validate Content-Type header is "application/json"
      - Validate charset is UTF-8 (if present)

Use REST Assured's fluent API for assertions. Use meaningful assertion messages.
```

---

## Section 3: Create Test Cases for POST Requests

### Objective
Write test cases for POST requests to validate data creation and response validation.

### What You'll Learn
- How to send POST requests with JSON request bodies
- How to use RequestBody or plain JSON in REST Assured
- How to validate created resource properties
- How to handle request/response logging

### Prompt for GitHub Copilot

```
Create comprehensive POST request test cases for JSONPlaceholder API.

Requirements:
1. Create a new file: src/test/java/com/automation/api/tests/PostRequestTests.java
   - Extend the BaseTest class
   - Add the following test cases using @Test annotation:
   
   a) testCreateNewPost()
      - Send POST request to /posts endpoint
      - Request body:
        {
          "title": "Test Post Title",
          "body": "This is a test post body",
          "userId": 1
        }
      - Validate status code is 201 (Created)
      - Validate response contains id field (JSONPlaceholder returns id: 101)
      - Validate response contains the same title and body
      - Validate response contains userId as sent
   
   b) testCreatePostWithValidation()
      - Send POST request to /posts with different data
      - Request body:
        {
          "title": "Automated Test Post",
          "body": "Created by REST Assured automation test",
          "userId": 2
        }
      - Validate status code is 201
      - Validate response id is an integer greater than 100
      - Validate title and body match what was sent
   
   c) testCreatePostInvalidData()
      - Send POST request to /posts with missing required fields
      - Request body:
        {
          "userId": 1
        }
      - Validate the request still goes through (JSONPlaceholder accepts it)
      - Validate status code is 201
      - Note the behavior for validation purposes

Use REST Assured's body() method to send JSON. Log request and response bodies.
```

---

## Section 4: Create Test Cases for PUT Requests

### Objective
Write test cases for PUT requests to validate data update and complete replacement.

### What You'll Learn
- How to perform PUT requests (full resource replacement)
- How to validate updated resource properties
- How to verify data integrity after updates

### Prompt for GitHub Copilot

```
Create comprehensive PUT request test cases for JSONPlaceholder API.

Requirements:
1. Create a new file: src/test/java/com/automation/api/tests/PutRequestTests.java
   - Extend the BaseTest class
   - Add the following test cases using @Test annotation:
   
   a) testUpdateExistingPost()
      - Send PUT request to /posts/1 endpoint
      - Request body:
        {
          "id": 1,
          "title": "Updated Post Title",
          "body": "This is the updated body content",
          "userId": 1
        }
      - Validate status code is 200 (OK)
      - Validate response contains updated title and body
      - Validate response contains the same userId and id
   
   b) testUpdatePostWithPartialData()
      - Send PUT request to /posts/2 endpoint
      - Request body:
        {
          "title": "Only Title Updated",
          "body": "Only Body Updated",
          "userId": 1
        }
      - Validate status code is 200
      - Validate title and body are updated
      - Validate response structure

   c) testUpdateNonExistentPost()
      - Send PUT request to /posts/99999 endpoint
      - Request body with valid data
      - Validate status code is 200 (JSONPlaceholder's behavior)
      - Validate response contains the data sent

Use REST Assured's put() method. Validate that the response reflects all updates.
```

---

## Section 5: Create Test Cases for DELETE Requests

### Objective
Write test cases for DELETE requests to validate resource deletion.

### What You'll Learn
- How to perform DELETE requests
- How to validate successful deletion
- How to verify deletion with subsequent GET requests

### Prompt for GitHub Copilot

```
Create comprehensive DELETE request test cases for JSONPlaceholder API.

Requirements:
1. Create a new file: src/test/java/com/automation/api/tests/DeleteRequestTests.java
   - Extend the BaseTest class
   - Add the following test cases using @Test annotation:
   
   a) testDeleteExistingPost()
      - Send DELETE request to /posts/1 endpoint
      - Validate status code is 200 (OK)
      - Validate response is an empty object {} or null
   
   b) testDeletePostAndVerify()
      - Send DELETE request to /posts/2 endpoint
      - Validate status code is 200
      - Send a GET request to /posts/2 to verify deletion
      - Validate GET returns empty response or appropriate status
   
   c) testDeleteNonExistentPost()
      - Send DELETE request to /posts/99999 endpoint
      - Validate status code is 200 (JSONPlaceholder still returns success)
      - Validate response structure
   
   d) testDeleteWithoutId()
      - Send DELETE request to /posts/ endpoint (without ID)
      - Capture the status code and response
      - Document the behavior

Use REST Assured's delete() method. Verify that deletions are reflected in subsequent requests.
```

---

## Section 6: Create JSON Schema Validation Tests

### Objective
Write test cases that validate API responses against JSON schemas.

### What You'll Learn
- How to create JSON schema files
- How to validate REST API responses using JSON schema validator
- How to ensure API responses follow expected structure

### Prompt for GitHub Copilot

```
Create JSON schema validation tests for JSONPlaceholder API.

Requirements:
1. Create JSON schema files in src/test/resources/:
   
   a) src/test/resources/schemas/post_schema.json
      - Define a JSON schema that represents a Post object from JSONPlaceholder
      - Include properties: id (integer), title (string), body (string), userId (integer)
      - Mark all fields as required
      - Set appropriate type constraints

2. Create a new file: src/test/java/com/automation/api/tests/SchemaValidationTests.java
   - Extend the BaseTest class
   - Add the following test cases using @Test annotation:
   
   a) testPostResponseSchemaValidation()
      - GET request to /posts/1 endpoint
      - Validate response against post_schema.json
      - Assert status code is 200
      - Use io.restassured.module.jsv.JsonSchemaValidator
   
   b) testAllPostsResponseSchemaValidation()
      - GET request to /posts endpoint (returns array)
      - Create a schema for an array of posts or validate each item
      - Validate all items match the post schema
   
   c) testPostCreationSchemaValidation()
      - POST request to /posts with valid data
      - Validate response against post_schema.json
      - Assert status code is 201

Use REST Assured's JsonSchemaValidator. Import from io.rest-assured.module.jsv.
```

---

## Section 7: Create Helper/Utility Classes

### Objective
Create utility classes to reduce code duplication and improve maintainability.

### What You'll Learn
- How to create reusable request builders
- How to create response validators
- How to follow DRY principle in test automation

### Prompt for GitHub Copilot

```
Create helper and utility classes for REST Assured API testing.

Requirements:
1. Create a new file: src/test/java/com/automation/api/utils/RequestBuilder.java
   - Create methods to build common request bodies:
     a) createPostRequestBody(String title, String body, int userId) - returns String (JSON)
     b) createUpdatePostRequestBody(int id, String title, String body, int userId) - returns String
   - These methods should return valid JSON strings
   - Add a method to create a generic JSON body from key-value pairs

2. Create a new file: src/test/java/com/automation/api/utils/ResponseValidator.java
   - Create helper methods for common validations:
     a) validateStatusCode(Response response, int expectedCode)
     b) validateContentType(Response response, String expectedType)
     c) validateResponseTime(Response response, long maxTime)
     d) validateJsonPath(Response response, String jsonPath, Object expectedValue)
   - Each method should provide clear error messages on failure

3. Create a new file: src/test/java/com/automation/api/utils/TestDataProvider.java
   - Create static methods to provide test data:
     a) getValidPostData() - returns a map with valid post data
     b) getValidUserData() - returns a map with user data
     c) getInvalidPostData() - returns a map with invalid post data

Ensure these utility classes reduce code duplication across test classes.
```

---

## Section 8: Configure and Generate Allure Reports

### Objective
Configure Allure reports for test execution and generate detailed test reports.

### What You'll Learn
- How to add Allure annotations to tests
- How to run tests and generate Allure reports
- How to interpret and access Allure reports

### Prompt for GitHub Copilot

```
Configure Allure Reports for test execution and add annotations.

Requirements:
1. Add Allure annotations to all test classes:
   - Add @DisplayName to each test class with a meaningful description
   - Add @Description to each test method with details about what it tests
   - Add @Severity with appropriate level (CRITICAL, MAJOR, MINOR, TRIVIAL)
   - Add @Tag for categorization (e.g., "GET", "POST", "DELETE", "Schema")
   - For complex tests, add @Step for each major action

Example structure for GetRequestTests.java:
@DisplayName("GET Request Tests")
class GetRequestTests extends BaseTest {
    
    @Test
    @DisplayName("Verify GET /posts returns all posts")
    @Description("This test validates that GET /posts returns a list of all posts with status 200")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("GET")
    void testGetAllPosts() { ... }
}

2. Update pom.xml to include:
   - Add io.qameta.allure:allure-rest-assured dependency (already partially configured)
   - Ensure maven-surefire-plugin is configured with argLine for Allure

3. Create a new file: src/test/java/com/automation/api/utils/AllureSteps.java
   - Create @Step annotated methods for common test actions:
     a) @Step("Sending GET request to {endpoint}")
     b) @Step("Validating status code {expectedCode}")
     c) @Step("Validating response body contains {field}")

Import from io.qameta.allure package. This will improve test report readability.
```

---

## Section 9: Create an Integration Test (Workflow Test)

### Objective
Create an end-to-end workflow test that combines multiple API operations.

### What You'll Learn
- How to chain multiple API requests
- How to pass data between requests
- How to create realistic test workflows

### Prompt for GitHub Copilot

```
Create an integration/workflow test that performs multiple API operations in sequence.

Requirements:
1. Create a new file: src/test/java/com/automation/api/tests/WorkflowTests.java
   - Extend BaseTest class
   
2. Implement the following workflow test:
   
   a) testCreateUpdateDeleteWorkflow()
      - @DisplayName("Complete CRUD Workflow Test")
      - @Description("Test complete workflow: Create Post -> Retrieve Post -> Update Post -> Delete Post")
      - @Severity(CRITICAL), @Tag("Workflow")
      
      Workflow steps:
      1. Create a new post using POST /posts
         - Capture the response id (usually 101 for JSONPlaceholder)
         - Store the id for subsequent requests
      
      2. Retrieve the created post using GET /posts/{id}
         - Validate the retrieved data matches what was created
      
      3. Update the post using PUT /posts/{id}
         - Update title and body
         - Validate the update was successful
      
      4. Delete the post using DELETE /posts/{id}
         - Validate deletion was successful

This test demonstrates that all CRUD operations work together in a realistic workflow.
```

---

## Section 10: Execute Tests and Generate Reports

### Objective
Run all tests using Maven and generate comprehensive Allure reports.

### What You'll Learn
- How to execute tests using Maven commands
- How to interpret test execution output
- How to generate and view Allure reports
- How to identify and troubleshoot test failures

### Prompt for GitHub Copilot

```
Provide instructions for executing tests and generating Allure reports.

The user will:
1. Open terminal in the project root directory
2. Run: mvn clean test
   - This runs all test classes in src/test/java
   - Generates test results in target/surefire-reports
   - Generates Allure results in target/allure-results

3. Generate Allure report: mvn allure:report
   - This generates a visual HTML report
   - Report location: target/site/allure-report/index.html
   - Open the index.html in a browser to view the report

4. Run tests with specific tags (optional):
   mvn test -Dgroups=GET  (runs only GET tests)
   
5. View test results:
   - JUnit output: target/surefire-reports/
   - Allure HTML: target/site/allure-report/index.html

The report shows:
- Overall test statistics (passed, failed, skipped)
- Test categorization by tags
- Detailed test steps with Allure @Step annotations
- Screenshots/logs for failed tests (if applicable)
- Timeline of test execution
```

---

## Section 11: Add Parameterized Tests (Optional Enhancement)

### Objective
Create parameterized tests to test multiple scenarios with different inputs.

### What You'll Learn
- How to use @ParameterizedTest in JUnit 5
- How to use @CsvSource, @ValueSource, or @MethodSource
- How to reduce test code duplication for similar test cases

### Prompt for GitHub Copilot

```
Create parameterized tests for testing multiple scenarios with different inputs.

Requirements:
1. Create a new file: src/test/java/com/automation/api/tests/ParameterizedTests.java
   - Extend BaseTest class
   
2. Implement parameterized tests using @ParameterizedTest:
   
   a) testGetPostsByMultipleUserIds()
      - Use @ValueSource with values: 1, 2, 3, 4, 5
      - For each userId, perform GET /posts?userId={userId}
      - Validate status code is 200
      - Validate all returned posts have the specified userId
   
   b) testCreatePostsWithMultipleTitles()
      - Use @CsvSource with multiple title and body combinations
      - For each combination, POST to /posts
      - Validate response contains the correct title and body
      - Example CSV data:
        "First Post, First Body"
        "Second Post, Second Body"
        "Third Post, Third Body"

This approach tests multiple scenarios with minimal code duplication.
```

---

## Section 12: Create README and Documentation

### Objective
Create comprehensive documentation for the project.

### What You'll Learn
- How to document test automation projects
- How to create setup and execution instructions
- How to create a reference guide for the test suite

### Prompt for GitHub Copilot

```
Create a comprehensive README.md file for the API testing project.

Requirements:
1. Create: README.md in the project root

2. Include the following sections:
   a) Project Title and Description
      - Brief overview of what this project does
      - Link to JSONPlaceholder API
   
   b) Prerequisites
      - Java 21 or higher
      - Maven 3.6+
      - Internet connection (to access JSONPlaceholder API)
   
   c) Installation
      - Steps to clone/download the project
      - Steps to build: mvn clean install
   
   d) Project Structure
      - Explanation of src/test/java and src/test/resources
      - Description of each test class
      - Explanation of config and utils folders
   
   e) Running Tests
      - Command: mvn clean test
      - Command: mvn allure:report
      - How to view reports
   
   f) Test Coverage
      - List all test classes (GetRequestTests, PostRequestTests, etc.)
      - Count of total test cases
      - Description of what each test class covers
   
   g) Troubleshooting
      - Common issues and solutions
      - How to debug failing tests
   
   h) Future Enhancements
      - Performance testing
      - Load testing
      - Security testing

Make the documentation beginner-friendly and comprehensive.
```

---

## Execution Order

Follow this order to build the project incrementally:

1. **Section 1** → Create test configuration and base classes (foundation)
2. **Section 2** → Create GET request tests
3. **Section 3** → Create POST request tests
4. **Section 4** → Create PUT request tests
5. **Section 5** → Create DELETE request tests
6. **Section 6** → Create JSON schema validation tests
7. **Section 7** → Create helper/utility classes (refactor previous tests if needed)
8. **Section 8** → Add Allure annotations and configure reports
9. **Section 9** → Create integration/workflow tests
10. **Section 10** → Execute tests and generate reports
11. **Section 11** → Add parameterized tests (optional)
12. **Section 12** → Create README documentation

---

## Key Points to Remember

- **REST Assured API**: Use `given()...when()...then()` fluent API
- **JUnit 5**: Use `@Test`, `@BeforeEach`, `@AfterEach` annotations
- **Base URI**: https://jsonplaceholder.typicode.com
- **Response Format**: All responses are in JSON
- **Test Organization**: Keep tests organized in separate classes by HTTP method
- **Error Handling**: JSONPlaceholder is lenient; some invalid requests still return 200
- **Assertions**: Use meaningful assertion messages for easy debugging
- **Logging**: Enable request/response logging for troubleshooting

---

## Quick Reference: API Endpoints

- `GET /posts` - Get all posts
- `GET /posts/{id}` - Get post by ID
- `GET /posts?userId={userId}` - Get posts by user ID
- `POST /posts` - Create new post
- `PUT /posts/{id}` - Update post
- `DELETE /posts/{id}` - Delete post

JSONPlaceholder returns synthetic IDs for created resources (usually 101 and above).


