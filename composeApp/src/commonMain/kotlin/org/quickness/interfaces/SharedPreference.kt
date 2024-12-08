package org.quickness.interfaces

/**
 * Interfaz que define las operaciones de lectura y escritura para preferencias compartidas.
 *
 * Esta interfaz debe ser implementada por cualquier clase que se encargue de gestionar las preferencias
 * compartidas de la aplicación, proporcionando métodos para almacenar y recuperar datos de diferentes tipos.
 */
interface SharedPreference {
    /**
     * Recupera un valor de tipo [String] de las preferencias compartidas.
     *
     * @param key La clave para identificar el valor en las preferencias.
     * @param defaultValue El valor por defecto a devolver si la clave no existe.
     * @return El valor asociado a la clave, o el valor por defecto si la clave no existe.
     */
    fun getString(key: String, defaultValue: String?): String

    /**
     * Almacena un valor de tipo [String] en las preferencias compartidas.
     *
     * @param key La clave para identificar el valor.
     * @param value El valor de tipo [String] a almacenar.
     */
    fun setString(key: String, value: String)

    /**
     * Recupera un valor de tipo [Int] de las preferencias compartidas.
     *
     * @param key La clave para identificar el valor.
     * @param defaultValue El valor por defecto a devolver si la clave no existe.
     * @return El valor asociado a la clave, o el valor por defecto si la clave no existe.
     */
    fun getInt(key: String, defaultValue: Int): Int

    /**
     * Almacena un valor de tipo [Int] en las preferencias compartidas.
     *
     * @param key La clave para identificar el valor.
     * @param value El valor de tipo [Int] a almacenar.
     */
    fun setInt(key: String, value: Int)

    /**
     * Almacena una lista de cadenas en las preferencias compartidas.
     *
     * @param key La clave para identificar la lista.
     * @param value La lista de cadenas a almacenar.
     */
    fun setList(key: String, value: List<String>)

    /**
     * Recupera una lista de cadenas de las preferencias compartidas.
     *
     * @param key La clave para identificar la lista.
     * @return La lista de cadenas asociada a la clave, o `null` si la clave no existe.
     */
    fun getList(key: String): List<String>?

    /**
     * Recupera un mapa de cadenas de las preferencias compartidas.
     *
     * @param key La clave para identificar el mapa.
     * @return El mapa de cadenas asociado a la clave, o `null` si la clave no existe.
     */
    fun getMap(key: String): Map<String, String>?

    /**
     * Almacena un mapa de cadenas en las preferencias compartidas.
     *
     * @param key La clave para identificar el mapa.
     * @param value El mapa de cadenas a almacenar.
     */
    fun setMap(key: String, value: Map<String, String>)

    /**
     * Almacena un valor de tipo [Long] en las preferencias compartidas.
     *
     * @param key La clave para identificar el valor.
     * @param value El valor de tipo [Long] a almacenar.
     */
    fun setLong(key: String, value: Long)

    /**
     * Recupera un valor de tipo [Long] de las preferencias compartidas.
     *
     * @param key La clave para identificar el valor.
     * @param defaultValue El valor por defecto a devolver si la clave no existe.
     * @return El valor asociado a la clave, o el valor por defecto si la clave no existe.
     */
    fun getLong(key: String, defaultValue: Long): Long

    /**
     * Almacena un valor de tipo [Boolean] en las preferencias compartidas.
     *
     * @param key La clave para identificar el valor.
     * @param value El valor de tipo [Boolean] a almacenar.
     */
    fun setBoolean(key: String, value: Boolean)

    /**
     * Recupera un valor de tipo [Boolean] de las preferencias compartidas.
     *
     * @param key La clave para identificar el valor.
     * @param defaultValue El valor por defecto a devolver si la clave no existe.
     * @return El valor asociado a la clave, o el valor por defecto si la clave no existe.
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun setFloat(key: String, value: Float)
    fun getFloat(key: String, defaultValue: Float): Float
    fun logOut()
}