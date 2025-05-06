package data.datasources

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.bson.UuidRepresentation


object MongoDatabaseFactory {

    private const val connectionString = "mongodb+srv://7amasa:9LlgpCLbd99zoRrJ@amsterdam.qpathz3.mongodb.net/?retryWrites=true&w=majority&appName=Amsterdam"

    private val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(connectionString))
        .uuidRepresentation(UuidRepresentation.STANDARD)
        .build()

    val db = MongoClient
        .create(settings)
        .getDatabase("Amsterdam")
}