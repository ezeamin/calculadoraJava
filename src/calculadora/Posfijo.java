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
    }
    
    public void reset(){
        s.reset();
        s2.reset();
        posfijo="";
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
        String ret;
        double resultado;
        
        for(int i=0;true;i++){
            ret=separar();
            
            if(checkNumeric(ret)){
                s2.add(Double.parseDouble(ret));
            }
            else{
                x=ret.charAt(0);
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
    
    private String separar(){
        if(posfijo==null) return null;
        
        String substr="";
        int i2=999;
        
        for(int i=0;i<posfijo.length();i++){
            if(posfijo.charAt(i)==','){
                 i2=i;
                 if (i2!=0) break;
            } 
            
            if(posfijo.charAt(i)=='$') return "$";
        }
        
        
        if(i2==999){
            return posfijo;
        }
        
        substr=posfijo.substring(0,i2);
        posfijo=posfijo.substring(i2+1);
        if("".equals(posfijo)) posfijo=null;
        
        //System.out.println("i1= "+i1+", i2= "+i2);
        //System.out.println("sub: "+substr);
        //System.out.println("pos: "+posfijo);
        
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

