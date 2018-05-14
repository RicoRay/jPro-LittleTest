/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.javafxgradle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Titi
 */
public class Tools {

    /**
     * Get the propertie from the properties file
     *
     * @param filePath
     * @param propertie
     * @return
     */
    public String getPropertie(String filePath, String propertie) {
        StringBuilder sbResult = new StringBuilder();        
        
        Properties prop = new Properties();
        InputStream input = null;

        try {        
            URL url = getClass().getResource(filePath);
            File file;
            try {
                file = new File(url.toURI());
                input = new FileInputStream(file);
            } catch (URISyntaxException ex) {
                Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // load a properties file
            prop.load(input);

            // get the property value and print it out
            sbResult.append(prop.getProperty(propertie));


        } catch (IOException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }

        return sbResult.toString();
    }
}
