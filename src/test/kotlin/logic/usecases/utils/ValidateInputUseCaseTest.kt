package logic.usecases.utils

import com.google.common.truth.Truth
import helper.ValidateInputUseCaseTestFactory
import org.junit.jupiter.api.Test

class ValidateInputUseCaseTest {

    private val validateInputUseCase: ValidateInputUseCase = ValidateInputUseCase()

    //region Name Validation Test Cases
    @Test
    fun `should return true when take a valid name`() {
        //Given & When
        val result = validateInputUseCase.isValidName(ValidateInputUseCaseTestFactory.VALID_NAME)

        //Then
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `should return false when take a blank name`() {
        //Given & When
        val result = validateInputUseCase.isValidName(ValidateInputUseCaseTestFactory.BLANK_NAME)

        //Then
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `should return false when take a short name(less than 3) characters`() {
        //Given & When
        val result = validateInputUseCase.isValidName(ValidateInputUseCaseTestFactory.SHORT_NAME)

        //Then
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `should return false when take a name longer than 100 characters`() {
        //Given & When
        val result = validateInputUseCase.isValidName(ValidateInputUseCaseTestFactory.LONG_NAME)

        //Then
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `should return false when take a name with invalid characters`() {
        //Given & When
        val result = validateInputUseCase.isValidName(ValidateInputUseCaseTestFactory.NAME_WITH_INVALID_CHARACTERS)

        //Then
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `should return true when take a name with underscores, spaces`() {
        //Given & When
        val result = validateInputUseCase.isValidName(ValidateInputUseCaseTestFactory.NAME_WITH_UNDERSCORES_AND_SPACES)

        //Then
        Truth.assertThat(result).isTrue()
    }

    //endregion

    //region UUID Validation Test Cases

    @Test
    fun `should return true when take a valid UUID`() {
        //Given & When
        val result = validateInputUseCase.isValidUUID(ValidateInputUseCaseTestFactory.VALID_UUID)

        //Then
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `should return false when take an invalid UUID`() {
        //Given & When
        val result = validateInputUseCase.isValidUUID(ValidateInputUseCaseTestFactory.INVALID_UUID_STRING_FORMAT)

        //Then
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `should return false when take an empty string as UUID`() {
        //Given & When
        val result = validateInputUseCase.isValidUUID(ValidateInputUseCaseTestFactory.EMPTY_STRING)

        //Then
        Truth.assertThat(result).isFalse()
    }

    //endregion
}