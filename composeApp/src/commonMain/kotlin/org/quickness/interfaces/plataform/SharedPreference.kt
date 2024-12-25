package org.quickness.interfaces.plataform

/**
 * An interface representing a shared preference store for persisting key-value data.
 *
 * This interface provides methods to store and retrieve data of various types,
 * including strings, integers, lists, maps, booleans, floats, and longs.
 * It also includes a method for logging out the user.
 */
interface SharedPreference {

    /**
     * Retrieves a string value associated with the given key.
     *
     * @param key The key to retrieve the value for.
     * @param defaultValue The default value to return if the key is not found. Can be null.
     * @return The string value associated with the key, or the default value if the key is not found.
     */
    fun getString(key: String, defaultValue: String?): String

    /**
     * Sets a string value associated with a given key.
     *
     * @param key The key to identify the stored value.
     * @param value The string value to be stored.
     */
    fun setString(key: String, value: String)

    /**
     * Retrieves an integer value associated with the given key.
     *
     * If the key is not found or the associated value is not an integer,
     * the default value is returned.
     *
     * @param key The key to search for.
     * @param defaultValue The value to return if the key is not found or the associated value is not an integer.
     * @return The integer value associated with the key, or the default value if not found.
     */
    fun getInt(key: String, defaultValue: Int): Int

    /**
     * Sets an integer value associated with the given key.
     *
     * @param key The key to identify the value.
     * @param value The integer value to be stored.
     */
    fun setInt(key: String, value: Int)

    /**
     * Sets a list of strings associated with a given key.
     *
     * @param key The key to associate the list with.
     * @param value The list of strings to set.
     */
    fun setList(key: String, value: List<String>)

    /**
     * Retrieves a list of strings associated with the given key.
     *
     * @param key The key used to identify the list.
     * @return A list of strings associated with the key, or null if the key is not found.
     */
    fun getList(key: String): List<String>?

    /**
     * Retrieves a map associated with the given key.
     *
     * @param key The key used to identify the map.
     * @return The map associated with the key, or null if no such map exists.
     */
    fun getMap(key: String): Map<String, String>?

    /**
     * Sets a map value associated with the given key.
     *
     * This function stores a map of string key-value pairs in some underlying data structure
     * using the provided key as an identifier.
     *
     * @param key The key used to identify the map value.
     * @param value The map containing string key-value pairs to be stored.
     */
    fun setMap(key: String, value: Map<String, String>)

    /**
     * Sets a long value associated with the given key.
     *
     * @param key The key to identify the value.
     * @param value The long value to be stored.
     */
    fun setLong(key: String, value: Long)

    /**
     * Retrieves a Long value associated with the given key.
     *
     * @param key The key to retrieve the value for.
     * @param defaultValue The default value to return if the key is not found.
     * @return The Long value associated with the key, or the default value if the key is not found.
     */
    fun getLong(key: String, defaultValue: Long): Long

    /**
     * Sets a boolean value associated with the given key.
     *
     * @param key The key to identify the stored value.
     * @param value The boolean value to be stored.
     */
    fun setBoolean(key: String, value: Boolean)

    /**
     * Retrieves a boolean value from a source (e.g., SharedPreferences, Bundle) based on the provided key.
     *
     * @param key The key used to identify the boolean value.
     * @param defaultValue The default value to return if the key is not found or the value is not a boolean.
     * @return The boolean value associated with the key, or the defaultValue if not found.
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    /**
     * Sets a float value associated with the given key.
     *
     * @param key The key to identify the value.
     * @param value The float value to be stored.
     */
    fun setFloat(key: String, value: Float)
    /**
     * Retrieves a float value associated with the given key.
     *
     * @param key The key to retrieve the value for.
     * @param defaultValue The default value to return if the key is not found.
     * @return The float value associated with the key, or the default value if the key is not found.
     */
    fun getFloat(key: String, defaultValue: Float): Float
    /**
     * Logs the user out of the application.
     *
     * This function should perform the necessary actions to end the current user's session,
     * such as clearing session data, invalidating tokens, and redirecting to the login screen.
     */
    fun logOut()
}