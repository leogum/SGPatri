/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Utilitario;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;

/**
 *
 * @author d732229
 */
public class Transformar {

//Converte String para o padrão UTF8    
    public static String utf8(String s) {
        String out;
        try {
//            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
            out = new String(s.getBytes("UTF-8"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
    
          

//Converte padronização o texto para a Primeira Letra da frase em Maiuscula    
    public static String priMaiuscula(String value) {
        value = value.toLowerCase();    	
        String result = "";
        String[] nomes = value.split("\\s+");
            for(String palavra : nomes)
                result = result +" "+ palavra.replaceFirst(palavra.substring(0, 1), palavra.substring(0, 1).toUpperCase());
        return result.trim();
	} 

//Criptografia o texto para o padrão MD5 (Message-Digest algorithm 5)
    public static String md5(String senha){
		String sen = "";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
		sen = hash.toString(16);			
		return sen;
	}    

    public static String removeAccents(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;
    }

    
}
