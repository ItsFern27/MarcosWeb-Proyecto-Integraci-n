# 🚀 Mejoras del Proyecto — ProjectLinks

Este documento resume las mejoras implementadas en esta actualización y propone nuevas funcionalidades que podrían añadirse al proyecto en el futuro.

---

## ✅ Mejoras Implementadas

### 🔒 Seguridad
- **Credenciales de base de datos eliminadas del código fuente**: Las credenciales hardcodeadas en `application.yml` fueron eliminadas. Toda la configuración de conexión ahora se gestiona exclusivamente mediante variables de entorno, evitando que contraseñas y URLs queden expuestas en el repositorio.
- **Validación del formulario de registro**: Se agregaron comprobaciones en el servidor para el registro de usuarios: nombre no vacío (máx. 100 caracteres), formato de correo electrónico válido y contraseña de al menos 8 caracteres.

### 🛠️ Manejo de Errores
- **Manejador global de excepciones** (`GlobalExceptionHandler`): Captura los errores HTTP (404, 403, 500) y los redirige a una página de error amigable (`error.html`) en lugar de mostrar la pantalla de error por defecto de Spring.
- **Página de error personalizada** (`error.html`): Muestra al usuario un mensaje claro y un botón de retorno al inicio cuando ocurre cualquier error.

### ✏️ Nueva Funcionalidad: Editar Proyecto
- **Formulario de edición** (`/proyecto/{id}/editar`): El autor de un proyecto puede modificar el título, descripción, duración, URL de imagen y tecnologías asociadas. No está disponible para proyectos ya finalizados.
- **Botón "Editar Proyecto"** visible solo para el autor del proyecto en la página de detalles.

### 🐛 Correcciones de Bugs
- **Bug de visualización de rol en "Mis Proyectos"**: La expresión Thymeleaf que mostraba el rol del usuario en los proyectos donde participaba como miembro siempre comparaba el id del usuario consigo mismo (siempre verdadero). Se corrigió pasando un mapa `rolPorProyecto` desde el controlador.
- **Badge de estado incorrecto**: El badge de estado "En desarrollo" no coincidía con el valor real "En progreso" guardado en la base de datos. Se unificaron los valores y se añadió un badge genérico para estados futuros.

### 📋 Calidad de Código
- **Logging con SLF4J/Logback**: Se agregó `@Slf4j` a los controladores principales (`AuthController`, `ProyectosController`, `MisProyectosController`, `DetalleProyectoController`, `GlobalExceptionHandler`) para registrar eventos relevantes como creación de usuarios, proyectos y errores.
- **Archivo `a.js` eliminado**: Se eliminó un archivo JavaScript de prueba que había quedado en la raíz del proyecto sin ningún propósito.
- **Dependencia `spring-boot-starter-validation`**: Añadida para habilitar Bean Validation en el proyecto.

---

## 💡 Mejoras Futuras Sugeridas

A continuación se listan funcionalidades que podrían añadirse al proyecto para hacerlo más completo y competitivo:

### 👤 Gestión de Perfil de Usuario
- **Editar perfil**: Permitir que el usuario cambie su nombre, foto de perfil y una bio/descripción.
- **Cambiar contraseña**: Formulario para que el usuario actualice su contraseña desde la configuración de su cuenta.
- **Recuperar contraseña**: Flujo de "Olvidé mi contraseña" con enlace de recuperación enviado al correo.

### 📁 Gestión Avanzada de Proyectos
- **Paginación de proyectos**: Dividir la lista de proyectos en páginas para mejorar el rendimiento cuando hay muchos proyectos cargados.
- **Proyectos privados / borradores**: Añadir visibilidad al proyecto (público, privado, borrador) para que el autor pueda trabajar en él antes de publicarlo.
- **Subida de imagen de portada**: En lugar de ingresar una URL externa, permitir subir directamente una imagen desde el dispositivo del usuario (almacenamiento local o en la nube como AWS S3 o Cloudinary).

### 🔔 Notificaciones
- **Notificación al líder cuando alguien se une**: Enviar un correo o una notificación interna al autor del proyecto cuando un nuevo miembro se une.
- **Notificaciones en la aplicación**: Un icono de campanita en la barra de navegación que muestre alertas relevantes (nuevos miembros, cambios de estado, etc.).

### 💬 Comunicación
- **Comentarios en proyectos**: Sección de comentarios en la página de detalles del proyecto para que los miembros y visitantes puedan dejar preguntas o feedback.
- **Mensajería entre usuarios**: Chat privado o mensajes directos entre miembros del mismo proyecto.

### 🔍 Búsqueda y Descubrimiento
- **Filtros avanzados de búsqueda**: Filtrar proyectos por estado (en progreso / finalizado), cantidad de miembros, o fecha de creación.
- **Recomendaciones personalizadas**: Sugerir proyectos según las tecnologías que ya usa el usuario.
- **Explorar por tecnología**: Página dedicada para ver todos los proyectos de una tecnología específica.

### 📊 Estadísticas y Portafolio
- **Portafolio público del usuario**: Página de perfil pública (`/usuario/{id}`) con los proyectos en los que participó, tecnologías que maneja y estadísticas.
- **Estadísticas del proyecto**: Gráficas o indicadores de avance, número de miembros, actividad reciente, etc.

### ⚙️ Administración
- **Panel de administrador**: Una sección `/admin` completa con gestión de usuarios, proyectos y tecnologías disponibles (agregar, editar, eliminar tecnologías).
- **Moderación de contenido**: Permitir al admin eliminar proyectos o usuarios que incumplan las normas.

### 🔐 Seguridad Adicional
- **Protección CSRF habilitada**: Re-habilitar la protección CSRF para los formularios del sitio (todos los formularios ya usan `th:action`, por lo que Thymeleaf inyectaría el token automáticamente).
- **Límite de intentos de login**: Bloquear temporalmente la cuenta tras varios intentos fallidos de inicio de sesión.
- **Verificación de correo electrónico**: Enviar un correo de confirmación al registrarse para validar que el email es real.
