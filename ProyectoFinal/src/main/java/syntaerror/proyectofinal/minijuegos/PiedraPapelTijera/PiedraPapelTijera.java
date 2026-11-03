
package syntaerror.proyectofinal.minijuegos.PiedraPapelTijera;

import javax.swing.JOptionPane;
import syntaerror.proyectofinal.helpers.MenuHelper;

public class PiedraPapelTijera {
    int opcion, playUser, playPc, puntosUser, puntosPc;
    String nombreUser, nombrePc, resultado;
    
   public void main() {

    JOptionPane.showMessageDialog(null,
            "Juego elegido:\n" +
            "---- PIEDRA, PAPEL O TIJERA ----");

    do {

        opcion = MenuHelper.solicitarOpcion();

        switch (opcion) {

            case 1:
                this.opcionUno();
                break;

            case 2:
                /*this.opcionDos();*/
                break;

            case 3:
                JOptionPane.showMessageDialog(null,
                        "Gracias por jugar. ¡Hasta pronto!");
                break;

            default:
                JOptionPane.showMessageDialog(null,
                        "Opción incorrecta. Seleccioná 1, 2 o 3.");
                break;
        }

    } while (opcion != 3);
}
    
    public void opcionUno(){
        String eleccionUser, eleccionPc;
        eleccionUser = "";
        eleccionPc = "";
        playUser = Integer.parseInt(JOptionPane.showInputDialog("Elegí tu jugada:\n"+
                     "1. Piedra\n" +
                     "2. Papel\n" +
                     "3. Tijera"));
        while(playUser < 1 || playUser > 3){
        playUser = Integer.parseInt(JOptionPane.showInputDialog("Jugada Invalida. Elegí tu jugada:\n"+
                     "1. Piedra\n" +
                     "2. Papel\n" +
                     "3. Tijera\n"));
        }
        // Número entero entre 1 y 3 (piedra=1, papel=2, tijera=3)
        playPc = (int) (Math.random() * 3) + 1;         
        
        switch(playUser){ //Asignamos los valores para eleccionUser
            case 1:
                eleccionUser = "Piedra";
                break;
            case 2:
                eleccionUser = "Papel";
                break;
            case 3:
                eleccionUser = "Tijera";
                break;
        }
        
        switch(playPc){ //Asignamos los valores para eleccionPc
            case 1:
                eleccionPc = "Piedra";
                break;
            case 2:
                eleccionPc = "Papel";
                break;
            case 3:
                eleccionPc = "Tijera";
                break;
        }
        
        JOptionPane.showMessageDialog(null, "Vos: "+ eleccionUser);
        JOptionPane.showMessageDialog(null, "Pc: "+ eleccionPc);
        //Condicionamos los diferentes resultados
        if (playUser == playPc){
            this.resultado = "Empate";
        }else{
            if ((playUser == 1 && playPc == 3) ||
                (playUser == 2 && playPc == 1) ||
                (playUser == 3 && playPc == 2)){
                
                resultado = "Ganaste!!";
            }else{
                resultado = "Perdiste...";
            }
        }
        JOptionPane.showMessageDialog(null, "Resultado: "+resultado);
    }
}
