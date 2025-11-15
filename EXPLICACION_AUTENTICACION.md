# Explicación del Sistema de Autenticación y Registro

Este documento detalla el funcionamiento del sistema de inicio de sesión (Login) y registro (Sign In) de la aplicación, y cómo se integra con Spring Security.

## Componentes Principales

El sistema se basa en la interacción de los siguientes componentes clave:

1.  **`SecurityConfig.java` (Configuración de Seguridad):**
    *   Es el cerebro de la seguridad. Define qué rutas son públicas (como `/login`, `/register`) y cuáles requieren autenticación.
    *   Configura el `PasswordEncoder` (usando `BCrypt`) que se usará para encriptar y verificar contraseñas.
    *   Establece el formulario de login (`.formLogin()`) y le dice a Spring Security dónde está la página de login y a dónde redirigir al usuario tras un inicio de sesión exitoso.

2.  **`CustomUserDetailsService.java` (Servicio de Detalles de Usuario):**
    *   Actúa como un puente entre Spring Security y la base de datos de usuarios.
    *   Implementa el método `loadUserByUsername`, que Spring Security usa durante el login para buscar un usuario por su email.

3.  **`AuthController.java` (Controlador de Autenticación):**
    *   Maneja las peticiones HTTP para mostrar las páginas de login y registro.
    *   Contiene la lógica para procesar el formulario de registro: recibe los datos del nuevo usuario, encripta la contraseña y lo guarda en la base de datos.

4.  **`UsuarioRepository.java` (Repositorio):**
    *   Interfaz de Spring Data JPA que permite realizar operaciones CRUD (Crear, Leer, Actualizar, Borrar) sobre la tabla `usuarios` en la base de datos.

5.  **`login.html` y `registro.html` (Vistas):**
    *   Son las plantillas de Thymeleaf que renderizan los formularios de HTML para que el usuario interactúe con ellos.

---

## Flujo 1: Registro de un Nuevo Usuario (Sign In)

Este es el proceso paso a paso cuando un nuevo usuario crea una cuenta:

1.  **Acceso a la Página:** El usuario está en la página de `login.html` y hace clic en el enlace "**Crear una cuenta**".
2.  **Mostrar Formulario:** El navegador realiza una petición `GET` a `/register`. El `AuthController` recibe esta petición y devuelve la vista `registro.html`.
3.  **Envío de Datos:** El usuario completa el formulario (nombre, email, contraseña) y hace clic en "**Registrarse**".
4.  **Procesamiento en Backend:** El navegador envía una petición `POST` a `/register` con los datos del formulario.
5.  **Lógica del Controlador:** El método `processRegister` en `AuthController` se ejecuta:
    *   Primero, usa `usuarioRepository.findByEmail()` para verificar si ya existe un usuario con ese correo. Si es así, devuelve un error.
    *   Si el correo no existe, utiliza el `passwordEncoder` para encriptar la contraseña que el usuario introdujo (`passwordEncoder.encode(password)`).
    *   Crea un nuevo objeto `Usuario` con los datos y la contraseña ya encriptada.
    *   Usa `usuarioRepository.save()` para persistir el nuevo usuario en la base de datos.
6.  **Redirección:** Finalmente, el controlador redirige al usuario de vuelta a la página de `/login`, mostrando un mensaje de que la cuenta fue creada con éxito.

## Flujo 2: Inicio de Sesión de un Usuario Existente (Login)

Este flujo es manejado en su mayor parte por la magia de Spring Security:

1.  **Envío de Credenciales:** El usuario introduce su email y contraseña en `login.html` y hace clic en "**Iniciar Sesión**".
2.  **Intercepción de Spring Security:** El navegador envía una petición `POST` a `/login`. **Importante:** Esta petición no es manejada por nuestro `AuthController`, sino que es interceptada automáticamente por el filtro de autenticación de Spring Security (configurado en `SecurityConfig` con `.formLogin()`).
3.  **Búsqueda del Usuario:** Spring Security invoca a nuestro `CustomUserDetailsService`. Llama al método `loadUserByUsername()` pasándole el email que el usuario escribió.
4.  **Acceso a la Base de Datos:** Nuestro `CustomUserDetailsService` usa `usuarioRepository.findByEmail()` para buscar al usuario en la base de datos.
    *   Si no lo encuentra, lanza una excepción `UsernameNotFoundException`.
    *   Si lo encuentra, devuelve un objeto `UserDetails` que contiene el email, la **contraseña encriptada** almacenada en la base de datos y el rol del usuario.
5.  **Verificación de Contraseña:** Spring Security recibe el objeto `UserDetails`. Ahora tiene dos contraseñas:
    *   La que el usuario escribió en el formulario (en texto plano).
    *   La que está en la base de datos (encriptada).
    Usa el `passwordEncoder` para compararlas de forma segura (`passwordEncoder.matches(contraseña_plana, contraseña_encriptada)`).
6.  **Acceso Concedido o Denegado:**
    *   **Si las contraseñas coinciden:** Spring Security crea una sesión para el usuario, lo considera autenticado y lo redirige a la página `/index` (según lo configurado en `.defaultSuccessUrl()`).
    *   **Si no coinciden:** Spring Security lo redirige de vuelta a `/login` con un parámetro de error para que se muestre un mensaje de "credenciales incorrectas".

---

## Conclusión

Como se puede ver, las responsabilidades están bien separadas. El `AuthController` se encarga del registro, mientras que Spring Security, con la ayuda del `CustomUserDetailsService` y el `PasswordEncoder`, se encarga de todo el proceso de autenticación de forma segura y automática.

---

## Anexo: `SetupController` (Componente de Uso Único)

Durante el desarrollo, surgió un problema que impedía el arranque de la aplicación: la base de datos, al tener un límite de conexiones muy estricto, rechazaba las operaciones de inicialización que se ejecutaban al inicio.

Para solucionar esto, se creó temporalmente el `SetupController`.

### Propósito y Funcionalidad

El `SetupController` exponía un único endpoint, `GET /setup`, que movía la lógica de inicialización fuera del arranque de la aplicación para poder ser ejecutada manualmente una sola vez. Sus tareas eran:

1.  **Crear el Usuario Administrador:** Verificaba si el usuario `admin@mail.com` existía y, si no, lo creaba con una contraseña por defecto (`admin123`) y el rol `admin`.
2.  **Hashear Contraseñas Antiguas:** Recorría la base de datos en busca de usuarios cuyas contraseñas estuvieran en texto plano y las encriptaba usando `BCrypt`.

### ¿Por Qué Borrarlo Después de Usarlo?

Una vez que el endpoint `/setup` se ha ejecutado con éxito, es **crítico por seguridad** eliminar el `SetupController.java`. Las razones son:

1.  **Reducción de la Superficie de Ataque:** Cada endpoint es una "puerta" a la aplicación. Eliminar los que no son necesarios minimiza los puntos de entrada para posibles atacantes.
2.  **Evitar la Reintroducción de Contraseñas Débiles:** Si el controlador se mantiene, un atacante (o alguien por error) podría acceder a `/setup` en el futuro. Si la cuenta `admin@mail.com` fue eliminada, esto la recrearía con la contraseña débil y conocida "admin123", creando una vulnerabilidad de seguridad.
3.  **Principio de Código Limpio:** Es una buena práctica de desarrollo eliminar el código que ya ha cumplido su propósito y no forma parte de la funcionalidad principal de la aplicación.

En resumen, el `SetupController` fue una herramienta de migración y configuración de un solo uso. Mantenerlo activo representa un riesgo de seguridad innecesario.
