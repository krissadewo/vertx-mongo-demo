package id.blacklabs.vertx.mongo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import id.blacklabs.vertx.mongo.common.CollectionName;
import id.blacklabs.vertx.mongo.context.ConfigContext;
import id.blacklabs.vertx.mongo.document.Product;
import id.blacklabs.vertx.mongo.document.Sales;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.Shareable;
import lombok.Builder;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * @author krissadewo
 * @date 4/24/21 7:56 PM
 */
public class MongoConfig implements Shareable {

    private MongoDatabase database;

    @Builder
    private MongoConfig(Vertx vertx) {
        buildClient();

        //register config to context
        ConfigContext configContext = new ConfigContext(vertx);
        configContext.putIfAbsent(MongoConfig.class, () -> this);
    }

    private void buildClient() {
        PojoCodecProvider codecProvider = PojoCodecProvider.builder()
            .conventions(Arrays.asList(Conventions.ANNOTATION_CONVENTION, Conventions.OBJECT_ID_GENERATORS))
            .automatic(true)
            .build();

        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(codecProvider));

        MongoClient mongoClient = MongoClients.create(
            MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://root:root@mongo.local1:27017/?authSource=admin&readPreference=primary&appname=MongoDB%20Compass&ssl=false"))
                .codecRegistry(codecRegistry)
                .build()
        );

        database = mongoClient.getDatabase("vertx_demo");
        database.withCodecRegistry(codecRegistry);
    }

    public MongoCollection<Product> getProductCollection() {
        return database.getCollection(CollectionName.PRODUCT, Product.class);
    }

    public MongoCollection<Sales> getSalesCollection() {
        return database.getCollection(CollectionName.SALES, Sales.class);
    }
}
