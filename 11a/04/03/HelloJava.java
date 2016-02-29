/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author valentin.yonev
 */
public class HelloJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {    
        ArrayList<String> properties;
        String sort = null;
        properties = new ArrayList<>();
        for(String property : args)
        {
            if(!property.equals("down") && !property.equals("up"))
                properties.add(property);
            else
                sort = property;
        }
        Arrays.sort(args);
        if (sort != null || sort.equals("down")) {
            Collections.reverse(properties);
        }
        for(String property : properties)
                System.out.print(System.getProperty(property) != null ? System.getProperty(property) + "\n" : "");
    }
    
}
