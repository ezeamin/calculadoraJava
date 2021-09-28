/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.util.Scanner;

/**
 *
 * @author Eze
 */
public class PilaDouble {
    private double v[];
    private int tope;
    private int n; //maximo
    
    Scanner input = new Scanner(System.in);
    
    PilaDouble(int _n){
        n=_n;
        tope=-1;
        v=new double[n];
    }
    
    public void reset(){
        for(int i=0;i<tope;i++){
            v[i]=0;
        }
        tope=-1;
    }
    
    public void add(double x){
        if (tope==n-1){
            //System.out.println("Pila llena"); //Comentadas porque no necesito esta informacion siendo mostrada
            return;
        }
        tope++;
        v[tope]=x;
    }
    
    public double pop(){
        if (tope==-1){
            //System.out.println("Pila vacia"); //Comentadas porque no necesito esta informacion siendo mostrada
            return -100;
        }
        double x=v[tope];
        tope--;
        return x;
    }
    
    public int getN(){
        return n;
    }
    
    public int getTope(){
        return tope;
    }
    
    public double getLast(){
        return v[tope];
    }
    
    public void imprimir(){
        System.out.printf("Pila: [ "+v[0]);
        if (tope==0) System.out.println(" ]");
        else for (int i=1;i<=tope;i++){
            if (i==tope){
                System.out.println(" , "+v[i]+" ]");
            }
            else System.out.printf(" , "+v[i]);
            
        }
    }
}
