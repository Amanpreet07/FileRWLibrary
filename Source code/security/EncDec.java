package security;

import customexceptions.keyLength;
import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncDec {
    /**
     * This method is used to encrypt using default key.
     * @param str
     * @return String of encrypted text
     * @throws Exception 
     */
    public static String encrypt(String str) throws Exception{   
       Key k = EncDec.generateKey();
       Cipher c = Cipher.getInstance("AES"); 
       c.init(Cipher.ENCRYPT_MODE, k);
       byte[] encVal = c.doFinal(str.getBytes());
       String encrypted = new String(Base64.getEncoder().encode(encVal));
       return encrypted;
    }
   
    /**
     * This method is used to decrypt text using default key.
     * @param str
     * @return String of decrypted text
     * @throws Exception 
     */
   public static String decrypt(String str) throws Exception{
       Key k = EncDec.generateKey();
       Cipher c = Cipher.getInstance("AES"); 
       c.init(Cipher.DECRYPT_MODE, k);
       byte[] decrypted = Base64.getDecoder().decode(str);
       byte[] decValue = c.doFinal(decrypted);
       String dec = new String(decValue);
       return dec;
   }    
   
   /**
    * This is to return default key for encryption/decryption
    * @return key
    * @throws Exception 
    */
   private static Key generateKey() throws Exception{
     Key k = new SecretKeySpec("TOMMARVOLORIDDLE".getBytes(), "AES");
     return k;
   }
   
   /**
    * This method is used to encrypt using custom key
    * @param str
    * @param key
    * @return String of encrypted text
    * @throws Exception 
    */
   public static String encrypt(String str, String key) throws Exception{   
       if(key.length()!=16){
           throw new keyLength("wrong key length(16 required)");
       }
       Key k = generateKey(key);
       Cipher c = Cipher.getInstance("AES"); 
       c.init(Cipher.ENCRYPT_MODE, k);
       byte[] encVal = c.doFinal(str.getBytes());
       String encrypted = new String(Base64.getEncoder().encode(encVal));
       return encrypted;
    }
   
   /**
    * this method is used to return decrypted text using custom key
    * @param str
    * @param key
    * @return String of decrypted text
    * @throws Exception 
    */
   public static String decrypt(String str, String key) throws Exception{
       if(key.length()!=16){
           throw new keyLength("wrong key length(16 required)");
       }
       Key k = generateKey(key);
       Cipher c = Cipher.getInstance("AES"); 
       c.init(Cipher.DECRYPT_MODE, k);
       byte[] decrypted = Base64.getDecoder().decode(str);
       byte[] decValue = c.doFinal(decrypted);
       String dec = new String(decValue);
       return dec;
   } 
   
   /**
    * This method is used to produce custom key based on value provided by user
    * @param key
    * @return Key (custom Key)
    * @throws Exception 
    */
   private static Key generateKey(String key) throws Exception{
     Key k = new SecretKeySpec(key.getBytes(), "AES");
     return k;
   }
    
}
