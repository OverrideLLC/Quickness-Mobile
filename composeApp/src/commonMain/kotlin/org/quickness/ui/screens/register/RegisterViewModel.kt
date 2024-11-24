package org.quickness.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soywiz.krypto.SHA256
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.quickness.data.repository.RegisterRepository

class RegisterViewModel(
    private val registerRepository: RegisterRepository,
) : ViewModel() {
    data class RegisterState(
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val name: String = "",
        val curp: String = "",
        val phoneNumber: String = "",
        val termsAndConditions: Boolean = false,
        val privacyPolicy: Boolean = false,
        val dataAnalytics: Boolean = false,
        val day: String = "",
        val month: String = "",
        val year: String = "",
        val sex: String = "",
        val selectedState: String = "",
        val verificationCode: String = "",
        val isVisiblePassword: Boolean = false,
        val isVisibleConfirmPassword: Boolean = false,
        val isError: Boolean = false,
        val errorMessage: String = "",
        val isLoading: Boolean = false,
    )

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun toggleError() {
        _state.value = _state.value.copy(isError = !_state.value.isError)
    }

    private fun errorMessage(errorMessage: String) {
        _state.value = _state.value.copy(errorMessage = errorMessage)
    }

    fun validateEmailAndPassword(): Boolean {
        return isEmailValid() && isPasswordValid() && confirmPassword()
    }

    fun validatePersonalInfo(): Boolean {
        return isNameValid() && isCurpValid() && isPhoneNumberValid()
    }

    private fun isEmailValid(): Boolean {
        val emailRegex =
            Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
        return when {
            _state.value.email.isEmpty() -> {
                errorMessage("Email is required")
                false
            }

            !emailRegex.matches(_state.value.email) -> {
                errorMessage("Invalid email format. Please provide a valid email.")
                false
            }

            else -> true
        }
    }

    private fun isPasswordValid(): Boolean {
        val password = _state.value.password
        return when {
            password.isEmpty() -> {
                errorMessage("Password is required")
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

    private fun confirmPassword(): Boolean {
        return when {
            _state.value.confirmPassword.isEmpty() -> {
                errorMessage("Please confirm your password.")
                false
            }

            _state.value.password != _state.value.confirmPassword -> {
                errorMessage("Passwords do not match.")
                false
            }

            else -> true
        }
    }

    fun isNameValid(): Boolean {
        return when {
            _state.value.name.isEmpty() -> {
                errorMessage("Name is required.")
                false
            }

            _state.value.name.length < 2 -> { // Valida que el nombre tenga al menos 2 caracteres
                errorMessage("Name must be at least 2 characters long.")
                false
            }

            _state.value.name.any { it.isDigit() } -> { // Evita números en el nombre
                errorMessage("Name cannot contain numbers.")
                false
            }

            isNotInappropriate(_state.value.name) -> {
                errorMessage("Invalid inappropriate name")
                false
            }

            else -> {
                capitalizeWords()
                true
            }
        }
    }

    fun isCurpValid(): Boolean {
        val curpRegex = Regex("^[A-Z]{4}[0-9]{6}[HM]{1}[A-Z]{2}[A-Z]{3}[0-9A-Z]{2}$")
        val curp = _state.value.curp.uppercase()

        return when {
            curp.isEmpty() -> {
                errorMessage("CURP is required.")
                false
            }

            !curpRegex.matches(curp) -> {
                errorMessage("Invalid CURP format. Please provide a valid CURP.")
                false
            }

            !doesDateMatchCurp(curp.substring(4, 10)) -> {
                errorMessage("Invalid CURP format. Please provide a valid CURP.")
                false
            }

            !doesSexMatchCurp(curp[10]) -> {
                errorMessage("Invalid CURP format. Please provide a valid CURP.")
                false
            }

            !doesStateMatchCurp(curp.substring(11, 13)) -> {
                errorMessage("Invalid CURP format. Please provide a valid CURP.")
                false
            }

            !doNamesMatchCurp(curp.substring(0, 4)) -> {
                errorMessage("Name incorrect")
                false
            }

            else -> true
        }
    }

    private fun isNotInappropriate(name: String): Boolean {
        val prohibitedWords = listOf(
            // Roles administrativos y palabras reservadas
            "admin", "administrator", "root", "superuser", "mod", "moderator", "support",
            "helpdesk", "sysadmin", "system", "owner", "god", "master", "boss",

            // Palabras ofensivas generales (en inglés)
            "idiot", "stupid", "dumb", "fool", "jerk", "moron", "loser", "trash", "scum",
            "nonsense", "troll", "hater", "clown",

            // Insultos y términos ofensivos (en varios idiomas)
            "puta", "puto", "pendejo", "idiota", "imbecil", "tarado", "baboso", "tonto",
            "estupido", "cabrón", "gilipollas", "mamon", "mierda", "imbécil", "zorra",
            "retard", "bastard", "dick", "pussy", "ass", "butt", "crap", "hell", "damn",

            // Palabras relacionadas con odio
            "racist", "nazi", "bigot", "terrorist", "killer", "murderer", "pedophile",
            "abuser", "pervert", "incest", "rapist", "misogynist", "homophobe", "antisemitic",

            // Referencias sexuales explícitas
            "sex", "porn", "xxx", "naked", "boobs", "butt", "orgasm", "penis", "vagina",
            "cum", "sperm", "milf", "bdsm", "fetish", "anal", "hentai", "incest",
            "masturbate", "ejaculate",

            // Palabras comunes de spam y estafas
            "free", "money", "winner", "lottery", "cash", "bitcoin", "crypto",
            "giveaway", "prize", "gift", "cheap", "discount", "deal", "loan",

            // Términos religiosos inapropiados
            "mohammed", "allah", "buddha", "god", "messiah", "prophet", "christ", "satan",
            "lucifer", "devil", "hell",

            // Palabras relacionadas con drogas o sustancias prohibidas
            "weed", "cannabis", "cocaine", "heroin", "meth", "lsd", "ecstasy", "opium",
            "addict", "drug", "stoned", "high", "joint", "blunt",

            // Otros términos sensibles
            "suicide", "death", "kill", "die", "gun", "bomb", "weapon", "war", "violence",
            "blood", "terror", "shoot", "execute", "massacre", "assassinate"
        )
        return prohibitedWords.none { name.contains(it, ignoreCase = true) }
    }

    private fun doesDateMatchCurp(curpDate: String): Boolean {
        val day = _state.value.day.padStart(2, '0')
        val month = _state.value.month.padStart(2, '0')
        val year = _state.value.year.takeLast(2) // Toma los últimos 2 dígitos del año

        return curpDate == "$year$month$day"
    }

    private fun doesSexMatchCurp(curpSex: Char): Boolean {
        var sexOptions = ' '
        sexOptions = if (_state.value.sex == "Male") 'H' else 'M'
        val enteredSex = _state.value.curp[10].uppercaseChar()
        return enteredSex == curpSex && enteredSex == sexOptions
    }

    private fun doesStateMatchCurp(curpState: String): Boolean {
        val stateCodes = mapOf(
            "Aguascalientes" to "AS",
            "Baja California" to "BC",
            "Baja California Sur" to "BS",
            "Campeche" to "CC",
            "Chiapas" to "CS",
            "Chihuahua" to "CH",
            "Coahuila" to "CL",
            "Colima" to "CM",
            "Durango" to "DG",
            "Guanajuato" to "GT",
            "Guerrero" to "GR",
            "Hidalgo" to "HG",
            "Jalisco" to "JC",
            "Estado de México" to "MC",
            "Michoacán" to "MN",
            "Morelos" to "MS",
            "Nayarit" to "NT",
            "Nuevo León" to "NL",
            "Oaxaca" to "OC",
            "Puebla" to "PL",
            "Querétaro" to "QT",
            "Quintana Roo" to "QR",
            "San Luis Potosí" to "SP",
            "Sinaloa" to "SL",
            "Sonora" to "SR",
            "Tabasco" to "TC",
            "Tamaulipas" to "TS",
            "Tlaxcala" to "TL",
            "Veracruz" to "VZ",
            "Yucatán" to "YN",
            "Zacatecas" to "ZS",
            "Ciudad de México" to "DF",
            "Nacidos en el extranjero" to "NE" // Para los extranjeros
        )

        val enteredStateCode = stateCodes[_state.value.selectedState]
        return curpState == enteredStateCode
    }

    private fun doNamesMatchCurp(curpName: String): Boolean {
        val names = _state.value.name.split(" ")
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

    // Extensión para verificar si un carácter es vocal
    private fun Char.isVowel(): Boolean = this in "AEIOU"


    fun isPhoneNumberValid(): Boolean {
        val phoneRegex = Regex("^\\+?[1-9]\\d{1,14}\$") // Patrón para E.164 (formato internacional)
        val phone = _state.value.phoneNumber

        return when {
            phone.isEmpty() -> {
                errorMessage("Phone number is required.")
                false
            }

            !phoneRegex.matches(phone) -> {
                errorMessage("Invalid phone number format. Please provide a valid number (e.g., +123456789).")
                false
            }

            phone.length < 10 -> { // Longitud mínima estándar para números de teléfono
                errorMessage("Phone number must be at least 10 digits long.")
                false
            }

            phone.length > 15 -> { // Longitud máxima según formato E.164
                errorMessage("Phone number cannot exceed 15 digits.")
                false
            }

            else -> {
                true
            }
        }
    }

    fun isTermsAndConditionsChecked(): Boolean {
        return if (_state.value.termsAndConditions && _state.value.privacyPolicy && _state.value.dataAnalytics)
            true
        else {
            errorMessage("Please accept all terms and conditions.")
            false
        }
    }

    private fun formatPhoneNumber(phoneNumber: String): String {
        return phoneNumber
            .replace(Regex("[^0-9]"), "") // Elimina todos los caracteres que no son dígitos
            .take(10) // Limita el número a los primeros 10 dígitos
            .let { digits ->
                // Formatea solo si tiene exactamente 10 dígitos
                if (digits.length == 10) {
                    "+52 ${digits.substring(0, 3)}-${digits.substring(3, 6)}-${digits.substring(6)}"
                } else {
                    digits // Si no tiene 10 caracteres, no lo formatea
                }
            }
    }

    private fun capitalizeWords(): String {
        return _state.value.name.split(" ")
            .joinToString(" ") { word -> word.replaceFirstChar { it.uppercaseChar() } }
    }

    fun onIsVisiblePasswordChange() {
        _state.value = _state.value.copy(isVisiblePassword = !_state.value.isVisiblePassword)
    }

    fun onIsVisibleConfirmPasswordChange() {
        _state.value =
            _state.value.copy(isVisibleConfirmPassword = !_state.value.isVisibleConfirmPassword)
    }

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _state.value = _state.value.copy(confirmPassword = confirmPassword)
    }

    fun onNameChange(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun onCurpChange(curp: String) {
        _state.value = _state.value.copy(curp = curp)
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        _state.value = _state.value.copy(phoneNumber = phoneNumber)
    }

    fun onTermsAndConditionsChange() {
        _state.value = _state.value.copy(termsAndConditions = !_state.value.termsAndConditions)
    }

    fun onPrivacyPolicyChange() {
        _state.value = _state.value.copy(privacyPolicy = !_state.value.privacyPolicy)
    }

    fun onDataAnalyticsChange() {
        _state.value = _state.value.copy(dataAnalytics = !_state.value.dataAnalytics)
    }

    fun onDayChange(day: String) {
        _state.value = _state.value.copy(day = day)
    }

    fun onMonthChange(month: String) {
        _state.value = _state.value.copy(month = month)
    }

    fun onYearChange(year: String) {
        _state.value = _state.value.copy(year = year)
    }

    fun onStateSelected(state: String) {
        _state.value = _state.value.copy(selectedState = state)
    }

    fun onSexSelected(sex: String) {
        _state.value = _state.value.copy(sex = sex)
    }

    private fun encryptPasswordSHA256(password: String): String {
        val hashBytes = SHA256.digest(password.encodeToByteArray())
        return hashBytes.hex
    }

    fun register(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val result = registerRepository.register(
                    email = _state.value.email,
                    password = encryptPasswordSHA256(_state.value.password),
                    name = _state.value.name,
                    curp = _state.value.curp,
                    phoneNumber = formatPhoneNumber(_state.value.phoneNumber)
                )
                if (result.uid != "") {
                    onSuccess()
                } else {
                    onError()
                    _state.value = _state.value.copy(isError = true)
                    _state.value = _state.value.copy(
                        errorMessage = result.message
                    )
                    println(result.message)
                }
            } catch (e: Exception) {
                onError()
                _state.value = _state.value.copy(isError = true)
                _state.value = _state.value.copy(
                    errorMessage = e.message ?: "Error connecting to server"
                )
                println(e.message)
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

}