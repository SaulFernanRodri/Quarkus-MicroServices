package example.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@QuarkusTest
class IntegrationTest {

    @Test
    void testGetAllSoldiers() {
        RestAssured.given()
                .queryParam("page", 0)
                .when()
                .get("/api/v1/soldiers")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("$.size()", is(2))
                .body("[0].id", is(1))
                .body("[0].name", is("John Doe"))
                .body("[0].type", is("Infantry"))
                .body("[0].quantity", is(50));
    }

    @Test
    void testGetSoldierById_found() {
        RestAssured.given()
                .pathParam("id", 1)
                .when()
                .get("/api/v1/soldiers/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", is(1))
                .body("name", is("John Doe"))
                .body("type", is("Infantry"))
                .body("quantity", is(50));
    }

    @Test
    void testGetSoldierById_notFound() {
        RestAssured.given()
                .pathParam("id", 999)
                .when()
                .get("/api/v1/soldiers/{id}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    void testCreateSoldier() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"John Doe\", \"type\": \"Infantry\", \"quantity\": 50}")
                .when()
                .post("/api/v1/soldiers")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .body("id", notNullValue())
                .body("name", is("John Doe"))
                .body("type", is("Infantry"))
                .body("quantity", is(50));
    }

    @Test
    void testDeleteSoldier() {
        RestAssured.given()
                .pathParam("id", 1)
                .when()
                .delete("/api/v1/soldiers/{id}")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        RestAssured.given()
                .pathParam("id", 1)
                .when()
                .get("/api/v1/soldiers/{id}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}