package data.datasources

import org.junit.jupiter.api.Test

class CsvDataSourceTest {


    // region getAll
    @Test
    fun `should return list of objects when csv file is valid`() {
    }

    @Test
    fun `should throw empty file exception when reading an empty file`() {
    }

    // endregion

    // region getById
    @Test
    fun `should return object when given valid object id`() {
    }


    @Test
    fun `should throw object does not exist when object id is not found in file`(){

    }
    //endregion

    //region add
    @Test
    fun `should add new csv line to file with object`(){

    }

    @Test
    fun `should throw incorrect type exception when object type does not match file items`(){

    }

    //endregion

    //region saveAll
    @Test
    fun `should overwrite file with new list of objects when given valid list`(){

    }

    @Test
    fun `should throw empty data exception when given empty list`(){

    }
    //endregion

    //region deleteById
    @Test
    fun `should delete item when given id in file`(){

    }

    @Test
    fun `should throw object not found exception when id is not in file`(){

    }
    //endregion
}