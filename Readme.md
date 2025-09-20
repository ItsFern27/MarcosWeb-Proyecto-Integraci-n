# Plataforma Web Colaborativa - Base de Datos

Este proyecto utiliza **PostgreSQL** como sistema gestor de base de datos.  
Aquí encontrarás los pasos para crear la base de datos localmente y mantenerla sincronizada entre todos los miembros del equipo.

---

## 📂 Estructura de la carpeta `db/`

Dentro del proyecto existe una carpeta llamada **`db/`** que contiene:

- `schema.sql` → Script con la estructura de la base de datos (tablas, relaciones, llaves foráneas).
- `data.sql` → Script opcional con datos de ejemplo (usuarios, proyectos, contactos).

---

## 🚀 Pasos para crear la base de datos

1. Abre **Visual Studio Code** y asegúrate de tener instalada la extensión de PostgreSQL.
2. Conéctate a tu servidor PostgreSQL local (con Docker Desktop abierto).
3. Crea la base de datos:
   ```sql
   CREATE DATABASE integracion_proyectos;

4.Conéctate a la base de datos recién creada:

\c integracion_proyectos;
5.Ejecuta el script de estructura:

\i db/schema.sql;

6.(Opcional) Carga datos de ejemplo:

\i db/data.sql;
