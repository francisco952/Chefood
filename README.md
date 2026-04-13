# Chefood

Este repositorio corresponde al proyecto grupal. La idea de este README es dejar claro qué ya está hecho y cómo seguir avanzando sin pisarnos entre ramas.

## Qué se implementó hasta ahora

### 1) Módulo de Video

- Se creó un listado de recetas en formato tarjeta tipo feed.
- Cada tarjeta muestra imagen, nombre, descripción y calorías.
- Al tocar una tarjeta se abre un modal con reproducción de video, nombre, descripción, botón de favorito y botón de eliminar.
- Favoritos y eliminados quedan guardados entre sesiones.

Archivos principales:

- app/src/main/java/com/gold/chefood/VideoFragment.kt
- app/src/main/java/com/gold/chefood/VideoPlayerDialogFragment.kt
- app/src/main/java/com/gold/chefood/VideoFoodAdapter.kt

### 2) Módulo Web

- Se agregó barra de búsqueda arriba del contenido.
- La búsqueda filtra por nombre, descripción, tipo e ingredientes.
- Los resultados se muestran en tarjetas.
- Al tocar un resultado, se abre dentro de la app en un WebView.
- También se muestra disponibilidad de navegador en el dispositivo.

Archivos principales:

- app/src/main/java/com/gold/chefood/WebFragment.kt
- app/src/main/java/com/gold/chefood/WebResultAdapter.kt
- app/src/main/java/com/gold/chefood/WebViewActivity.kt

## Cómo se están cargando los datos

La app usa un repositorio único para evitar que cada módulo lea datos por su cuenta.

- Repositorio: app/src/main/java/com/gold/chefood/RecipeRepository.kt
- Modelos: app/src/main/java/com/gold/chefood/FoodModels.kt
- Persistencia de estado (favoritos/eliminados): app/src/main/java/com/gold/chefood/RecipeStateStore.kt

Fuente de datos:

1. Si existe RECIPES_API_URL, intenta cargar API remota.
2. Si falla o está vacío, usa fallback local en app/src/main/assets/recipes.json.

Configuración:

- Archivo: gradle.properties
- Propiedad: RECIPES_API_URL=

Ejemplo:

RECIPES_API_URL=https://tu-api.com/recipes

## Punto de integración para el equipo

Para agregar pantallas nuevas sin romper navegación:

1. Crear el Fragment en app/src/main/java/com/gold/chefood.
2. Agregar opción al menú en app/src/main/res/menu/nav_menu.xml.
3. Registrar el item en Home.kt, método createFragmentForMenu(menuId).

Home donde se centraliza navegación:

- app/src/main/java/com/gold/chefood/Home.kt

## Reglas simples para no romper trabajo de otros

- No parsear JSON/API directamente en Fragments.
- No duplicar modelos fuera de FoodModels.kt.
- Si un módulo necesita datos nuevos, primero extender RecipeApiItem y mapear en RecipeRepository.
- Si hay estado que deba mantenerse, usar RecipeStateStore.
- Evitar listas hardcodeadas largas en pantalla.

## Flujo recomendado de trabajo (equipo)

1. Crear rama personal desde web.
2. Hacer cambios pequeños y probables de revisar.
3. Probar assembleDebug antes de subir.
4. Abrir PR con resumen corto: qué cambió, qué probar, qué riesgos hay.

## Checklist antes de entregar módulo

- Compila sin errores.
- Navega desde menú correctamente.
- No rompe Video ni Web.
- Si usa datos, pasa por RecipeRepository.
- Si guarda estado, quedó persistente.

## Notas rápidas de prueba

Prueba offline:

- Dejar RECIPES_API_URL vacío.
- Ejecutar y validar que lee recipes.json.

Prueba online:

- Configurar RECIPES_API_URL.
- Verificar que toma datos remotos y mantiene fallback local en caso de error.
