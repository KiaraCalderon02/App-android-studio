# 📱 Android Blog App — Login, Registro, Blogs y Perfil

Aplicación móvil desarrollada en **Android Studio** utilizando **Java** y **XML**.  
La app consume datos desde una **API REST en Node.js**, permitiendo gestionar usuarios y mostrar contenido dinámico de blogs.

---

## 🚀 Características principales

### 🔐 Autenticación de Usuarios
- **Login** con validación básica.
- **Registro** de nuevos usuarios con datos enviados a la API.
- Manejo de **sesiones locales** utilizando `SharedPreferences`.

### 🏠 Pantalla Principal
- Vista inicial tras iniciar sesión.
- Acceso rápido a:
  - Lista de blogs
  - Perfil de usuario
  - Configuración / Logout

### 📰 Página de Blogs
- Los blogs se muestran en un **RecyclerView**.
- Datos obtenidos desde una API en **Node.js** mediante peticiones HTTP (`Volley` o `Retrofit`, según el proyecto).
- Incluye imagen, título, fecha y descripción breve.

### 📄 Vista Individual de Blog
- Cada blog tiene una pantalla dedicada.
- Muestra:
  - Imagen principal
  - Título
  - Contenido completo
  - Autor

### 👤 Ventana de Usuario
- Muestra información del usuario autenticado.
- Posibilidad de cerrar sesión.

---

## 🛠️ Tecnologías Utilizadas

- **Java**
- **XML** (layouts)
- **ConstraintLayout**, **LinearLayout**, **ScrollView**, etc.
- **RecyclerView**
- **Picasso / Glide** para carga de imágenes (dependiendo del proyecto)
- **SharedPreferences** para manejo de sesión
- **Volley / Retrofit** para consumo de API