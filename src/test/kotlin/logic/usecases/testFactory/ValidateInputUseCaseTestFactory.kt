package logic.usecases.testFactory

import java.util.UUID

object ValidateInputUseCaseTestFactory {
    const val VALID_NAME = "Bilal Al-khatib"
    const val SHORT_NAME = "az"
    val LONG_NAME = "a".repeat(101)
    const val NAME_WITH_INVALID_CHARACTERS = "bilal@x!"
    const val NAME_WITH_UNDERSCORES_AND_SPACES = "Bilal_b Alkhatib-A"
    const val BLANK_NAME = "   "

    const val INVALID_UUID_STRING_FORMAT = "XYZ"
    const val EMPTY_STRING = ""
    val VALID_UUID = UUID.randomUUID().toString()
}