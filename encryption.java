package test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Test {
		public static void main(String args[]) {
			Scanner sc = new Scanner(System.in);
			String psk=sc.next();
			System.out.println("encrypted completed"+Encrypt(psk));
		}
		public static String Encrypt(String psk) {
			 String encryptedpsk = null;  
		        try   
		        {  		            
		            MessageDigest m = MessageDigest.getInstance("MD5");  
		         
		            m.update(psk.getBytes());  
		              
		              
		            byte[] bytes = m.digest();  
		              
		           
		            StringBuilder s = new StringBuilder();  
		            for(int i=0; i< bytes.length ;i++)  
		            {  
		                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
		            }  	              
		             
		            encryptedpsk= s.toString();  
		        }   
		        catch (NoSuchAlgorithmException e)   
		        {  
		            e.printStackTrace();  
		        }  
		          
		       
		        System.out.println("Plain-text password: " + psk);  
		        System.out.println("Encrypted pskusing MD5: " + encryptedpsk);  
		        return encryptedpsk;
		}
}
		
