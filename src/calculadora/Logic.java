/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.text.DecimalFormat;
import javax.swing.JTextArea;

/**
 *
 * @author EZEA2
 */
public class Logic {
    private final Posfijo pos;
    private String cadena;
    private double res;
    private boolean entry;
    private boolean hayParentesis;
    private int cont;
    
    Logic(JTextArea entrada,JTextArea resultado){
        entrada.setText("");
        resultado.setText("");
        cadena="";
        pos=new Posfijo();
        res=0;
        entry=false;
    }
    
    public void send(String str,JTextArea entrada,JTextArea resultado){  //metodo que recibe ingresos de la calculadora
        str=checkPi(str,resultado);
        limpiarSyntax(str,resultado);
        
        if(checkOperador(str)) return;

        agregarCero(str,resultado);
        checkDobleOperador(str);
        checkParentesisYNum(str);
        
        cadena+=str;
        entrada.setText(cadena);
        tempTotal(entrada,resultado);
    }
    
    private void checkParentesisYNum(String str){
        if(str.charAt(0)=='('){
            try{
                if(Character.isDigit(cadena.charAt(cadena.length()-1))) cadena+="*";
                else if (cadena.charAt(cadena.length()-1)=='-') cadena+="1*";
            }
            catch(Exception e){}
        }
    }
    
    private String checkPi(String str, JTextArea resultado){
        if("π".equals(str)){
            if(resultado.getText().equals("Syntax error")) str="3.14";
            else if(cadena.length()!=0 && (Character.isDigit(cadena.charAt(cadena.length()-1)))){
                if(cadena.charAt(cadena.length()-1)=='(') str="3.14";   
                else str="*3.14";
            }
            else str="3.14";
        }
        return str;
    }
    
    private boolean checkOperador(String str){//Primer digito un operador, no hacer nada (exceptuar - y .)
        return ("".equals(cadena) && res==0) && (!Character.isDigit(str.charAt(0)) && str.charAt(0)!='-' && str.charAt(0)!='.' && str.charAt(0)!='(');
    }
    
    private void limpiarSyntax(String str, JTextArea resultado){
        if(resultado.getText().equals("Syntax error") || resultado.getText().equals("Math error")){ //limpiar pantalla si se ingresa un operador con syntax error
            if(!Character.isDigit(str.charAt(0)) && str.charAt(0)!='.' && str.charAt(0)!='('){
                cadena="";
                resultado.setText("");
                res=0;
            }
            else resultado.setText("");
        }
    }
    
    private void agregarCero(String str, JTextArea resultado){ //al poner solo '.', agregar un 0
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
    }
    
    private void checkDobleOperador(String str){ //No permite se pongan multiples operadores juntos a menos que sea un menos
        try{
            char x=cadena.charAt(cadena.length()-1);
            if(x==')') return;
            if(str.charAt(0)=='+' || str.charAt(0)=='/' || str.charAt(0)=='*'){ //reemplazo doble operador, considerando el caso negativo
                if(!Character.isDigit(x)) cadena=cadena.substring(0, cadena.length()-1);
            }
            else if(str.charAt(0)=='-' && (x=='+' || x=='-')) cadena=cadena.substring(0, cadena.length()-1);
        }
        catch(Exception e){}
    }
    
    private void tempTotal(JTextArea entrada,JTextArea resultado){ //Total temporal que se va mostrando por pantalla       
        if("".equals(cadena)){
            entrada.setText("");
            resultado.setText("");
        }
        else if(hayParentesis && !parentesisCompletos() && Character.isDigit(cadena.charAt(cadena.length()-1))){
            for(int i=0;i<cont;i++){ //agrego tantos parentesis como falten
                cadena+=")";    
            }   
            double resTemp=pos.convertir(cadena);
            imprimirRes(resTemp,entrada,resultado,false);
            
            for(int i=0;i<cont;i++){ //agrego tantos parentesis como falten
                cadena=cadena.substring(0,cadena.length()-1);   
            } 
        }
        else if(syntaxError()){
        }
        else{
            double resTemp=pos.convertir(cadena);
            if(Double.isNaN(resTemp)) return;
            if(hayOperadores()) imprimirRes(resTemp,entrada,resultado,false);
        }
    }
    
    private boolean hayOperadores(){ //impide que se imprima un resultado temporal sin haber operaciones
        char x;
        
        for(int i=0;i<cadena.length();i++){
            x=cadena.charAt(i);
            if(x=='+' || x=='*' || x=='/' || x=='↑' || (x=='-' && i!=0)) return true;
        }
        
        return false;
    }

    public boolean checkDouble(double _num){ //Detecta si un numero es decimal
        int temp1=(int)_num;
        double temp2=_num-temp1;

        return temp2 == 0;
    }
    
    public void deleteOne(JTextArea entrada,JTextArea resultado){ //"CE"
        if("".equals(cadena) && entry==false) {
            return;
        }
        if(entry==true){
            cadena="";
            res=0;
            entry=false;
        }
        else{
            cadena=cadena.substring(0, cadena.length()-1);
        }

        entrada.setText(cadena);

        tempTotal(entrada,resultado);
        
    }
    
    private boolean doblePunto(){ //Para que salte Syntax error de haber mas de un punto
        boolean entrar=false;
        
        for(int i=0;i<cadena.length();i++){
            if(cadena.charAt(i)=='.' && !entrar){
                entrar=true;
            }
            else if(cadena.charAt(i)=='.'){ //se detecta el doble punto
                return true;
            }
            else if(cadena.charAt(i)=='+' || cadena.charAt(i)=='/' || cadena.charAt(i)=='*'  || cadena.charAt(i)=='-') entrar=false;
        }
        
        return false;
    }
    
    private boolean parentesisCompletos(){
        char x;
        cont=0;
        hayParentesis=false;
        
        for(int i=0;i<cadena.length();i++){
            x=cadena.charAt(i);
            
            if(x=='(') {
                hayParentesis=true;
                cont++;
            }
            else if(x==')') cont--;
        }
        
        return hayParentesis && cont==0;
    }
    
    private boolean syntaxError(){ //devuelve false si esta todo correcto, true si hay syntax error
        char x=cadena.charAt(cadena.length()-1);
        
        if(hayParentesis && !parentesisCompletos()) return true;
        if(!Character.isDigit(x)) {
            if(cadena.charAt(cadena.length()-1)==')') return !parentesisCompletos();
            if(parentesisCompletos()) return true;
            return true;
        }
        return doblePunto();
    }
    
    public void total(JTextArea entrada,JTextArea resultado){
        if(res!=0) {return;}
        else if(hayParentesis && !parentesisCompletos() && Character.isDigit(cadena.charAt(cadena.length()-1))){
            for(int i=0;i<cont;i++){ //agrego tantos parentesis como falten
                cadena+=")";    
            }   
            double resTemp=pos.convertir(cadena);
            imprimirRes(resTemp,entrada,resultado,false);
        }
        else if(syntaxError()){
            resultado.setText("Syntax error");
            return;
        }
        
        res=pos.convertir(cadena); 
        if(Double.isInfinite(res) || Double.isNaN(res)){
            resultado.setText("Math error");
            return;
        }
        res=imprimirRes(res,resultado,entrada,true); //mando al reves resultado y entrada para que me modifique la entrada
        cadena="";    
        entry=true;
    }
    
    public double imprimirRes(double resTemp,JTextArea entrada, JTextArea resultado, boolean total){ //No uso res porque sino se rompe la impresion parcial   
        if(checkDouble(resTemp)) resultado.setText(String.valueOf((int)resTemp));
        else { //Dejar solo 3 lugares tras la coma
            DecimalFormat numberFormat = new DecimalFormat("#.000");
            String temp=numberFormat.format(resTemp);
            resTemp=Double.parseDouble(temp.replace(',', '.'));
            resultado.setText(String.valueOf(resTemp));
        }
        
        if(total) entrada.setText(""); //boolean total indica desde donde se llamo al metodo, y por ende si se quiere reemplazar la entrada con null
        
        return resTemp;
    }
}
