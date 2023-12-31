package Utility;

//CustomException.java
public class CustomException extends Exception {
	
	String message;


 // Constructor with a custom error message
 public CustomException(String message) {
     this.message = message;
 }
 
 public void printErrorMessage() {
	 System.out.println(message);
 }
 
}
