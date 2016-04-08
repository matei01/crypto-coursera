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
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author M
 */

public class Week1 {

    /**
     * @param args the command line arguments
     */
    public static String decr=new String();
    
    
    static List<String> c=new ArrayList<>();
    static List<byte[]> b=new ArrayList<>();
    
    public static void getCyphertexts() throws FileNotFoundException, IOException, DecoderException{
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\cyphers.txt"))) {
            int i=1;
            String line;
            while ((line = br.readLine()) != null) {
                c.add( line);
                /*byte[] bytes = Hex.decodeHex(c.get(i).toCharArray());
                b.add(bytes);*/
            }
            c.add("");
        }
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\enc.txt"))){
            decr=br.readLine();
        }
        
        
        
    }
    public static void printt(){
        for(int i=0;i<c.size();i++){
            System.out.print(c.get(i)+"\n");
            System.out.print("\n"+c.get(i).length() +" "+"\n");
        }

            
    }
    public static String xorHex(String a, String b) {
    // TODO: Validation
        int x,y;
        
        if(a.length()<b.length()){
            char chars[]=new char[b.length()];
            for (int i = 0; i < a.length(); i++) 
                chars[i] = toHex(fromHex(a.charAt(i)) ^ fromHex(b.charAt(i)));
            for(int j=a.length();j<b.length();j++)
                chars[j]=b.charAt(j);
            return new String(chars);
        }
        
        else{
            char chars[]=new char[a.length()];
            for (int i = 0; i < b.length(); i++) 
                chars[i] = toHex(fromHex(a.charAt(i)) ^ fromHex(b.charAt(i)));
            for(int j=b.length();j<a.length();j++)
                chars[j]=a.charAt(j);
            return new String(chars);
        }
        
        
        
}

    private static int fromHex(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        }
        throw new IllegalArgumentException();
    }

    private static char toHex(int nybble) {
        if (nybble < 0 || nybble > 15) {
            throw new IllegalArgumentException();
        }
        return "0123456789abcdef".charAt(nybble);
    }
    public static boolean conditie(int i,int j,int k){
        String s=xorHex(c.get(i),c.get(j));
        if(k+2>s.length())
            return false;
        return (Integer.parseInt(xorHex(s.substring(k,k+2),"20"),16)==(Integer.parseInt(s.substring(k,k+2),16)+32))||
                ((Integer.parseInt(xorHex(s.substring(k,k+2),"20"),16))==(Integer.parseInt(s.substring(k,k+2),16)-32));
    }
    
    public static String find(){
        int k = 0,i,j = 0;
        for( i=0;i<10;i++)
            for( j=i+1;j<10;j++){
                String s=xorHex(c.get(i),c.get(j));
                for( k=0;k<min(c.get(i).length(),c.get(j).length())-1;k=k+2){
                    char[] keys=new char[500];
                    for(int z=0;z<499;z++)
                        keys[z]='0';
                    if(conditie(i,j,k)){
                        boolean oare=true;
                        for(int l=j+1;l<10;l++)
                            
                            if(!conditie(i,l,k))
                                oare=false;
                        for(int z=0;z<499;z++)
                                keys[z]='0';
                        if(oare==true){
                            if(keys[k]=='0'&&keys[k+1]=='0'){
                                keys[k]= xorHex(c.get(i).substring(k,k+2),"20").charAt(0);
                                keys[k+1]=xorHex(c.get(i).substring(k,k+2),"20").charAt(1);
                                String sx=decrypt(decr,String.valueOf(keys));
                                decr=new String(sx);

                            }
                        }
                        else{
                            if(keys[k]=='0'&&keys[k+1]=='0'){
                                keys[k]= xorHex(c.get(j).substring(k,k+2),"20").charAt(0);
                                keys[k+1]= xorHex(c.get(j).substring(k,k+2),"20").charAt(1);
                                String sx=decrypt(decr,String.valueOf(keys));
                                decr=new String(sx);
                            }
                        }
                    }
                    k=min(c.get(i).length(),c.get(j).length())-1;
                }
                
            }
        return decr;
    }
    /*public static String find1(){
        int i,j,k;
        for(i=1;i<=10;i++)
            for(j=i;j<=10;j++){
                String co=xorHex(c.get(i),c.get(j));
                for(k=0;k<min(c.get(i).length(),c.get(j).length());k=k+2){
                    if(conditie(i,j,k)){
                        if(conditie(i,j+1,k)){
                            String key = iadin(i,k);
                            new=decrypt()
                        }
                            
                    }
                }
                
            }
        
    }
    */
    /*public static void printk(){
        for (int i=1;i<keys.length;i++)
            System.out.print(keys[i]);
        System.out.print("\n");

    }
    */
    public static String decrypt(String s,String key){
        String p=xorHex(s,key);
        
        return p;
    }
    public static void main(String[] args) throws IOException, FileNotFoundException, DecoderException {
        // TODO code application logic here
       
        getCyphertexts();
        String k=find();
        System.out.print(k);
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < k.length(); i+=2) {
            String str = k.substring(i, i+2);
            output.append((char)Integer.parseInt(str, 16));
        }
        System.out.println(output);
        
        
        

}
}
