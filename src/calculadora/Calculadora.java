/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author EZEA2
 */
public class Calculadora {
    private static double num;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Detect app = new Detect();
        
        GUI ventana = new GUI();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
  
        try{
            //javax.swing.UIManager.setLookAndFeel(new com.jtattoo.plaf.hifi.HiFiLookAndFeel());
            javax.swing.UIManager.setLookAndFeel(new com.jtattoo.plaf.mcwin.McWinLookAndFeel());
        }
        catch(Exception e){
            
        }
    }
    
    
}
