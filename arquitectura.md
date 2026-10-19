# Arquitectura del proyecto

## Estado actual

El código Java activo está en `ProyectoFinal/` un proyecto con jdk 15. Por ahora, esa clase solo imprime `Hello World!`; los paquetes `menu`, `helpers` y `minijuegos` están creados, pero todavía no contienen código fuente.

## Organización propuesta

```text
ProyectoFinal/
  pom.xml
  src/
    main/java/syntaerror/proyectofinal/
      ProyectoFinal.java # main
      menu/                 # navegación y opciones del menú
      minijuegos/           # reglas y flujo de cada juego
        Ahorcado/
        PiedraPapelTijera/
        TaTei/
      helpers/              # utilidades compartidas entre juegos 
```

El flujo recomendado es `ProyectoFinal.main()` → menú principal → minijuego elegido. El menú se ocupa de la interacción y navegación; cada minijuego mantiene sus propias reglas y estado; `helpers` contiene únicamente funcionalidad realmente compartida. Así se evita que el menú concentre las reglas de todos los juegos o que los juegos dependan entre sí.

Esta es una guía para completar el esqueleto actual, no una descripción de funcionalidades ya implementadas. Conviene mantener los nombres de paquete en minúsculas conforme a las convenciones de Java (por ejemplo, `minijuegos.ahorcado`).


## Instalación y uso

### Requisitos

- JDK 15 instalado.
- Apache NetBeans con soporte para proyectos Maven.
- Maven disponible en NetBeans o instalado para usarlo desde una terminal.

NetBeans puede necesitar una versión de JDK más nueva para ejecutarse. Eso no impide usar JDK 15 para este proyecto: se registra como plataforma Java del proyecto.

### Configuración en NetBeans

1. En NetBeans, abrir `Tools > Java Platforms > Add Platform` y seleccionar la carpeta donde está instalado el JDK 15.
2. Abrir `File > Open Project` y seleccionar la carpeta `ProyectoFinal`, donde está el `pom.xml`.
3. Esperar a que NetBeans cargue el proyecto Maven y comprobar en sus propiedades que la plataforma seleccionada sea JDK 15. El POM configura `maven.compiler.release` en `15`.

### Ejecutar y compilar

- Para ejecutar la aplicación, abrir `src/main/java/syntaerror/proyectofinal/ProyectoFinal.java` y elegir `Run File`. Actualmente imprime `Hello World!`; el menú y los minijuegos todavía deben implementarse.
- Para compilar y ejecutar las pruebas desde una terminal ubicada en `ProyectoFinal`, usar `mvn clean test`.
- Para comprobar qué Java usa Maven desde la terminal, ejecutar `mvn -v`; debe indicar JDK 15 si se busca compilar usando esa instalación exacta. El POM limita la API de compilación a Java 15 incluso cuando Maven se ejecuta con un JDK más nuevo compatible.

