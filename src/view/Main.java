package view;

import control.Controlador;

public class Main {

    
    public static void main(String[] args) {
        Vista vista = new Vista();
        new Controlador (vista).abrirFormulario();
        
    }
    
}
