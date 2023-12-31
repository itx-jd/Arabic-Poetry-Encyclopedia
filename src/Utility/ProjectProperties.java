package Utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import DAL.MySQLConnectionSingleton;

public class ProjectProperties {
	
public static Properties loadProperties() {
    	
        Properties properties = new Properties();
        
        try (InputStream input = MySQLConnectionSingleton.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config/application.properties");
                return properties;
            }

            // load a properties file from class path, inside static method
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

}
