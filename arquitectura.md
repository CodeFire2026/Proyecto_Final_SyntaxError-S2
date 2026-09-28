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
