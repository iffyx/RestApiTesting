import com.jayway.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.jayway.restassured.RestAssured.given;

public class RestAssuredDemo {

    public String url = "https://api.trello.com/1";
    public String token = "239a8a927f019001fd11fe2f71649fa2cff4c1310960f168e012c40ee3238959";
    public String key = "095750e20d74c0b98c2f59ed8c3c188e";

    @Test
    public void displayBoard() {
        Response response = given()
                .baseUri(url)
                .queryParam("key", key)
                .queryParam("token", token).when()
                .get("/members/me/boards");

        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void addListToBoard() {
        Response responseAddBoard = addBoard();
        String boardId = responseAddBoard.jsonPath().getString("id");

        Response responseAddList = addList(boardId);
        String listId = responseAddList.jsonPath().getString("id");

        Response responseAddCard = addCard(listId);

        responseAddCard.then().statusCode(200);
        responseAddCard.prettyPrint();
    }

    private Response addBoard() {
        return given()
                .baseUri(url)
                .contentType("Application/Json")
                .queryParam("key", key)
                .queryParam("token", token).when()
                .queryParam("name", "BoardByRestAssured")
                .post("/boards");
    }

    private Response addList(String boardId) {
        return given()
                .baseUri(url)
                .contentType("Application/Json")
                .queryParam("key", key)
                .queryParam("token", token).when()
                .queryParam("name", "ListByRestAssured")
                .queryParam("idBoard", boardId)
                .post("/lists");
    }

    private Response addCard(String listId) {
        return given()
                .baseUri(url)
                .contentType("Application/Json")
                .queryParam("key", key)
                .queryParam("token", token).when()
                .queryParam("name", "CardByRestAssured")
                .queryParam("idList", listId)
                .post("/cards");
    }
}
