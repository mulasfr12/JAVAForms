/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.factory;

import com.dev.utilities.JAXBUtils;
import java.io.InputStream;

public class ParserFactory {

    public static <T> T parse(InputStream inputStream, Class<T> clazz) throws Exception {
        return JAXBUtils.unmarshal(inputStream, clazz);
    }
}

