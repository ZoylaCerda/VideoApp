
---

# **VideoApp: Aplicación de Grabación y Reproducción de Videos**

Esta aplicación permite grabar videos utilizando la cámara del dispositivo, almacenarlos en la memoria interna y reproducirlos dentro de la misma aplicación. La interfaz está implementada con **Jetpack Compose**, y utiliza **CameraX** y **ExoPlayer** para las funcionalidades de grabación y reproducción de videos.

---

## **Características**

- **Grabar videos** con la cámara del dispositivo utilizando CameraX.
- **Guardar los videos** en el almacenamiento interno del dispositivo.
- **Listar videos grabados** y permitir su reproducción desde la lista.
- **Reproducir videos** utilizando ExoPlayer.
- **Interfaz moderna** con Jetpack Compose para una navegación fluida.

---

## **Requisitos**

Antes de ejecutar la aplicación, asegúrate de cumplir con los siguientes requisitos:

### **Permisos Necesarios**
Esta aplicación requiere los siguientes permisos:

1. **Permisos de cámara** para grabar videos.
2. **Permisos de grabación de audio** para capturar audio junto con los videos.
3. **Permisos de almacenamiento** (lectura y escritura) para guardar y acceder a los videos grabados.

Agrega los siguientes permisos en tu **`AndroidManifest.xml`**:

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

> **Nota**: Para Android 10 y superior, agrega también la siguiente línea dentro de la etiqueta `<application>`:

```xml
<application
    android:requestLegacyExternalStorage="true"
    ... >
</application>
```

## **Cómo Ejecutar el Proyecto**

Sigue estos pasos para ejecutar la aplicación en tu entorno local:

### **1. Clonar el Repositorio**
Abre una terminal y ejecuta:

```bash
git clone https://github.com/tu-usuario/VideoApp.git
cd VideoApp
```

### **2. Abrir el Proyecto en Android Studio**
- Abre **Android Studio**.
- Selecciona **Open an Existing Project** y navega a la carpeta donde clonaste el repositorio.
- Espera a que Android Studio sincronice las dependencias del proyecto.

### **3. Ejecutar la Aplicación**
- Conecta un dispositivo Android o utiliza un emulador.
- Haz clic en **Run > Run 'app'** o presiona `Shift + F10`.

> **Nota**: Si estás utilizando un dispositivo físico, asegúrate de habilitar la **depuración por USB** desde las opciones de desarrollador.

---

## **Estructura del Proyecto**

```plaintext
VideoApp/
│
├── app/                  # Código fuente de la aplicación
│   ├── java/com/example/videoapp/
│   │   ├── MainActivity.kt        # Actividad principal
│   │   ├── ui/
│   │   │   ├── view/              # Vistas de Jetpack Compose
│   │   │   │   ├── AppNavHost.kt  # Navegación principal
│   │   │   │   ├── VideoPlayerScreen.kt # Pantalla de reproducción
│   │   │   │   ├── VideoRecorderScreen.kt # Pantalla de grabación
│   │   │   └── viewmodel/         # ViewModels
│   │   │       └── VideoViewModel.kt # Lógica del estado
│   │   └── data/                  # Repositorio para gestionar archivos de video
│   │       └── VideoRepository.kt
│   └── AndroidManifest.xml       # Archivo de configuración de la app
│
└── README.md                     # Documentación del proyecto
```

---

## **Funcionalidades**

1. **Grabar Video**:
   - Inicia la grabación de un video usando la cámara del dispositivo.
   - Guarda automáticamente el video al detener la grabación.

2. **Listar Videos Grabados**:
   - Muestra una lista de todos los videos grabados almacenados en la memoria interna.

3. **Reproducir Video**:
   - Permite seleccionar cualquier video de la lista y reproducirlo con ExoPlayer.

4. **Volver a la lista desde la pantalla de reproducción**:
   - Incluye un botón de **volver** en la pantalla del reproductor para regresar a la lista de videos.

---

## **Posibles Errores y Soluciones**

### **1. La cámara no se abre o la grabación falla**
- Verifica que los permisos de **cámara y audio** estén concedidos.
- Asegúrate de que la cámara del dispositivo esté funcionando correctamente.

### **2. Los videos no aparecen en la lista**
- Verifica que los archivos de video se guarden en el directorio correcto.
- Asegúrate de que los permisos de **almacenamiento** estén concedidos.

### **3. La reproducción del video falla**
- Asegúrate de que ExoPlayer esté correctamente configurado.
- Verifica que los archivos de video no estén corruptos o vacíos.

---

## ¡Gracias! ✨👋
