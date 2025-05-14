################################################################################
# REGLAS ESPECÍFICAS PARA KTOR Y SUS DEPENDENCIAS
# Estas reglas son para asegurar que Ktor y las bibliotecas de las que depende
# (como kotlinx.coroutines y kotlinx.serialization) no sean eliminadas o
# alteradas de forma incorrecta por R8.
################################################################################

# --- Reglas para Ktor Client y sus plugins ---
# Mantener las clases principales de los plugins de Ktor que estás usando.
-keep class io.ktor.client.plugins.HttpTimeout$* { *; } # Clases internas de HttpTimeout
-keep class io.ktor.client.plugins.contentnegotiation.ContentNegotiation$* { *; } # Clases internas de ContentNegotiation

# Regla más general para los plugins de Ktor si las específicas no son suficientes.
# Intenta primero con las reglas específicas de arriba. Descomenta esta si sigues teniendo problemas.
# -keep class io.ktor.client.plugins.** { *; }

# Mantener clases y miembros de Ktor que podrían ser accedidos por reflexión o son esenciales.
-keep class io.ktor.client.** { *; }
-keep class io.ktor.http.** { *; }
-keep class io.ktor.util.** { *; }
-keep class io.ktor.utils.io.** { *; } # Para I/O no bloqueante

# Mantener constructores y métodos específicos si es necesario (generalmente cubierto por { *; })
# -keepclassmembers class io.ktor.client.engine.cio.CIOEngine { public <init>(...); }

# --- Reglas para kotlinx.coroutines (Ktor depende mucho de coroutines) ---
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory { *; }
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler { *; }
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler { *; }
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory { *; }
-keepclassmembers class kotlinx.coroutines.flow.** { *; } # Necesario para Flows
-keepclassmembers class kotlinx.coroutines.channels.** { *; } # Necesario para Channels
-keep class kotlinx.coroutines.debug.internal.DebugProbesKt { *; } # Para debugging, puede ser opcional en release estricto
-dontwarn io.ktor.client.plugins.HttpTimeout$HttpTimeoutCapabilityConfiguration
-dontwarn io.ktor.client.plugins.HttpTimeout$Plugin
-dontwarn io.ktor.client.plugins.HttpTimeout
-dontwarn io.ktor.client.plugins.contentnegotiation.ContentNegotiation$Config
-dontwarn io.ktor.client.plugins.contentnegotiation.ContentNegotiation$Plugin
-dontwarn io.ktor.client.plugins.contentnegotiation.ContentNegotiation
-dontwarn java.lang.management.ManagementFactory
-dontwarn java.lang.management.RuntimeMXBean

# --- Reglas para kotlinx.serialization (Ktor ContentNegotiation a menudo usa esto) ---
# Mantener clases anotadas con @Serializable y sus serializadores.
-keep,allowobfuscation @kotlinx.serialization.Serializable class *
-keepclassmembers class * {
    @kotlinx.serialization.KSerializer <methods>;
}
# Mantener todas las clases dentro de kotlinx.serialization, ya que la reflexión se usa a menudo.
-keep class kotlinx.serialization.** { *; }
# Específicamente para JSON si lo usas (muy probable con ContentNegotiation)
-keep class kotlinx.serialization.json.** { *; }
# Mantener atributos necesarios para la serialización.
-keepattributes Signature,InnerClasses,EnclosingMethod,RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations

################################################################################
# OTRAS REGLAS IMPORTANTES
################################################################################

# Si usas DataStore con kotlinx.serialization
# -keep class androidx.datastore.preferences.protobuf.** { *; }

# Asegúrate de que las clases Enum no sean eliminadas si se accede a ellas por nombre (valueOf).
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Mantener clases Parcelable y sus miembros CREATOR.
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# Mantener nombres de clases que se usan en llamadas nativas (JNI).
# -keepclasseswithmembernames class * {
#    native <methods>;
# }

# Mantener constructores de clases que se usan en llamadas nativas (JNI).
# -keepclasseswithmembernames class * {
#    native <init>(...);
# }

################################################################################
# ¡IMPORTANTE!
# 1. Coloca estas reglas en tu archivo `proguard-rules.pro` del módulo `composeApp`.
# 2. ASEGÚRATE de que tu archivo `build.gradle.kts` (o `build.gradle`)
#    en el módulo `composeApp` esté configurado para usar este archivo ProGuard.
#    Ejemplo en build.gradle.kts:
#
#    android {
#        // ...
#        buildTypes {
#            getByName("release") {
#                isMinifyEnabled = true
#                proguardFiles(
#                    getDefaultProguardFile("proguard-android-optimize.txt"),
#                    "proguard-rules.pro" // ¡Verifica este nombre y ruta!
#                )
#            }
#        }
#    }
#
# 3. Después de añadir/modificar estas reglas, realiza:
#    - File > Invalidate Caches / Restart... (Selecciona "Invalidate and Restart")
#    - Build > Clean Project
#    - Build > Rebuild Project
################################################################################
