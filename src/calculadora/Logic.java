/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import static java.lang.Math.pow;
import java.text.DecimalFormat;
import javax.swing.JTextArea;

/**
 *
 * @author EZEA2
 */
public class Logic {
    private double num1; //Dos numeros a operar         //Igual me debe borrar operador (a null)
    private double num2;
    private double total;
    private double numActive;
    private String operador;
    private boolean isComa;
    private int n;
    
    public void init(JTextArea entrada,JTextArea resultado){
        entrada.setText("0");
        resultado.setText("0");
    }
    
    public void send(String str,JTextArea entrada,JTextArea resultado){     
        double _num;
        numActive=checkNum();
        
        if(total==num1 && operador==null && !isComa){
            limpiar(entrada);
            total=0;
        }
        
        if(isComa){
            _num=Integer.parseInt(str);
            _num=_num/cantDecimal();
            numActive+=_num;
            
        }
        else{
            _num=Integer.parseInt(str);
            numActive*=10;
            numActive+=_num;
        }
        
        actualizar();
        imprimir(entrada,resultado);
    }
    
    private int cantDecimal(){
        n++;
        return (int)pow(10,n);
    }
    
    public boolean checkDouble(double _num){
        int temp1=(int)_num;
        double temp2=_num-temp1;
        
        if (temp2 > 0) {
            return false;
        }
        return true;
    }

    private double checkNum(){
        if(operador!=null){
            return num2;
        }
        else return num1;
    }
    
    public double deleteOne(JTextArea entrada,JTextArea resultado){
        numActive=checkNum();
        
        if(n>0){
            double temp=numActive*pow(10,n);
            temp%=10;
            temp/=pow(10,n);
            System.out.println(""+temp);
            numActive-=temp;
            n--;
            
            if(n==0) isComa=false;
        }
        else{
            numActive=(int)(numActive/10);
        }
        
        actualizar();
        entrada.setText(null);
        imprimir(entrada,resultado);
        
        return numActive;
    }
    
    public void actualizar(){     
        if(operador!=null){
            num2=numActive;
        }
        else num1=numActive;
    }
    
    private void imprimir(JTextArea entrada,JTextArea resultado){
        numActive=checkNum();
        limpiarRes(resultado);
        
        if(operador!=null){
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
                        System.out.println(String.valueOf(num1)+" "+operador+" ");*/
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
    
    public void oper(String op,JTextArea entrada,JTextArea resultado){ //y si hago doble operador?
        operador=op;
        isComa=false;
        n=0;
        imprimir(entrada,resultado);
    }
    
    /*Operaciones*/
    
    public void total(JTextArea entrada,JTextArea resultado){
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
    }
    
    public void limpiar(JTextArea entrada){
        num1=0;
        num2=0;
        operador=null;
        numActive=0;
        isComa=false;
        n=0;
        
        entrada.setText("0");
    }
    
    public void coma(JTextArea entrada,JTextArea resultado){
        if(total==num1 && operador==null && !isComa){
            limpiar(entrada);
            limpiarRes(entrada);
        }
        isComa=true;
        imprimir(entrada,resultado);
    }
}
