/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

/**
 *
 * @author EZEA2
 */
public class Posfijo {
    private final Pila s;
    private final PilaDouble s2;
    private String posfijo;
    private String cadNum;
    
    Posfijo(){
        s=new Pila(10);
        s2=new PilaDouble(10);
        posfijo="";
        cadNum="";
    }
    
    public void reset(){
        s.reset();
        s2.reset();
        posfijo="";
        cadNum="";
    }
    
    public double convertir(String entrefijo){
        entrefijo=entrefijo.concat("$");
        char x;
        
        System.out.println(""+entrefijo);
        reset();

        s.add('$');
        for(int i=0;true;i++){ //Se repite siempre, y va sumando i en 1 para ir al siguiente caracter  
            x=entrefijo.charAt(i);
            
            if(x=='-' && "".equals(cadNum)){
                cadNum+=Character.toString(x);
                continue;
            } //numeros negativos
            
            if(Character.isDigit(x) || x=='.') cadNum+=Character.toString(x);
            else{
                if(!"".equals(cadNum)){
                    posfijo+=cadNum;
                    posfijo+=",";
                    cadNum="";
                }

                switch(x){
                    case ')':{
                        while(s.getLast()!='('){
                            if (posfijo==null) posfijo=Character.toString(x);
                            else posfijo+=Character.toString(s.pop());
                            
                            posfijo+=",";
                        }
                        Character.toString(s.pop()); //elimino el '(' de la pila
                        /*posfijo+=Character.toString(s.pop()); //Imprimir '(', ya que el while lo va a saltear al encontrarlo
                        posfijo+=",";*/
                        break;
                    }
                    case '$':{
                        while(s.getTope()>0){
                            if (posfijo==null) posfijo=Character.toString(x);
                            else posfijo+=Character.toString(s.pop());
                            
                            posfijo+=",";
                        }
                        posfijo+=Character.toString('$');
                        System.out.println("Pos: "+posfijo);
                        return evaluar();
                    }
                    default:{
                        while(s.getPP()>=getPE(x)){
                            posfijo+=Character.toString(s.pop());
                            posfijo+=",";
                        }
                        s.add(x);
                    }
                }
            } 
        }
    }
    
    private int getPE(char x){
        switch(x){
            case '↑':{
                return 4;
            }
            case '*':
            case '/':{
                return 2;
            }
            case '+':
            case '-':{
                return 1;
            }
            case '(':{
                return 4;
            }
        }
        return 0;
    }
    
    public double evaluar(){
        char x;
        String ret;
        double resultado;
        
        for(int i=0;true;i++){
            ret=separar(); //obtengo el siguiente substr (valores entre comas)
            
            if(checkNumeric(ret)){ //mi substr puede ser un operando o un operador
                s2.add(Double.parseDouble(ret));
            }
            else{ //caso de operadores
                x=ret.charAt(0); //si es operador, es un unico caracter y lo trabajo como tal utilizando la variable x
                switch(x){
                    case '$':{
                        resultado=s2.pop();
                        if(resultado==-9999999) return 0; //s2.pop() devuelve -9999999 cuando la pila esta vacia 
                        return resultado;
                    }
                    default:{
                        double b=s2.pop(); //Extraigo primero b y luego a para obtener el orden original
                        double a=s2.pop();

                        switch(x){ 
                            case '+':{
                                s2.add(a+b);
                                break;
                            }
                            case '-':{
                                s2.add(a-b);
                                break;
                            }
                            case '*':{
                                s2.add(a*b);
                                break;
                            }
                            case '/':{
                                s2.add(a/b);
                                break;
                            }
                            case '↑':{
                                s2.add(Math.pow(a,b));   
                            }
                        }
                    }
                }
            }
        }
    }
    
    private String separar(){
        if(posfijo==null) return null; //caso que el string venga vacio
        
        String substr;
        int i2=999;
        
        for(int i=0;i<posfijo.length();i++){
            if(posfijo.charAt(i)==','){ //Llegar hasta la siguiente coma y cortar
                 i2=i;
                 break;
            } 
            
            if(posfijo.charAt(i)=='$') return "$"; //Llega al final del string
        }
        
        substr=posfijo.substring(0,i2);
        posfijo=posfijo.substring(i2+1); //preparo posfijo para siguiente vuelta, sacandole lo que ya esta analizado

        return substr;
    }
    
    private boolean checkNumeric(String temp){
        if(temp==null) return false;
        
        try{
            double d=Double.parseDouble(temp);
        }
        catch(Exception e){
            return false;
        }
        
        return true;
    }
}

