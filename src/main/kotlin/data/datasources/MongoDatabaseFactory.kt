package data.datasources

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.bson.UuidRepresentation


object MongoDatabaseFactory {

    private const val MONGODB_CONNECTION_URL = "mongodb+srv://7amasa:9LlgpCLbd99zoRrJ@amsterdam.qpathz3.mongodb.net/?retryWrites=true&w=majority&appName=Amsterdam"
    private const val DATABASE_NAME = "Amsterdam"

    private val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(MONGODB_CONNECTION_URL))
        .uuidRepresentation(UuidRepresentation.STANDARD)
        .build()

    val database = MongoClient
        .create(settings)
        .getDatabase(DATABASE_NAME)
}