/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.dal;

/**
 *
 * @author salum
 */import java.util.Properties;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RepositoryFactory {
    private static Repository instance;
    private static final String PATH = "/config/repository.properties";
    private static final String CLASS_NAME = "CLASS_NAME";
    private static final Properties PROPERTIES = new Properties();

    private RepositoryFactory() {}

    static {
        try (InputStream is = RepositoryFactory.class.getResourceAsStream(PATH)) {
            PROPERTIES.load(is);
            instance = (Repository) Class.forName(PROPERTIES.getProperty(CLASS_NAME))
                                         .getDeclaredConstructor()
                                         .newInstance();
        } catch (Exception ex) {
            Logger.getLogger(RepositoryFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Repository getInstance() {
        return instance;
    }
}
