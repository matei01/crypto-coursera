/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.valueOf;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.DecoderException;
import static week1.Week1.c;

/**
 *
 * @author M
 */
public class try2 {
    static List<Integer[]> c=new ArrayList<Integer[]>();
    static List<Integer> p=new ArrayList<>();
    static Integer[] che=new Integer[300];
    
    static Integer[] decr=new Integer[499];
    public static void getCyphertexts() throws FileNotFoundException, IOException, DecoderException{
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\cyphers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Integer[] cy=new Integer[line.length()/2];
                for(int j=0;j<line.length();j=j+2)
                    cy[j/2]=Integer.parseInt(line.substring(j,j+2),16);
                c.add(cy);
            }
            
        }
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\enc.txt"))){
            String line=br.readLine();
            decr=new Integer[line.length()/2];
            Integer[] cy=new Integer[line.length()];
                for(int j=0;j<line.length();j=j+2)
                    decr[j/2]=Integer.parseInt(line.substring(j,j+2),16);
            
        }
    }
    public static Integer[] xorr(Integer[] un,Integer[] doi)
    {
        Integer[] no=new Integer[max(un.length,doi.length)];
        for(int i=0;i<max(un.length,doi.length);i++){
            if(i>=un.length)
                no[i]=doi[i];
            else  
                if(i>=doi.length)
                    no[i]=un[i];
                else
                    no[i]=un[i]^doi[i];
        }
        return no;
        
    }
    public static boolean conditie(Integer[] ba,int k){
        if(k>=ba.length)
            return false;
        if(((ba[k]>=65 && ba[k]<=90)||(ba[k]>=97 && ba[k]<=122) || (ba[k]==32))&&
                            (((ba[k]^32)==(ba[k]+32))||((ba[k]^32)==(ba[k]-32))))
            return true;
        return false;
    }
    public static void find(){
        int i,j,k;
        Integer space=new Integer(32);
        for(i=0;i<10;i++)
            for(j=0;j<10&&j!=i;j++){
                Integer[] ba;
                ba=xorr(c.get(i),c.get(j));
                for(k=0;k<min(c.get(i).length,c.get(j).length);k++)
                    if(conditie(ba,k)&& (!p.contains((Integer) k)))
                    {
                        Integer[] ca=new Integer[ba.length];
                        Integer[] cb=new Integer[ba.length];
                        if(j==9)
                            ca=xorr(c.get(i),c.get(j-1));
                        else
                            ca=xorr(c.get(i),c.get(j+1));
                        if(i==9)
                            cb=xorr(c.get(i-1),c.get(j));
                        else
                            cb=xorr(c.get(i+1),c.get(j));
                        if(k<ca.length)
                            if(conditie(ca,k)){
                                Integer[] rez=c.get(i); 
                                if(k<decr.length)
                                    if(decr[k]!=null && k<decr.length){
                                        decr[k]=decr[k]^((rez[k])^32);

                                        p.add((Integer) k);
                                    }
                            }
                        else if(k<cb.length)
                            if(conditie(cb,k)){
                                Integer[] rez=c.get(j); 
                                if(k<decr.length)
                                    if(decr[k]!=null  ){
                                        decr[k]=decr[k]^((rez[k])^32);
                                        p.add((Integer) k);
                                    }
                            }
                    }
                
            }
    }
    public static void print(){
        for(int i=0;i<=decr.length;i++){
            int a = decr[i].intValue();
            char c=(char) a;
            System.out.println(c);
            
        }
    }
    public static void main(String[] args) throws IOException, FileNotFoundException, DecoderException{
        getCyphertexts();
        for(int j=0;j<decr.length;j++){
            System.out.print(decr[j]+" ");
            
        }
        for(int i=0;i<c.size();i++){
            Integer[] ne=c.get(i);
            for(int j=0;j<ne.length;j++)
                System.out.print(ne[j]+" ");
            System.out.println("/n");
        }
        find();
        for(int j=0;j<decr.length;j++){
            System.out.print(decr[j]+" ");
            
        }
        System.out.println("/n");
        for(int j=0;j<decr.length;j++){
            int a=decr[j];
            System.out.print((char) a);
        }
        System.out.println("/n");
        for(Integer in:p){
            System.out.print(in + " ");
        }

        
    }
        
}
