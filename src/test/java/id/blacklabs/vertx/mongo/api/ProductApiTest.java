package id.blacklabs.vertx.mongo.api;

import id.blacklabs.vertx.mongo.common.DummyData;
import id.blacklabs.vertx.mongo.common.StatusCode;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProductApiTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(ProductApiTest.class);

    @Test
    void save_product(Vertx vertx, VertxTestContext testContext) {
        WebClient webClient = WebClient.create(vertx);
        Checkpoint requestCheckpoint = testContext.checkpoint(1);

        webClient.post(8888, "localhost", "/products")
            .as(BodyCodec.json(Object.class))
            .sendJsonObject(JsonObject.mapFrom(new DummyData().product()), testContext.succeeding(response -> testContext.verify(() -> {
                    JsonObject object = JsonObject.mapFrom(response.body());

                    Assertions.assertEquals(object.getString("status"), StatusCode.SAVE_SUCCESS);
                    Assertions.assertEquals(response.statusCode(), 200);

                    requestCheckpoint.flag();
                }))
            );
    }

    @Test
    void find_product(Vertx vertx, VertxTestContext testContext) {
        WebClient webClient = WebClient.create(vertx);
        Checkpoint requestCheckpoint = testContext.checkpoint(1);

        webClient.get(8888, "localhost", "/products")
            .addQueryParam("offset", "0")
            .addQueryParam("limit", "10")
            .as(BodyCodec.json(Object.class))
            .send(testContext.succeeding(response -> testContext.verify(() -> {
                    JsonObject object = JsonObject.mapFrom(response.body());

                    Assertions.assertTrue(object.getJsonArray("data").size() > 0);
                    Assertions.assertEquals(response.statusCode(), 200);

                    requestCheckpoint.flag();
                }))
            );
    }

    @AfterAll
    void complete(VertxTestContext testContext) {
        testContext.completeNow();
    }

}
