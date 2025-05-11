package org.override.quickness.shared.utils.enums

/**
 * Enum representing the states of Mexico and their associated codes.
 *
 * Each state has a two-letter code and a human-readable display name.
 *
 * @property code The two-letter code of the state.
 * @property displayName The human-readable name of the state.
 */
enum class MexicanState(val code: String, val displayName: String) {
    Aguascalientes("AS", "Aguascalientes"),
    BajaCalifornia("BC", "Baja California"),
    BajaCaliforniaSur("BS", "Baja California Sur"),
    Campeche("CC", "Campeche"),
    Chiapas("CS", "Chiapas"),
    Chihuahua("CH", "Chihuahua"),
    Coahuila("CL", "Coahuila"),
    Colima("CM", "Colima"),
    Durango("DG", "Durango"),
    Guanajuato("GT", "Guanajuato"),
    Guerrero("GR", "Guerrero"),
    Hidalgo("HG", "Hidalgo"),
    Jalisco("JC", "Jalisco"),
    EstadoDeMexico("MC", "Estado de México"),
    Michoacan("MN", "Michoacán"),
    Morelos("MS", "Morelos"),
    Nayarit("NT", "Nayarit"),
    NuevoLeon("NL", "Nuevo León"),
    Oaxaca("OC", "Oaxaca"),
    Puebla("PL", "Puebla"),
    Queretaro("QT", "Querétaro"),
    QuintanaRoo("QR", "Quintana Roo"),
    SanLuisPotosi("SP", "San Luis Potosí"),
    Sinaloa("SL", "Sinaloa"),
    Sonora("SR", "Sonora"),
    Tabasco("TC", "Tabasco"),
    Tamaulipas("TS", "Tamaulipas"),
    Tlaxcala("TL", "Tlaxcala"),
    Veracruz("VZ", "Veracruz"),
    Yucatan("YN", "Yucatán"),
    Zacatecas("ZS", "Zacatecas"),
    CiudadDeMexico("DF", "Ciudad de México"),
    NacidosEnElExtranjero("NE", "Nacidos en el extranjero");

    companion object {
        /**
         * Retrieves a [MexicanState] by its display name.
         *
         * @param displayName The human-readable name of the state.
         * @return The corresponding [MexicanState], or `null` if no match is found.
         *
         * Example:
         * ```
         * val state = MexicanState.fromDisplayName("Jalisco")
         * println(state?.code) // Output: JC
         * ```
         */
        fun fromDisplayName(displayName: String): MexicanState? {
            return entries.find { it.displayName.equals(displayName, ignoreCase = true) }
        }

        /**
         * Retrieves a [MexicanState] by its code.
         *
         * @param code The two-letter code of the state.
         * @return The corresponding [MexicanState], or `null` if no match is found.
         *
         * Example:
         * ```
         * val state = MexicanState.fromCode("QR")
         * println(state?.displayName) // Output: Quintana Roo
         * ```
         */
        fun fromCode(code: String): MexicanState? {
            return entries.find { it.code.equals(code, ignoreCase = true) }
        }
    }
}
