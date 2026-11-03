# Piedra, Papel o Tijera

## Ejecutar en NetBeans

1. Abrir el proyecto Maven `ProyectoFinal` (la carpeta que contiene `pom.xml`).
2. En Source Packages, abrir el paquete `syntaerror.proyectofinal.minijuegos.PiedraPapelTijera`.
3. Hacer clic derecho en `Main.java` y elegir **Run File** (Shift + F6).
   `Test.java` también inicia este mismo juego.

El punto de entrada general `ProyectoFinal.java` sigue independiente de este minijuego.

## Modalidades y puntuación

- **Una partida:** una ronda; puede terminar en victoria, derrota o empate.
- **Al mejor de 3:** gana quien consiga 2 victorias. Los empates no suman victorias y se vuelve a jugar, por lo que puede haber más de 3 rondas en total.
- El marcador de cada partida comienza en cero.
- Las estadísticas acumulan rondas ganadas, perdidas y empatadas durante la sesión; cada ronda ganada suma un punto.
- Al interrumpir una partida se conservan las estadísticas de las rondas ya jugadas.
- El menú permite consultar y reiniciar las estadísticas. No se guardan al cerrar el programa.
- Cancelar o cerrar un diálogo de entrada vuelve al menú o sale, según la pantalla. Las entradas que no son números se vuelven a solicitar.

## Clases

- `Main`: nombre del jugador y menú principal.
- `Jugador`: nombre, puntos y estadísticas.
- `PiedraPapelTijera`: modalidades, jugadas y resultados con `JOptionPane`.
- `Test`: acceso alternativo para iniciar el juego, sin pruebas automáticas.
