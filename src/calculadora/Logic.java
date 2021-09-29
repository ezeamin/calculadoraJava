/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import javax.swing.JTextArea;

/**
 *
 * @author EZEA2
 */
public class Logic {
    private boolean isComa; //Trabajar decimales y negativos
    private Posfijo pos;
    private String cadena;
    private double res;
    private boolean entry;
    
    Logic(JTextArea entrada,JTextArea resultado){
        entrada.setText("0");
        resultado.setText("0");
        cadena="";
        pos=new Posfijo();
        res=0;
        entry=false;
    }
    
    public void send(String str,JTextArea entrada,JTextArea resultado){
        if(res!=0){
            if(!Character.isDigit(str.charAt(0))){
                if(checkDouble(res)){
                    cadena+=String.valueOf((int)res);
                }
                else cadena+=String.valueOf(res);
            }
            res=0;
        }
        else if("".equals(cadena)) resultado.setText("0");
        cadena+=str;
        entrada.setText(cadena);
    }

    public boolean checkDouble(double _num){
        int temp1=(int)_num;
        double temp2=_num-temp1;
        
        if (temp2 > 0) {
            return false;
        }
        return true;
    }
    
    public void deleteOne(JTextArea entrada,JTextArea resultado){
        if("".equals(cadena)){
            entrada.setText("0");
            if(res!=0 && entry) {
                resultado.setText("0");
                res=0;
                cadena="";
            }
            entry=true;
        }
        else{
            cadena=cadena.substring(0, cadena.length()-1);
            if("".equals(cadena)) entrada.setText("0");
            entrada.setText(cadena);
        }
        
    }
    
    private boolean syntaxError(){
        //ver negativos

        return !Character.isDigit(cadena.charAt(cadena.length()-1)) || !Character.isDigit(cadena.charAt(0));
    }
    
    public void total(JTextArea entrada,JTextArea resultado){
        if(res!=0) {} 
        else if(syntaxError()){
            resultado.setText("Syntax error");
        }
        else{
            res=pos.convertir(cadena);
            if(checkDouble(res)){
                resultado.setText(String.valueOf((int)res));
            }
            else resultado.setText(String.valueOf(res));
            cadena="";    
            entry=false;
        }
    }
    
    /*private void imprimir(JTextArea entrada,JTextArea resultado){
        numActive=checkNum();
        //limpiarRes(resultado);
        
        if(isComa && lastDigit()==0){ //Imprimir "0" segun se ingresen (porque los 0 no aportan valor hasta que se ingresa otro numero)
            if(n==0 && numActive==0 && num1!=0) entrada.append("0."); 
            else if (n==0) entrada.append(".");
            else entrada.append("0");
        }
        else if(operador!=null){
            if(checkDouble(numActive)){
                if(checkDouble(num1)){
                    if(num2==0) {
                        if(isComa && n==0) entrada.setText(String.valueOf((int)num1)+" "+operador+" 0.");
                        else entrada.setText(String.valueOf((int)num1)+" "+operador+" ");
                    }
                    else entrada.setText(String.valueOf((int)num1)+" "+operador+" "+String.valueOf((int)numActive)); 
                }
                else{
                    if(num2==0) {
                        if(isComa && n==0) entrada.setText(String.valueOf(num1)+" "+operador+" 0.");
                        else entrada.setText(String.valueOf(num1)+" "+operador+" ");
                        /*System.out.println("num: "+num1);
                        System.out.println(String.valueOf(num1)+" "+operador+" ");
                    }
                    else entrada.setText(String.valueOf(num1)+" "+operador+" "+String.valueOf((int)numActive));
                }
            }
            else{
                if(checkDouble(num1)){
                    if (num2==0){
                        if(isComa && n==0) entrada.setText(String.valueOf((int)num1)+" "+operador+" 0.");
                        else entrada.setText(String.valueOf((int)num1)+" "+operador+" ");
                    }
                    else entrada.setText(String.valueOf((int)num1)+" "+operador+" "+String.valueOf(numActive));
                }
                else{
                    if (num2==0) {
                        if(isComa && n==0) entrada.setText(String.valueOf(num1)+" "+operador+" 0.");
                        else if(isComa && n==0) entrada.setText(String.valueOf(num1)+" "+operador+" ");
                    }
                    else entrada.setText(String.valueOf(num1)+" "+operador+" "+String.valueOf(numActive));
                }
            }
        }
        else if(isComa && n==0){
            if(checkDouble(numActive)) entrada.setText(String.valueOf((int)numActive)+".");
            else entrada.setText(String.valueOf(numActive)+".");
        }
        else{
            if(checkDouble(numActive)) entrada.setText(String.valueOf((int)numActive));
            else entrada.setText(String.valueOf(numActive));
        }
    }*/
    /*
    private int lastDigit(){
        int temp=(int)(numActive*pow(10,n));
        return temp%=10;
    }
    
    public void imprimirRes(JTextArea resultado){      
        if(checkDouble(total)) resultado.setText(String.valueOf((int)total));
        else { //Dejar solo 3 lugares tras la coma
            DecimalFormat numberFormat = new DecimalFormat("#.000");
            String temp=numberFormat.format(total);
            total=Double.parseDouble(temp.replace(',', '.'));
            resultado.setText(String.valueOf(total));
        }
    }
    
    public void limpiarRes(JTextArea resultado){
        resultado.setText("0");
    }
    
    /*public void oper(String op,JTextArea entrada,JTextArea resultado){ //y si hago doble operador?
        if(operador==null){
            operador=op;
            isComa=false;
            n=0;
            imprimir(entrada,resultado);
        }
        else{ //doble operador
            total(entrada,resultado);
            operador=op;
            imprimir(entrada,resultado);
        }
    }*/
    
    /*Operaciones*/
    
    
    /*public void total(JTextArea entrada,JTextArea resultado){
        //Si no hay nada...
        
        if(operador==null){
            total=numActive;
        }
        else{
            switch(operador){
                case "+":{
                    total=num1+num2;
                    break;
                }
                case "-":{
                    total=num1-num2;
                    break;
                }
                case "*":{
                    total=num1*num2;
                    break;
                }
                case "/":{
                    total=num1/num2;
                    break;
                }
            }
        }
        
        imprimirRes(resultado);
        limpiar(entrada);
        num1=total;
    }*/
    
    /*public void limpiar(JTextArea entrada){
        num1=0;
        num2=0;
        operador=null;
        numActive=0;
        isComa=false;
        n=0;
        
        entrada.setText("0");
    }*/
    
    /*public void coma(JTextArea entrada,JTextArea resultado){
        if(total==num1 && operador==null && !isComa){
            limpiar(entrada);
            limpiarRes(entrada);
        }
        isComa=true;
        imprimir(entrada,resultado);
    }*/
}
