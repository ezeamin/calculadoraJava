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
    private Pila s;
    private PilaDouble s2;
    private String posfijo;
    
    Posfijo(){
        s=new Pila(10);
        s2=new PilaDouble(10);
        posfijo="";
    }
    
    public void reset(){
        s.reset();
        s2.reset();
        posfijo="";
    }
    
    public double convertir(String entrefijo){
        entrefijo=entrefijo.concat("$");
        char x;
        
        reset();

        s.add('$');
        for(int i=0;true;i++){ //Se repite siempre, y va sumando i en 1 para ir al siguiente caracter  
            x=entrefijo.charAt(i);
            
            if(Character.isDigit(x)){
                if ("".equals(posfijo)) posfijo=Character.toString(x); //posfijo empieza como null, por lo que debo inicializarlo con un valor
                else posfijo+=Character.toString(x); //concateno a posfijo el caracter que extraí recien
            }
            else{
                switch(x){
                    /*case ')':{ //tira error
                        while(s.getLast()!='('){
                            if (posfijo==null) posfijo=Character.toString(x);
                            else posfijo+=Character.toString(s.pop());
                        }
                        posfijo+=Character.toString(s.pop()); //Imprimir '(', ya que el while lo va a saltear al encontrarlo
                        break;
                    }*/
                    case '$':{
                        while(s.getTope()>0){
                            if (posfijo==null) posfijo=Character.toString(x);
                            else posfijo+=Character.toString(s.pop());
                        }
                        posfijo+=Character.toString('$');
                        System.out.println(""+posfijo);
                        return evaluar();
                    }
                    default:{
                        while(s.getPP()>=getPE(x)){
                            if ("".equals(posfijo)) posfijo=Character.toString(x);
                            else posfijo+=Character.toString(s.pop());
                        }
                        s.add(x);
                    }
                }
            }
        }
    }
    
    private int getPE(char x){
        switch(x){
            case '^':{
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
            /*case '(':{
                return 4;
            }*/
        }
        return 0;
    }
    
    public double evaluar(){
        char x;
        double resultado;
        
        for(int i=0;true;i++){
            x=posfijo.charAt(i);
            
            if(Character.isDigit(x)){
                s2.add((double)(x-48)); //Agrego a la pila el numero (que se leyo como char y debe ser convertido)
            }
            else {
                switch(x){
                    case '$':{
                        resultado=s2.pop();
                        return resultado;
                    }
                    default:{
                        double b=s2.pop(); //Extraigo primero b y luego a para obtener el orden original
                        double a=s2.pop();

                        switch(x){ //case '('????????
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
                            case '^':{
                                s2.add(Math.pow(a,b));   
                            }
                        }
                    }
                }
            }
        }
    }
}
