/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.text.DecimalFormat;
import javax.swing.JTextArea; //doble ., *+ se cambie a +, 1er ingreso solo num o coma (no **,/,etc)

/**
 *
 * @author EZEA2
 */
public class Logic {
    private final Posfijo pos;
    private String cadena;
    private double res;
    private boolean entry;
    
    Logic(JTextArea entrada,JTextArea resultado){
        entrada.setText("");
        resultado.setText("");
        cadena="";
        pos=new Posfijo();
        res=0;
        entry=false;
    }
    
    public void send(String str,JTextArea entrada,JTextArea resultado){
        if(resultado.getText().equals("Syntax error")){ //limpiar pantalla si se ingresa un operador con syntax error
            if(!Character.isDigit(str.charAt(0)) && str.charAt(0)!='.'){
                cadena="";
                resultado.setText("0");
                res=0;
            }
        }
        
        if(".".equals(str) && ("".equals(cadena) || !Character.isDigit(cadena.charAt(cadena.length()-1)))) cadena+="0"; //agregar 0 antes de un .
        else if(res!=0){
            if(!Character.isDigit(str.charAt(0))){
                if(checkDouble(res)){
                    cadena+=String.valueOf((int)res);
                }
                else cadena+=String.valueOf(res);
            }
            entry=false;
            res=0;
        }
        else if("".equals(cadena)) resultado.setText("");
        
        
        
        cadena+=str;
        entrada.setText(cadena);
        tempTotal(entrada,resultado);
    }
    
    private void tempTotal(JTextArea entrada,JTextArea resultado){
        if("".equals(cadena)){
            entrada.setText("");
            resultado.setText("");
        }
        else if(syntaxError()){
        }
        else{
            double resTemp=pos.convertir(cadena);
            imprimirRes(resTemp,entrada,resultado,false);
        }
    }

    public boolean checkDouble(double _num){
        int temp1=(int)_num;
        double temp2=_num-temp1;
        
        /*System.out.println("_num= "+_num);
        System.out.println("temp1= "+temp1);
        System.out.println("temp2= "+temp2);*/
        
        if (temp2 != 0) {
            System.out.println("es decimal");
            return false;
        }
        return true;
    }
    
    public void deleteOne(JTextArea entrada,JTextArea resultado){
        if("".equals(cadena) && entry==false) {
            return;
        }
        if(entry==true){
            cadena="";
            res=0;
            entry=false;
            System.out.println("holi");
        }
        else{
            cadena=cadena.substring(0, cadena.length()-1);
            //if("".equals(cadena)) entrada.setText("");
        }

        entrada.setText(cadena);

        tempTotal(entrada,resultado);
        
    }
    
    private boolean dobleOperador(){
        //System.out.println("length="+cadena.length());
        for(int i=0;i<cadena.length();i++){
            if(!Character.isDigit(cadena.charAt(i)) && i!=(cadena.length()-1)){
                if((!Character.isDigit(cadena.charAt(i+1))) && cadena.charAt(i+1)!='-') return true; //hay un doble operador
                //System.out.println("x="+cadena.charAt(i)+",i="+i);
            }
        }
        
        return false;
    }
    
    private boolean doblePunto(){
        boolean entry=false;
        
        for(int i=0;i<cadena.length();i++){
            if(cadena.charAt(i)=='.' && !entry){
                entry=true;
            }
            else if(cadena.charAt(i)=='.'){ //se detecta el doble punto
                return true;
            }
            else if(cadena.charAt(i)=='+' || cadena.charAt(i)=='/' || cadena.charAt(i)=='*'  || cadena.charAt(i)=='-') entry=false;
        }
        
        return false;
    }
    
    private boolean syntaxError(){
        return !Character.isDigit(cadena.charAt(cadena.length()-1)) || (!Character.isDigit(cadena.charAt(0)) && !(cadena.charAt(0)=='-') || dobleOperador() || doblePunto()) ;
    }
    
    public void total(JTextArea entrada,JTextArea resultado){
        if(res!=0) {} 
        else if(syntaxError()){
            resultado.setText("Syntax error");
        }
        else{
            res=pos.convertir(cadena); 
            if(Double.isInfinite(res)){
                resultado.setText("Math error");
                return;
            }
            res=imprimirRes(res,resultado,entrada,true); //mando al reves para que me modifique la entrada
            cadena="";    
            entry=true;
        }
    }
    
    public double imprimirRes(double resTemp,JTextArea entrada, JTextArea resultado, boolean total){ //No uso res porque sino se rompe la impresion parcial   
        if(checkDouble(resTemp)) resultado.setText(String.valueOf((int)resTemp));
        else { //Dejar solo 3 lugares tras la coma
            DecimalFormat numberFormat = new DecimalFormat("#.000");
            String temp=numberFormat.format(resTemp);
            resTemp=Double.parseDouble(temp.replace(',', '.'));
            resultado.setText(String.valueOf(resTemp));
        }
        if(total) entrada.setText("");
        
        return resTemp;
    }
}
