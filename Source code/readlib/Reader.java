package readlib;

import java.io.BufferedReader;
import customexceptions.FileNotFound;
import customexceptions.LineLimit;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    
    public static final int ENCRYPTED = 1;
    public static final int NOT_ENCRYPTED = 2;
    
    /**
     * This method is used to count number of lines in a file.
     * It is used internally and can be used upon calling if required.
     * Encryption/Decryption is not a parameter as it would return same count
     * regardless
     * @param filename
     * @param path
     * @return integer if records exist otherwise 0 
     */
    public static int countLines(String filename, String path){
        int count = 0;
        File f;
        f = new File(path + "\\" + filename);
        if (!f.exists()) {
            try {
                throw new FileNotFound("File not found");
            } catch (FileNotFound ex) {
               ex.printStackTrace();
            }
        } else {
            try {
                FileInputStream ifs = new FileInputStream(f);
                DataInputStream in = new DataInputStream(ifs);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                while ((br.readLine()) != null) {
                    count++;
                }
                in.close();
                ifs.close();
                br.close();
                return count;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }
    
    /**
     * This method can be used to read all lines in a file and returns a 
     * String array. Dec_type is '1' if file is encrypted and '2' if plain text.
     * @param filename
     * @param path
     * @param dec_type
     * @return String array if records exist otherwise nothing 
     */
    public static String[] readAll(String filename, String path, 
            int dec_type){
        
        File f;
        f = new File(path + "\\" + filename);
        if (!f.exists()) {
            try {
                throw new FileNotFound("File not found");
            } catch (FileNotFound ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                FileInputStream ifs = new FileInputStream(f);
                DataInputStream in = new DataInputStream(ifs);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line = null;
                String[] block = new String[countLines(filename, path)];
                int linecount = 0;
                while ((line = br.readLine()) != null) {

                    if (dec_type == Reader.ENCRYPTED) {
                        block[linecount] = security.EncDec.decrypt(line);
                        linecount++;
                    } else if (dec_type == Reader.NOT_ENCRYPTED) {
                        block[linecount] = line;
                        linecount++;
                    }
                }
                in.close();
                ifs.close();
                br.close();
                return block;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * This method returns a block of lines from a file(from 'start' value to 
     * 'end' value). Dec_type is '1' if file is encrypted and '2' if plain text.
     * Note: First line count is 0 and hence, last is real value-1
     * Usage: To retrieve n number of records or lines from a file.
     * @param start
     * @param end
     * @param filename
     * @param path
     * @param dec_type
     * @return String array if records exist otherwise nothing 
     */
    public static String[] readBlock(int start,int end ,String filename, String path, 
            int dec_type){
        
        if((start>=end)||(start<0)||(end<0)){
            try {
                throw new LineLimit("Bad line limit");
            } catch (LineLimit ex) {
                ex.printStackTrace();
            }
        }
        File f;
        f = new File(path + "\\" + filename);
        if (!f.exists()) {
            try {
                throw new FileNotFound("File not found");
            } catch (FileNotFound ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                FileInputStream ifs = new FileInputStream(f);
                DataInputStream in = new DataInputStream(ifs);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line = null;
                int lines = countLines(filename, path);
                if(!((start < lines) && (end < lines))){
                   throw new LineLimit("Not enough lines");
                }            
                String[] block = new String[lines];
                int writeLineNumber = 0;
                while ((line = br.readLine()) != null) {
                      if (dec_type == Reader.ENCRYPTED) {
                        block[writeLineNumber] = security.EncDec.decrypt(line);
                        writeLineNumber++;
                    } else if (dec_type == Reader.NOT_ENCRYPTED) {
                        block[writeLineNumber] = line;
                        writeLineNumber++;
                    }
                }
                in.close();
                ifs.close();
                br.close();  
                String temp[] = new String[end-start+1] ;
                int j=0;
                for (int i = 0; i <= end; i++) {
                    if(i<start){
                        continue;
                    }
                    else{
                        temp[j] = block[i];
                        j++;
                    }
                }
                return temp;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * This method returns number of specified records from the top of the file.
     * Dec_type is '1' if file is encrypted and '2' if plain text.
     * @param count
     * @param filename
     * @param path
     * @param dec_type
     * @return String array if records exist otherwise nothing 
     */
    public static String[] readFirstn(int count, String filename, String path, 
            int dec_type){
       if(count<1){
           try {
               throw new LineLimit("Bad line limit");
           } catch (LineLimit ex) {
              ex.printStackTrace();
           }
       } 
       String temp[] = readBlock(0, count-1, filename, path, dec_type);
        return temp;
    }
    
    /**
     * This method returns number of specified records from the bottom of the file.
     * Dec_type is '1' if file is encrypted and '2' if plain text.
     * @param count
     * @param filename
     * @param path
     * @param dec_type
     * @return String array if records exist otherwise nothing 
     */
    public static String[] readlastn(int count, String filename, String path, 
            int dec_type){
       if(count<1){
           try {
               throw new LineLimit("Bad line limit");
           } catch (LineLimit ex) {
              ex.printStackTrace();
           }
       } 
       int totalLines = countLines(filename, path); 
       String temp[] = readBlock(totalLines-count, totalLines-1, filename, path, dec_type);
        return temp;
    }
    
    /**
     * This method returns records containing specified text from the file.
     * Dec_type is '1' if file is encrypted and '2' if plain text.
     * @param text
     * @param filename
     * @param path
     * @param dec_type
     * @return String array if records exist otherwise nothing 
     */
    public static String[] readFilterInc(String text, String filename, String path, 
            int dec_type){
        String data[] = readAll(filename, path, dec_type);
        List<String> temp = new ArrayList<>();
        for (String data1 : data) {
            if(data1.contains(text)){
                temp.add(data1);
            }
        }
        return temp.toArray(new String[temp.size()]);
    }   
         
    /**
     * This method returns records NOT containing specified text from the file.
     * Dec_type is '1' if file is encrypted and '2' if plain text.
     * @param text
     * @param filename
     * @param path
     * @param dec_type
     * @return String array if records exist otherwise nothing 
     */
    public static String[] readFilterExc(String text, String filename, String path, 
            int dec_type){
        String data[] = readAll(filename, path, dec_type);
        List<String> temp = new ArrayList<>();
        for (String data1 : data) {
            if(!data1.contains(text)){
                temp.add(data1);
            }
        }
        return temp.toArray(new String[temp.size()]);
    }   
    
    /**
     * This method can be used to read all lines in a file and returns a 
     * String array. Dec_type is '1' if file is encrypted and '2' if plain text.
     * parameter "key" is for custom key.
     * @param filename
     * @param path
     * @param dec_type
     * @param key
     * @return 
     */
    public static String[] readAll(String filename, String path, 
            int dec_type, String key){
        
        File f;
        f = new File(path + "\\" + filename);
        if (!f.exists()) {
            try {
                throw new FileNotFound("File not found");
            } catch (FileNotFound ex) {
              ex.printStackTrace();
            }
        } else {
            try {
                FileInputStream ifs = new FileInputStream(f);
                DataInputStream in = new DataInputStream(ifs);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line = null;
                String[] block = new String[countLines(filename, path)];
                int linecount = 0;
                while ((line = br.readLine()) != null) {

                    if (dec_type == Reader.ENCRYPTED) {
                        block[linecount] = security.EncDec.decrypt(line, key);
                        linecount++;
                    } else if (dec_type == Reader.NOT_ENCRYPTED) {
                        block[linecount] = line;
                        linecount++;
                    }
                }
                in.close();
                ifs.close();
                br.close();
                return block;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * This method returns a block of lines from a file(from 'start' value to 
     * 'end' value). Dec_type is '1' if file is encrypted and '2' if plain text.
     * Note: First line count is 0 and hence, last is real value-1
     * Usage: To retrieve n number of records or lines from a file.
     * parameter "key" is for custom key.
     * @param start
     * @param end
     * @param filename
     * @param path
     * @param dec_type
     * @param key
     * @return String array if records exist otherwise nothing
     */
    public static String[] readBlock(int start,int end ,String filename, String path, 
            int dec_type, String key){
        
        if((start>=end)||(start<0)||(end<0)){
            try {
                throw new LineLimit("Bad line limit");
            } catch (LineLimit ex) {
               ex.printStackTrace();
            }
        }
        File f;
        f = new File(path + "\\" + filename);
        if (!f.exists()) {
            try {
                throw new FileNotFound("File not found");
            } catch (FileNotFound ex) {
               ex.printStackTrace();
            }
        } else {
            try {
                FileInputStream ifs = new FileInputStream(f);
                DataInputStream in = new DataInputStream(ifs);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line = null;
                int lines = countLines(filename, path);
                if(!((start < lines) && (end < lines))){
                   throw new LineLimit("Not enough lines");
                }            
                String[] block = new String[lines];
                int writeLineNumber = 0;
                while ((line = br.readLine()) != null) {
                      if (dec_type == Reader.ENCRYPTED) {
                        block[writeLineNumber] = security.EncDec.decrypt(line, key);
                        writeLineNumber++;
                    } else if (dec_type == Reader.NOT_ENCRYPTED) {
                        block[writeLineNumber] = line;
                        writeLineNumber++;
                    }
                }
                in.close();
                ifs.close();
                br.close();  
                String temp[] = new String[end-start+1] ;
                int j=0;
                for (int i = 0; i <= end; i++) {
                    if(i<start){
                        continue;
                    }
                    else{
                        temp[j] = block[i];
                        j++;
                    }
                }
                return temp;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * This method returns number of specified records from the top of the file.
     * Dec_type is '1' if file is encrypted and '2' if plain text.
     * parameter "key" is for custom key.
     * @param count
     * @param filename
     * @param path
     * @param dec_type
     * @param key
     * @return String array if records exist otherwise nothing
     */
    public static String[] readFirstn(int count, String filename, String path, 
            int dec_type, String key){
       if(count<1){
           try {
               throw new LineLimit("Bad line limit");
           } catch (LineLimit ex) {
               ex.printStackTrace();
           }
       } 
       String temp[] = readBlock(0, count-1, filename, path, dec_type, key);
        return temp;
    }
    
    /**
     * This method returns number of specified records from the bottom of the file.
     * Dec_type is '1' if file is encrypted and '2' if plain text.
     * parameter "key" is for custom key.
     * @param count
     * @param filename
     * @param path
     * @param dec_type
     * @param key
     * @return String array if records exist otherwise nothing 
     */
    public static String[] readlastn(int count, String filename, String path, 
            int dec_type, String key){
       if(count<1){
           try {
               throw new LineLimit("Bad line limit");
           } catch (LineLimit ex) {
             ex.printStackTrace();
           }
       } 
       int totalLines = countLines(filename, path); 
       String temp[] = readBlock(totalLines-count, totalLines-1, filename, path, dec_type, key);
        return temp;
    }
    
    /**
     * This method returns records containing specified text from the file.
     * Dec_type is '1' if file is encrypted and '2' if plain text.
     * parameter "key" is for custom key.
     * @param text
     * @param filename
     * @param path
     * @param dec_type
     * @param key
     * @return String array if records exist otherwise nothing
     */
    public static String[] readFilterInc(String text, String filename, String path, 
            int dec_type, String key){
        String data[] = readAll(filename, path, dec_type, key);
        List<String> temp = new ArrayList<>();
        for (String data1 : data) {
            if(data1.contains(text)){
                temp.add(data1);
            }
        }
        return temp.toArray(new String[temp.size()]);
    }   
         
    /**
     * This method returns records NOT containing specified text from the file.
     * Dec_type is '1' if file is encrypted and '2' if plain text.
     * parameter "key" is for custom key.
     * @param text
     * @param filename
     * @param path
     * @param dec_type
     * @param key
     * @return String array if records exist otherwise nothing
     */
    public static String[] readFilterExc(String text, String filename, String path, 
            int dec_type, String key){
        String data[] = readAll(filename, path, dec_type, key);
        List<String> temp = new ArrayList<>();
        for (String data1 : data) {
            if(!data1.contains(text)){
                temp.add(data1);
            }
        }
        return temp.toArray(new String[temp.size()]);
    }   
    
}
