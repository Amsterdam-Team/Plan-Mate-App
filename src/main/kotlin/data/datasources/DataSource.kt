package data.datasources

import java.util.UUID

interface DataSource{
    fun getAll(): List<Any>
    fun getById(id: UUID): Any
    fun saveAll(items: List<Any>)
    fun add(item: Any)
}