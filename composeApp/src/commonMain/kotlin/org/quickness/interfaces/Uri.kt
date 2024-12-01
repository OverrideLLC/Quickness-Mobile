package org.quickness.interfaces

/**
 * Interfaz que define la funcionalidad para la navegación a una URI.
 *
 * Esta interfaz debe ser implementada por cualquier clase que maneje la navegación o el acceso
 * a una URI específica.
 */
interface Uri {
    /**
     * Método para realizar la navegación a la URI especificada.
     *
     * Este método debe implementar la lógica para llevar a cabo la acción de navegación
     * a la URI correspondiente.
     */
    fun navigate()
}
