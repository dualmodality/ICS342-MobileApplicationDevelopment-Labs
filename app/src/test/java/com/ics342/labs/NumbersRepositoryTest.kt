package com.ics342.labs

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.util.UUID
import kotlin.random.Random

internal class NumbersRepositoryTest {

    @Test
    fun ifDatabaseDoesNotHaveANumberFetchItFromTheApi() {
        //Setup
        val database = mockk<Database>()
        val api = mockk<Api>()
        val number = Number(UUID.randomUUID().toString(), Random.nextInt())
        val id = number.id

        every { database.getNumber(id) } returns null
        every { api.getNumber(id) } returns number
        //Test
        val repository = NumbersRepository(database, api)
        val result = repository.getNumber(id)
        //Verify
        assertNotNull(result)

        verify { database.getNumber(id) }
        verify { api.getNumber(id) }
    }

    @Test
    fun ifDatabaseIsEmptyShouldFetchNumbersFromApi() {
        //Setup
        val database: Database = mockk<Database>()
        val api : Api = mockk<Api>()
        val number = Number(UUID.randomUUID().toString(), Random.nextInt())
        val id : String = number.id

        //Mock the return value of getAllNumbers() on the database to be an empty list
        every { database.getAllNumbers() } returns listOf()

        //Mock the return value of getNumbers() on the Api to be a list of numbers.
        every { api.getNumbers() } returns listOf(number)

        every { database.storeNumbers(numbers = any())} just Runs

        //Test
        val repo = NumbersRepository(database, api)
        val results = repo.fetchNumbers()



        //Verify
        //Assert that Database.getAllNumbers() is called once
        verify { database.getAllNumbers() }
        //Assert that Api.getNumbers() is called once
        verify { api.getNumbers() }
        verify { database.storeNumbers(results) }
        //Assert that Database.storeNumbers() is called once with the list you mocked as the return for the Api.getNumbers() function
        assertEquals(listOf(number), repo.fetchNumbers())
    }
}
