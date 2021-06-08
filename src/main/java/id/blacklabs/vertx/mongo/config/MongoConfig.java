package id.blacklabs.vertx.mongo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.connection.SocketSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import id.blacklabs.vertx.mongo.common.CollectionName;
import id.blacklabs.vertx.mongo.context.ConfigContext;
import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.document.Sales;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.Shareable;
import lombok.Builder;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * @author krissadewo
 * @date 4/24/21 7:56 PM
 */
public class MongoConfig implements Shareable {

    private MongoDatabase database;

    @Builder
    private MongoConfig(Vertx vertx, JsonObject config) {
        buildClient(config);

        //register config to context
        ConfigContext configContext = new ConfigContext(vertx);
        configContext.putIfAbsent(MongoConfig.class, () -> this);
    }

    private void buildClient(JsonObject config) {
        PojoCodecProvider codecProvider = PojoCodecProvider.builder()
            .conventions(Arrays.asList(Conventions.ANNOTATION_CONVENTION, Conventions.OBJECT_ID_GENERATORS))
            .automatic(true)
            .build();

        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(codecProvider));

        MongoClient mongoClient = MongoClients.create(
            MongoClientSettings.builder()
                .applyToSocketSettings(builder -> {
                    builder.applySettings(SocketSettings.builder()
                        .readTimeout(2000, TimeUnit.MILLISECONDS)
                        .build());
                })
                .applyConnectionString(new ConnectionString(config.getString("mongo.connection.string")))
                .codecRegistry(codecRegistry)
                .build()
        );

        database = mongoClient.getDatabase(config.getString("mongo.database.name"));
        database.withCodecRegistry(codecRegistry);
    }

    public MongoCollection<Product> getProductCollection() {
        return database.getCollection(CollectionName.PRODUCT, Product.class);
    }

    public MongoCollection<Sales> getSalesCollection() {
        return database.getCollection(CollectionName.SALES, Sales.class);
    }
}
