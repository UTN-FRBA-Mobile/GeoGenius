# GeoGenius

GeoGenius es una aplicación móvil nativa desarrollada para Android en Kotlin, destinada a facilitar la exploración de lugares cercanos y permitir a los usuarios guardar sus sitios favoritos como bookmarks (marcadores). Utiliza Google Maps SDK para mostrar mapas interactivos, y almacena los marcadores en una base de datos SQL. La aplicación también cuenta con funcionalidades de filtrado y un widget que muestra la dirección y la distancia a los bookmarks más cercanos.

## Características

- **Mapa interactivo**: La pantalla principal de GeoGenius muestra un mapa proporcionado por Google Maps SDK, permitiendo al usuario explorar su entorno y ver lugares de interés.
- **Bookmarks**: Los usuarios pueden guardar lugares como bookmarks y estos se almacenan en una base de datos SQL local, facilitando el acceso rápido a sus lugares favoritos. Además, cada bookmark incluye información relevante como puntaje, dirección y distancia.
- **Filtros**: La aplicación permite filtrar los lugares mostrados en el mapa según diferentes categorías, facilitando la búsqueda específica de lugares.
- **Widget de Bookmarks**: GeoGenius incluye un widget que muestra la orientación y distancia a los bookmarks más cercanos, permitiendo al usuario acceder a esta información directamente desde la pantalla de inicio de su dispositivo.
- **Búsqueda y organización**: Incluye una función de búsqueda para encontrar bookmarks y una vista de borrado que permite eliminar bookmarks seleccionados.

## Tecnologías utilizadas

- **Lenguaje**: Kotlin
- **Google Maps SDK**: Para la visualización de mapas interactivos.
- **SQLite**: Para almacenar los bookmarks localmente en el dispositivo.
- **Android Jetpack**: Incluyendo ViewModel, Navigation, y DataStore.
- **Retrofit**: Para la comunicación con APIs externas.
- **Glance AppWidget**: Para la creación del widget que muestra información sobre los bookmarks.

## Dependencias

A continuación, algunas de las dependencias utilizadas en GeoGenius, especificadas en el archivo `build.gradle.kts`:

- **Google Maps SDK**: Para la integración de mapas interactivos.
- **Retrofit** y **Kotlin Serialization**: Para la comunicación con APIs y la serialización de datos JSON.
- **Room**: Para la persistencia de datos utilizando SQLite.
- **Accompanist Pager**: Para la implementación de widgets interactivos en la interfaz.
- **Glance AppWidget**: Para la creación del widget que muestra información de los bookmarks.

## Integrantes
- **Cafici, Felipe** (175.652-7)
- **Ferrante, Ignacio** (171.524-0)
- **Schuhmann, Juan Ignacio** (176.513-9)

Desarrollo de Aplicaciones para Dispositivos Móviles - K5551 (2024 - 2C)

## Información de publicación en Google Play Store

### Título

GeoGenius - Explora y Guarda tus Lugares Favoritos

### Descripción

¡Descubre el mundo que te rodea con GeoGenius! 🌍

GeoGenius es la aplicación perfecta para los amantes de la exploración y la aventura. Con nuestro mapa interactivo, puedes descubrir una variedad de lugares interesantes cerca de ti. Guarda tus lugares favoritos como bookmarks y accede a ellos con facilidad. GeoGenius también incluye filtros para que puedas ver solo lo que realmente te interesa y un widget de pantalla de inicio que te muestra la distancia a tus lugares favoritos de manera rápida y cómoda.

**Características principales**:

- 🌐 **Mapa interactivo**: Explora y encuentra lugares de interés cerca de ti.
- 📍 **Bookmarks**: Guarda tus lugares favoritos y accede a ellos cuando quieras.
- 🎯 **Filtros inteligentes**: Encuentra exactamente lo que buscas, filtrando los lugares mostrados en el mapa según tus preferencias.
- 📲 **Widget de Bookmarks**: Accede rápidamente a la dirección y distancia de tus lugares favoritos directamente desde la pantalla de inicio de tu dispositivo.

Descarga GeoGenius ahora y empieza a explorar!

### Screenshots

**TODO: screenshots**

### ¡Descárgala ahora y empieza a explorar el mundo a tu alrededor!
