# Quickness-Mobile

Bienvenido al repositorio de Quickness-Mobile, una aplicación móvil multiplataforma (Android & iOS)
desarrollada con Kotlin Multiplatform Mobile (KMM) y Compose Multiplatform.

## Descripción General

Quickness-Mobile tiene como objetivo ofrecer [**AÑADE AQUÍ UNA BREVE DESCRIPCIÓN DEL PROPÓSITO
PRINCIPAL DE TU APP. Ejemplo: "una solución rápida y eficiente para gestionar tareas diarias" o "una
plataforma para conectar usuarios con servicios locales"**]. La aplicación está diseñada con una
arquitectura modular y moderna para facilitar la escalabilidad, el mantenimiento y la colaboración
en el desarrollo.

## ✨ Características Principales (Inferidas)

* **Autenticación de Usuarios:**
    * Registro de nuevas cuentas.
    * Inicio de sesión con credenciales.
    * Opción de "Olvidé mi contraseña".
    * Inicio de sesión mediante autenticación biométrica.
* **Funcionalidad de Códigos QR:**
    * Generación y visualización de códigos QR.
    * Escaneo de códigos QR (probable).
    * Configuración personalizada de códigos QR.
* **Tienda / Productos (Shop):**
    * Visualización de productos o servicios.
    * Gestión de saldo o monedero virtual.
* **Servicios:**
    * Acceso a diferentes servicios ofrecidos por la aplicación.
* **Configuración Detallada:**
    * **Cuenta:** Gestión de la información del usuario.
    * **Privacidad:** Control sobre las opciones de privacidad.
    * **Seguridad:** Ajustes de seguridad de la cuenta.
    * **Aplicación:** Configuraciones generales de la app.
    * **Notificaciones:** Preferencias de notificación.
    * **Visualización:** Opciones de tema y apariencia.
    * **Idioma:** Selección de idioma de la aplicación.
    * **QR:** Configuraciones específicas para la funcionalidad QR.
* **Integración con Mapas:**
    * Funcionalidades que utilizan mapas (probable integración con Google Maps).
* **Interfaz de Usuario Moderna:**
    * Desarrollada con Compose Multiplatform para una experiencia de usuario consistente y fluida en
      Android e iOS.

## 🛠️ Tecnologías y Arquitectura

* **Kotlin Multiplatform Mobile (KMM):** Para compartir la lógica de negocio, acceso a datos, red y
  más entre Android e iOS.
* **Compose Multiplatform:** Para construir la interfaz de usuario desde una base de código
  compartida en Kotlin.
* **Kotlin:** Lenguaje principal para la lógica compartida y la aplicación Android.
* **Swift y SwiftUI (o UIKit con hosting de Compose):** Para la capa de presentación específica de
  iOS y la integración con el código KMM.
* **Arquitectura Modular:**
    * **Por Capas:** Separación clara de responsabilidades (UI, Dominio/Lógica de Negocio, Datos,
      Red).
        * `composeApp`: Contiene la UI principal y la lógica de presentación compartida.
        * `shared`: Módulos de utilidades, recursos y componentes de UI base compartidos.
        * `network`: Encapsula toda la comunicación de red (definiciones en `network/api` e
          implementaciones en `network/impl`).
        * `data`: Maneja la persistencia de datos locales y la gestión de datos (definiciones en
          `data/api` e implementaciones en `data/impl`).
    * **Por Funcionalidad (`feature`):** Cada característica principal de la aplicación (login,
      home, settings, etc.) está aislada en su propio módulo, promoviendo un bajo acoplamiento y
      alta cohesión.
* **Patrón de Diseño MVVM (Modelo-Vista-VistaModelo):** Utilizado para estructurar la lógica de
  presentación y el estado de la UI.
* **Inyección de Dependencias:** (Inferido por carpetas `di`) Probablemente utilizando Koin o una
  librería similar para gestionar las dependencias a lo largo de la aplicación.
* **Firebase:** Integración con servicios de Firebase (autenticación, etc., inferido por
  `google-services.json` y referencias a `FirebaseAuth`).
* **Persistencia Local:**
    * **Room:** Para la base de datos relacional local (inferido por `TokenDao`, `TokenEntity`).
    * **DataStore:** Para el almacenamiento de preferencias y datos simples (inferido por
      `DataStoreRepository`).
* **Gradle:** Como sistema de construcción para gestionar dependencias y el proceso de compilación.

## 🏗️ Estructura del Proyecto

El proyecto está organizado en los siguientes módulos principales:

* **`Quickness-Mobile/`** (Raíz del proyecto)
    * **`composeApp/`**: Módulo principal de KMM que contiene la UI de Compose Multiplatform y la
      lógica de aplicación compartida. Se compila para Android e iOS.
        * `commonMain/`: Código Kotlin común para Android e iOS.
        * `androidMain/`: Implementaciones específicas de Android (ej. `MainActivity`).
        * `iosMain/`: Implementaciones específicas de iOS (ej. `MainViewController` para integrar
          Compose en iOS).
    * **`iosApp/`**: Proyecto Xcode para la aplicación iOS. Contiene el `AppDelegate`,
      `ContentView.swift` y otros archivos específicos de la plataforma iOS.
    * **`shared/`**: Módulo KMM que provee componentes y utilidades fundamentales compartidos.
        * `ui/`: (Recomendación: debería ser la única fuente de componentes de UI genéricos y
          compartidos).
        * `resources/`: Recursos compartidos (cadenas de texto, drawables, etc.).
        * `utils/`: Clases de utilidad, constantes, extensiones, etc.
    * **`feature/`**: Contenedor de módulos de funcionalidad. Cada submódulo representa una
      característica específica de la aplicación.
        * `api/`: Define interfaces y contratos de navegación para los módulos `feature`.
        * `login/`: Funcionalidad de inicio de sesión y registro.
        * `biometric/`: Funcionalidad de autenticación biométrica.
        * `start/`: Pantallas o lógica inicial de la aplicación.
        * `home/`: Funcionalidad principal o "Home" de la app, que a su vez contiene:
            * `shop/`: Característica de tienda o compra.
            * `qr/`: Característica relacionada con códigos QR.
            * `service/`: Característica de servicios.
            * `settings/`: Módulo de configuración, altamente granularizado por sección (cuenta,
              privacidad, etc.).
    * **`network/`**: Módulo KMM para la capa de red.
        * `api/`: Interfaces para los servicios de red (ej. Retrofit/Ktor), modelos de datos (DTOs).
        * `impl/`: Implementaciones concretas de los servicios de red.
    * **`data/`**: Módulo KMM para la capa de datos.
        * `api/`: Interfaces para repositorios, DAOs (Data Access Objects), entidades de base de
          datos.
        * `impl/`: Implementaciones de repositorios, configuración de la base de datos (Room),
          DataStore.

## 🚀 Configuración y Compilación

### Prerrequisitos

* Android Studio [Versión Recomendada, ej. Iguana o superior]
* Xcode [Versión Recomendada, ej. 15.x o superior]
* Kotlin Plugin para Android Studio
* Kotlin Multiplatform Mobile Plugin
* Java Development Kit (JDK) [Versión, ej. 17]
* CocoaPods (para dependencias de iOS)

### Pasos para Android

1. Clonar el repositorio: `git clone https://[URL_DEL_REPOSITORIO]/Quickness-Mobile.git`
2. Abrir el proyecto con Android Studio.
3. Android Studio debería sincronizar el proyecto Gradle automáticamente.
4. Seleccionar la configuración de ejecución `composeApp` o `androidApp` (según esté nombrado tu
   módulo principal de Android).
5. Ejecutar en un emulador o dispositivo Android.

### Pasos para iOS

1. Asegúrate de tener CocoaPods instalado: `sudo gem install cocoapods`
2. Navega a la raíz del proyecto y ejecuta `pod install` dentro del directorio `iosApp` (o el script
   de Gradle que maneje la instalación de Pods, ej. `./gradlew iosApp:podInstall`).
    * *Nota: La configuración de KMM a menudo integra la instalación de Pods en el proceso de build
      de Gradle.*
3. Abre el archivo `.xcworkspace` (NO el `.xcodeproj`) ubicado en `Quickness-Mobile/iosApp/` con
   Xcode.
4. Selecciona un simulador o dispositivo iOS.
5. Construye y ejecuta el proyecto desde Xcode.

### Variables de Entorno / Archivos de Configuración

* **Firebase:** Asegúrate de tener el archivo `google-services.json` correcto en la ruta
  `Quickness-Mobile/composeApp/` para Android. Para iOS, el archivo `GoogleService-Info.plist` debe
  estar configurado en el proyecto de Xcode y añadido a los targets correspondientes.
* **Claves de API:** Si la aplicación utiliza APIs externas que requieren claves, considera usar un
  archivo `local.properties` (y no subirlo al control de versiones) o variables de entorno para
  gestionarlas.

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas! Si deseas contribuir al proyecto, por favor sigue estos pasos:

1. Haz un Fork del repositorio.
2. Crea una nueva rama para tu funcionalidad o corrección:
   `git checkout -b feature/nueva-funcionalidad` o `fix/descripcion-del-bug`.
3. Realiza tus cambios y haz commit: `git commit -m "Agrega nueva funcionalidad X"`.
4. Empuja tus cambios a tu rama: `git push origin feature/nueva-funcionalidad`.
5. Abre un Pull Request hacia la rama principal del repositorio original.

Por favor, asegúrate de que tu código siga las guías de estilo del proyecto y que todas las pruebas
pasen.

## 📄 Licencia

Este proyecto está bajo la Licencia [NOMBRE DE LA LICENCIA, ej. MIT, Apache 2.0]. Consulta el
archivo `LICENSE` para más detalles (si no existe, considera añadir uno).

---

*Este README fue generado basándose en la estructura del proyecto. Por favor, actualízalo con
información más específica sobre el propósito de la aplicación, instrucciones de configuración
detalladas y cualquier otra información relevante.*
