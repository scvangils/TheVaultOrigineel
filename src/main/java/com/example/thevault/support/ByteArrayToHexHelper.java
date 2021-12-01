/*
20211201 WB zet Array met bytes om naar hexadecimalen
 */

package com.example.thevault.support;

public class ByteArrayToHexHelper {

    public static String codeerHexString(byte[] byteArray){
        StringBuffer hexStringBuffer = new StringBuffer();
        for (byte b : byteArray){
            hexStringBuffer.append(byteToHex(b));
        }
        return hexStringBuffer.toString();
    }


    private static String byteToHex(byte num){
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }


}
