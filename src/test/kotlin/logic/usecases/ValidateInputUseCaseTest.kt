package logic.usecases

import com.google.common.truth.Truth.assertThat
import logic.usecases.testFactory.ValidateInputUseCaseTestFactory.BLANK_NAME
import logic.usecases.testFactory.ValidateInputUseCaseTestFactory.EMPTY_STRING
import logic.usecases.testFactory.ValidateInputUseCaseTestFactory.INVALID_UUID_STRING_FORMAT
import logic.usecases.testFactory.ValidateInputUseCaseTestFactory.LONG_NAME
import logic.usecases.testFactory.ValidateInputUseCaseTestFactory.NAME_WITH_INVALID_CHARACTERS
import logic.usecases.testFactory.ValidateInputUseCaseTestFactory.NAME_WITH_UNDERSCORES_AND_SPACES
import logic.usecases.testFactory.ValidateInputUseCaseTestFactory.SHORT_NAME
import logic.usecases.testFactory.ValidateInputUseCaseTestFactory.VALID_NAME
import logic.usecases.testFactory.ValidateInputUseCaseTestFactory.VALID_UUID
import logic.usecases.validation.ValidateInputUseCase
import org.junit.jupiter.api.Test

class ValidateInputUseCaseTest {

    private val validateInputUseCase: ValidateInputUseCase = ValidateInputUseCase()

    //region Name Validation Test Cases
    @Test
    fun `should return true when take a valid name`() {
        //Given & When
        val result = validateInputUseCase.isValidName(VALID_NAME)

        //Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when take a blank name`() {
        //Given & When
        val result = validateInputUseCase.isValidName(BLANK_NAME)

        //Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when take a short name(less than 3) characters`() {
        //Given & When
        val result = validateInputUseCase.isValidName(SHORT_NAME)

        //Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when take a name longer than 100 characters`() {
        //Given & When
        val result = validateInputUseCase.isValidName(LONG_NAME)

        //Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when take a name with invalid characters`() {
        //Given & When
        val result = validateInputUseCase.isValidName(NAME_WITH_INVALID_CHARACTERS)

        //Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return true when take a name with underscores, spaces`() {
        //Given & When
        val result = validateInputUseCase.isValidName(NAME_WITH_UNDERSCORES_AND_SPACES)

        //Then
        assertThat(result).isTrue()
    }

    //endregion

    //region UUID Validation Test Cases

    @Test
    fun `should return true when take a valid UUID`() {
        //Given & When
        val result = validateInputUseCase.isValidUUID(VALID_UUID)

        //Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when take an invalid UUID`() {
        //Given & When
        val result = validateInputUseCase.isValidUUID(INVALID_UUID_STRING_FORMAT)

        //Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when take an empty string as UUID`() {
        //Given & When
        val result = validateInputUseCase.isValidUUID(EMPTY_STRING)

        //Then
        assertThat(result).isFalse()
    }

    //endregion
}