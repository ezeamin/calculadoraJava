/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

/**
 *
 * @author Eze
 */
public class Pila {
    private char v[];
    private int tope;
    private int n; //maximo
    
    Pila(int _n){
        n=_n;
        tope=-1;
        v=new char[n];
    }
    
    public void reset(){
        for(int i=0;i<tope;i++){
            v[i]=0;
        }
        tope=-1;
    }
    
    public void add(char x){
        if (tope==n-1){
            //System.out.println("Pila llena"); //Comentadas porque no necesito esta informacion siendo mostrada
            return;
        }
        tope++;
        v[tope]=x;
    }
    
    public char pop(){
        if (tope==-1){
            //System.out.println("Pila vacia"); //Comentadas porque no necesito esta informacion siendo mostrada
            return '!';
        }
        char x=v[tope];
        tope--;
        return x;
    }
    
    public char getLast(){
        return v[tope];
    }
    
    public int getPP(){
        switch(v[tope]){
            case '↑':{
                return 3;
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
                return 0;
            }
        }
        return 0;
    }
    
    public int getTope(){
        return tope;
    }
}
