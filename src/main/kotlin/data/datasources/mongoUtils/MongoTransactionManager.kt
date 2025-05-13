package data.datasources.mongoUtils

import com.mongodb.kotlin.client.coroutine.MongoClient

class MongoTransactionManager(private val mongoClient: MongoClient) {
    suspend fun <T> executeInTransaction(block: suspend () -> T): T {
        val session = mongoClient.startSession()
        return try {
            session.startTransaction()
            val result = block()
            session.commitTransaction()
            result
        } catch (e: Exception) {
            try {
                session.abortTransaction()
            } catch (abortException: Exception) {
                // Log abort exception
                println("Failed to abort transaction: ${abortException.message}")
            }
            throw e
        } finally {
            session.close()
        }
    }
}