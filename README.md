
# **Quickness 0.0.8 Alpha**

## **Instalación de la Aplicación Móvil de Quickness**

Este documento detalla los pasos necesarios para configurar, desarrollar y ejecutar la aplicación móvil de **Quickness** en tu entorno de desarrollo local. La aplicación está construida utilizando **Kotlin Multiplataforma** y **Jetpack Compose Multiplataforma** para una experiencia moderna y eficiente.

---

## **Requisitos Previos**

Antes de comenzar, asegúrate de tener lo siguiente instalado en tu máquina:

1. **Android Studio** (versión 2022.3 o superior).  
   Descárgalo desde [Android Studio](https://developer.android.com/studio).
2. **Java Development Kit (JDK)** 17 o superior.  
   Descárgalo desde [OpenJDK](https://jdk.java.net/).
3. **Kotlin Compiler** (preinstalado con Android Studio).
4. **Git** instalado en tu máquina.  
   Descárgalo desde [Git](https://git-scm.com/).
5. Acceso al repositorio de **Quickness** en GitHub.

---

## **Pasos de Instalación**

### **1. Clonar el Repositorio**
Clona el repositorio en tu máquina local usando el siguiente comando:

```bash
git clone https://github.com/tu_usuario/quickness.git
```

Reemplaza `https://github.com/tu_usuario/quickness.git` con la URL de tu repositorio.

---

### **2. Abrir el Proyecto en Android Studio**
1. Abre **Android Studio**.
2. En la pantalla de inicio, selecciona **"Open"**.
3. Navega hasta la carpeta del proyecto que clonaste y selecciona el directorio `mobile/`.
4. Android Studio comenzará a sincronizar el proyecto automáticamente. Si no lo hace, ve al menú **File > Sync Project with Gradle Files**.

---

### **3. Configurar el Entorno**
1. **Verificar SDK y Plugins**  
   Asegúrate de que el proyecto tiene configurados los siguientes elementos:
  - **SDK mínimo**: 23 (Android 6.0 Marshmallow).
  - **SDK objetivo**: 33 (Android 13).
  - **Gradle Plugin**: 8.2.0 o superior.

2. **Instalar Dependencias**  
   Abre el terminal de Android Studio y ejecuta:

   ```bash
   ./gradlew clean build
   ```

   Esto instalará todas las dependencias necesarias y verificará que el proyecto esté configurado correctamente.

---

### **4. Ejecutar la Aplicación**
1. **Configurar un Emulador o Dispositivo Físico**
  - Emulador: Crea un dispositivo virtual desde el **AVD Manager** en Android Studio. Selecciona una imagen de Android 13 o superior.
  - Dispositivo físico: Habilita la **depuración USB** en tu teléfono y conéctalo a tu computadora.

2. **Iniciar la Aplicación**
  - Desde Android Studio, selecciona el dispositivo en el que deseas ejecutar la aplicación.
  - Haz clic en el botón **Run** o presiona `Shift + F10`.

La aplicación debería iniciarse en el emulador o dispositivo físico.

---
## **Estructura del Proyecto**

El directorio `mobile/` está organizado de la siguiente manera:

- **`app/`**: Contiene el código fuente principal de la aplicación móvil.
  - **`ui/`**: Componentes de interfaz de usuario creados con Compose.
  - **`viewmodel/`**: Lógica de negocio implementada en ViewModels.
  - **`data/`**: Repositorios y fuentes de datos (API, almacenamiento local, etc.).
- **`shared/`**: Código compartido con otros módulos (Kotlin Multiplataforma).
- **`build.gradle`**: Configuración específica del módulo móvil.

---

## **Flujo de Desarrollo**

### **1. Crear una Rama Nueva**
Antes de comenzar a trabajar, crea una rama basada en el feature que implementarás:

```bash
git checkout -b feature/nombre-del-feature
```

### **2. Añadir Cambios y Realizar un Commit**
Después de realizar cambios, ejecuta:

```bash
git add .
git commit -m "Descripción de los cambios realizados"
```

### **3. Subir Cambios al Repositorio**
Sube tu rama al repositorio remoto:

```bash
git push origin feature/nombre-del-feature
```

### **4. Crear un Pull Request**
Abre un **Pull Request** en GitHub y espera la revisión antes de fusionar tu rama en `develop`.

---

## **Control de Versiones**

El esquema de versiones de la aplicación sigue el siguiente formato:

```
<major>.<minor>.<patch>-<estado>
```

- **`<major>`**: Cambios importantes o incompatibles hacia atrás.
- **`<minor>`**: Nuevas funcionalidades compatibles con versiones anteriores.
- **`<patch>`**: Corrección de errores o mejoras menores.
- **`<estado>`**: Opcional, indica si es una versión `beta`, `alpha`, o estable.

Ejemplo: `Quickness 1.0.0-beta`.

---

## **Contribuir**

Consulta la [guía de contribución](../docs/guia_de_contribucion.md) para seguir las mejores prácticas al colaborar en este proyecto.

---

## **Soporte**

Si encuentras problemas durante la instalación o ejecución de la aplicación, no dudes en abrir un **Issue** en el repositorio o contactar al equipo técnico.

---

Con esta guía, deberías estar listo para configurar y desarrollar la aplicación móvil de **Quickness**. ¡Gracias por contribuir!