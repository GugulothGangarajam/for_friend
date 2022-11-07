package com.code.controller;
import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.code.model.User;
import com.code.service.RegistrationService;

@RestController
public class RegistrationController {
 
//it used for encrypt the password
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
	          
	        return encryptedpsk;
	}
	
	
	
	@Autowired
	private RegistrationService service;
	
	@PostMapping("registerUser")
	@CrossOrigin(origins="http://localhost:4200")
	public User registerUser(@RequestBody User user) throws Exception
	{
		user.setPassword(Encrypt(user.getPassword()));
		
		String tempEmailId=user.getEmailId();
		if(tempEmailId !=null && !"".equals(tempEmailId)) {
			User userObj= service.fetchUserByEmailId(tempEmailId);
			if(userObj !=null)
			{
				throw new Exception("user with "+tempEmailId+"is already exist");
			}
		}
		User userObj=null;
		 userObj= service.saveUser(user);
		 return userObj;
	}
	
	@PostMapping("login")
	@CrossOrigin(origins="http://localhost:4200")
	public User loginUser(@RequestBody User user) throws Exception
	{
	
		String tempEmailId=user.getEmailId();
		String tempPass=Encrypt(user.getPassword());
		
		User userObj=null;
		if(tempEmailId !=null && tempPass!=null)
		{
			userObj= service.fetchUserByEmailIdAndPassword(tempEmailId, tempPass);
		}
		if(userObj==null)
		{
			throw new Exception("bad creeintails");
		}
		return userObj;
	}
}
