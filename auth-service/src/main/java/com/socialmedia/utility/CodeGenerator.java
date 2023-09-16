package com.socialmedia.utility;

import java.util.UUID;

public class CodeGenerator {

    //random bir kod olusturacagız
    //random bir uuid olusturup..
    //metot bize string olarak kodu döndürsün..

    public static String generateCode(){
        String uuid = UUID.randomUUID().toString();

        String[] data = uuid.split("-");
        String newCode = "";
        int i = 0;

        while (i < data.length){
            newCode += data[i].charAt(0);
            i++;
        }
        return newCode;
    }
}
