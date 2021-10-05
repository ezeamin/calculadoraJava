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
                        }
                        posfijo+=Character.toString('$');
                        System.out.println("Pos: "+posfijo);
                        return evaluar();
                    }
                    default:{
                        while(s.getPP()>=getPE(x)){
                            posfijo+=Character.toString(s.pop());
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
        char x,y='!';
        double resultado;
        cadNum=""; //reseteo variable
        
        for(int i=0;true;i++){
            x=posfijo.charAt(i);
   
            if(x==',') continue; //Saltear si es coma
            
            try{
                y=posfijo.charAt(i+1);
            }
            catch(Exception e){}
            
            if(Character.isDigit(x) || (x=='-' && (y!=',' || y!='$'))){
                System.out.println("x="+x+", y="+y);
                while(Character.isDigit(x) || x=='.' || x=='-'){
                    cadNum+=x; //Concateno al numero total
                    i++; //Recorro el string
                    x=posfijo.charAt(i); //Obtengo siguiente posicion (se corta el while si esto arroja un operador o coma)
                }
                s2.add(Double.parseDouble(cadNum)); //Agrego a la pila el numero
                cadNum=""; //Reseteo el String
            }
            /*if(Character.isDigit(x) || x=='-'){
                if (y!=',' || y!='$'){
                    System.out.println("hola");
                }
                else {
                    System.out.println("x="+x+", y="+y);
                
                    while(Character.isDigit(x) || x=='.' || x=='-'){
                        cadNum+=x; //Concateno al numero total
                        i++; //Recorro el string
                        x=posfijo.charAt(i); //Obtengo siguiente posicion (se corta el while si esto arroja un operador o coma)
                    }
                    s2.add(Double.parseDouble(cadNum)); //Agrego a la pila el numero
                    cadNum=""; //Reseteo el String
                }
            }*/
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
