package org.quickness.utils.`object`

import org.quickness.utils.enums.MexicanState
import org.quickness.utils.enums.RestrictedWords
import org.quickness.utils.`object`.Extensions.isVowel

object ValidatesData {
    private fun isNotInappropriate(name: String): Boolean {
        val prohibitedWordsSet = RestrictedWords.getAllWords().toSet()

        val nameWords = name.split(Regex("\\s+"))

        return nameWords.none { word -> prohibitedWordsSet.contains(word.lowercase()) }
    }

    private fun doesDateMatchCurp(
        curpDate: String,
        day: String,
        month: String,
        year: String
    ): Boolean = curpDate == "${year.takeLast(2)}${month.padStart(2, '0')}${day.padStart(2, '0')}"

    private fun doesSexMatchCurp(
        curpSex: Char,
        curp: String,
        sex: String
    ): Boolean {
        val enteredSex = curp[10].uppercaseChar()
        return enteredSex == curpSex && enteredSex == if (sex == "Male") 'H' else 'M'
    }

    private fun doesStateMatchCurp(
        curpState: String,
        selectedState: String
    ): Boolean {
        return MexicanState.fromDisplayName(selectedState)?.code == curpState
    }

    private fun doNamesMatchCurp(
        curpName: String,
        name: String
    ): Boolean {
        val names = name.split(" ")
        if (names.size < 3) return false

        val firstSurname = names[0].uppercase()
        val secondSurname = names[1].uppercase()
        val firstName = names[2].uppercase()

        var generatedInitials =
            "${firstSurname[0]}${firstSurname.drop(1).firstOrNull { it.isVowel() }}"
        generatedInitials += secondSurname[0]
        generatedInitials += firstName[0]

        return curpName.startsWith(generatedInitials)
    }

    /**
     * Validates the password based on multiple criteria:
     * - Minimum 8 characters.
     * - At least one uppercase letter.
     * - At least one lowercase letter.
     * - At least one number.
     * - At least one special character.
     *
     * @return `true` if the password meets all criteria; `false` otherwise.
     */
    fun isPasswordValid(
        password: String,
        errorMessage: (String) -> Unit,
    ): Boolean {
        return when {
            password.isEmpty() -> {
                errorMessage("Password is required.")
                false
            }

            password.length < 8 -> {
                errorMessage("Password must be at least 8 characters long.")
                false
            }

            !password.any { it.isUpperCase() } -> {
                errorMessage("Password must contain at least one uppercase letter.")
                false
            }

            !password.any { it.isLowerCase() } -> {
                errorMessage("Password must contain at least one lowercase letter.")
                false
            }

            !password.any { it.isDigit() } -> {
                errorMessage("Password must contain at least one number.")
                false
            }

            !password.any { "!@#\$%^&*()-_=+[{]}|;:'\",<.>/?".contains(it) } -> {
                errorMessage("Password must contain at least one special character.")
                false
            }

            else -> true
        }
    }

    /**
     * Validates the email format and checks if the email field is not empty.
     *
     * @return `true` if the email is valid; `false` otherwise.
     */
    fun isEmailValid(
        email: String,
        errorMessage: (String) -> Unit,
    ): Boolean = when {
        email.isEmpty() -> {
            errorMessage("Email is required.")
            false
        }

        !Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$").matches(email) -> {
            errorMessage("Invalid email format. Please provide a valid email address.")
            false
        }

        else -> true
    }

    /**
     * Confirms that the password and confirm password fields match and are not empty.
     *
     * @return `true` if the passwords match; `false` otherwise.
     */
    fun confirmPassword(
        errorMessage: (String) -> Unit,
        confirmPassword: String,
        password: String,
    ): Boolean = when {
        confirmPassword.isEmpty() -> {
            errorMessage("Confirm password is required.")
            false
        }

        password != confirmPassword -> {
            errorMessage("Passwords do not match.")
            false
        }

        else -> true
    }

    /**
     * Formats the phone number by removing invalid characters and applying a standard format
     * (e.g., "+52 XXX-XXX-XXXX").
     *
     * @param phoneNumber The raw phone number provided by the user.
     * @return The formatted phone number as a [String].
     */
    fun formatPhoneNumber(phoneNumber: String): String = phoneNumber
        .replace(Regex("[^0-9]"), "")
        .take(10)
        .let { digits ->
            if (digits.length == 10) {
                "+52 ${digits.substring(0, 3)}-${digits.substring(3, 6)}-${digits.substring(6)}"
            } else {
                digits
            }
        }

    /**
     * Validates the phone number to ensure it follows a standard format, is not empty,
     * and falls within the length constraints (10–15 digits).
     *
     * @return `true` if the phone number is valid; `false` otherwise.
     */
    fun isPhoneNumberValid(
        errorMessage: (String) -> Unit,
        phone: String
    ): Boolean {
        return when {
            phone.isEmpty() -> {
                errorMessage("Phone number is required.")
                false
            }

            !Regex("^\\+?[1-9]\\d{1,14}\$").matches(phone) -> {
                errorMessage("Invalid phone number format. Please provide a valid number (e.g., +123456789).")
                false
            }

            phone.length < 10 -> {
                errorMessage("Phone number must be at least 10 digits.")
                false
            }

            phone.length > 15 -> {
                errorMessage("Phone number cannot exceed 15 digits.")
                false
            }

            else -> {
                true
            }
        }
    }

    /**
     * Validates the user's name to ensure it is not empty, has a minimum length,
     * does not contain numbers, and is not inappropriate.
     *
     * @return `true` if the name is valid; `false` otherwise.
     */
    fun isNameValid(
        errorMessage: (String) -> Unit,
        capitalizeWords: () -> Unit,
        name: String
    ): Boolean = when {
        name.isEmpty() -> {
            errorMessage("Name is required.")
            false
        }

        name.length < 2 -> {
            errorMessage("Name must be at least 2 characters long.")
            false
        }

        name.any { it.isDigit() } -> {
            errorMessage("Name cannot contain numbers.")
            false
        }

        !isNotInappropriate(name) -> {
            errorMessage("Name cannot contain inappropriate words.")
            false
        }

        else -> {
            capitalizeWords()
            true
        }
    }

    /**
     * Validates the CURP field for correct format and logical consistency with other data
     * such as date of birth, sex, and selected state.
     *
     * @return `true` if the CURP is valid; `false` otherwise.
     */
    fun isCurpValid(
        curp: String,
        day: String,
        month: String,
        year: String,
        sex: String,
        name: String,
        selectedState: String,
        errorMessage: (String) -> Unit
    ): Boolean {
        return when {
            curp.isEmpty() -> {
                errorMessage("CURP is required.")
                false
            }

            !Regex("^[A-Z]{4}[0-9]{6}[HM][A-Z]{2}[A-Z]{3}[0-9A-Z]{2}$").matches(curp) -> {
                errorMessage("Invalid CURP format. Please provide a valid CURP.")
                false
            }

            !doesDateMatchCurp(
                curpDate = curp.substring(4, 10),
                day = day,
                month = month,
                year = year
            ) -> {
                errorMessage("Invalid CURP format. Please provide a valid CURP.")
                false
            }

            !doesSexMatchCurp(
                curpSex = curp[10],
                curp = curp,
                sex = sex
            ) -> {
                errorMessage("Invalid CURP format. Please provide a valid CURP.")
                false
            }

            !doesStateMatchCurp(
                curpState = curp.substring(11, 13),
                selectedState = selectedState,
            ) -> {
                errorMessage("Invalid CURP format. Please provide a valid CURP.")
                false
            }

            !doNamesMatchCurp(
                curpName = curp.substring(0, 4),
                name = name
            ) -> {
                errorMessage("name incorrect.")
                false
            }

            else -> true
        }
    }
}