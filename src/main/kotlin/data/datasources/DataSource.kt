package data.datasources

import java.util.UUID

interface DataSource <T>{
    fun getAll(): List<T>
    fun getById(id: UUID): T?
    fun saveAll(items: List<T>)
    fun add(item: T)
}