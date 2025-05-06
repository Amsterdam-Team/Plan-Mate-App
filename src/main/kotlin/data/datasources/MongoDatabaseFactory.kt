package data.datasources

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.bson.UuidRepresentation


object MongoDatabaseFactory {

    private val connectionString = System.getenv("MONGO_DB_URI")

    private val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(connectionString))
        .uuidRepresentation(UuidRepresentation.STANDARD)
        .build()

    val db = MongoClient
        .create(settings)
        .getDatabase("Amsterdam")
}