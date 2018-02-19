package writelib;

import customexceptions.FileNotFound;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;

public class Writer {
    
    public static final int DELETE = 1;
    public static final int UPDATE = 2;
    public static final int ENCRYPT = 1;
    public static final int PLAIN_TEXT = 2;
    
    /**
     * This method can be used to write a single line to a specified file
     * "append" can be set to true to write further at EOF. 
     * "enc_type" is to use default encryption.
     * @param filename
     * @param path
     * @param data
     * @param append
     * @param enc_type
     */
    public static void writeData_Single(String filename, String path,String data,
            boolean append, int enc_type) {

          File f;
        f = new File(path + "\\" + filename);
        if (!f.exists()) {
              try {
                  throw new FileNotFound("No such file.");
              } catch (FileNotFound ex) {
                 ex.printStackTrace();
              }
        } else {
            try {
                FileWriter fs = new FileWriter(f, true);
                BufferedWriter op = new BufferedWriter(fs);
                String data2;
                if (enc_type == Writer.ENCRYPT) {
                    data2 = security.EncDec.encrypt(data);
                } else {
                    data2 = data;
                }
                if (append == true) {
                    op.append(data2);
                    op.newLine();
                } else if (append == false) {
                    op.write(data2);
                }
                op.close();
                fs.close();

            } catch (Exception ex) {
              ex.printStackTrace();
            }
        }
    }
    
    /**
     * This method can be used to write a single line to a specified file
     * "append" can be set to true to write further at EOF. 
     * "key" can be used to provide custom encryption key(128 bit).
     * @param filename
     * @param path
     * @param data
     * @param append
     * @param enc_type
     * @param key
     */
    public static void writeData_Single(String filename, String path,String data,
            boolean append, int enc_type, String key){

          File f;
        f = new File(path + "\\" + filename);
        if (!f.exists()) {
              try {
                  throw new FileNotFound("No such file.");
              } catch (FileNotFound ex) {
                 ex.printStackTrace();
              }
        } else {
            try {
                FileWriter fs = new FileWriter(f, true);
                BufferedWriter op = new BufferedWriter(fs);
                String data2;
                if (enc_type == Writer.ENCRYPT) {
                    data2 = security.EncDec.encrypt(data, key);
                } else {
                    data2 = data;
                }
                if (append == true) {
                    op.append(data2);
                    op.newLine();
                } else if (append == false) {
                    op.write(data2);
                }
                op.close();
                fs.close();

            } catch (Exception ex) {
              ex.printStackTrace();
            }
        }
    }
    
    /**
     * This method can be used to write a whole block of data to a specified file
     * "append" can be set to true to write further at EOF. 
     * "enc_type" is to use default encryption.
     * @param filename
     * @param path
     * @param data
     * @param append
     * @param enc_type
     */
    public static void writeData_Block(String filename, String path, String data[],
            boolean append, int enc_type){
     
          File f;
          f = new File(path+"\\"+filename);
          if(!f.exists()){
              try {
                  throw new FileNotFound("No such file");
              } catch (FileNotFound ex) {
                ex.printStackTrace();
              }
          }
            else{
        try {
            FileWriter fs = new FileWriter(f,true);
            BufferedWriter op = new BufferedWriter(fs);
            int x=0;
            for (String data1 : data) {
            x++;    
            }
            String data2[] = new String[x];
             if(enc_type==Writer.ENCRYPT){
               for(int i=0;i<x;i++){
                data2[i] = security.EncDec.encrypt(data[i]);
               }
              }
              else{
               data2 = data.clone();
              }              
             for (String data21 : data2) {
               if(append == true){
              
                op.append(data21);
                 op.newLine();
                }
               else if(append == false){
               op.write(data21);
              }
            }
            op.close();
            fs.close();
        } catch (Exception ex) {
         ex.printStackTrace();
        }
       }
    }
    
    /**
     * This method can be used to write a whole block of data to a specified file
     * "append" can be set to true to write further at EOF. 
     * "key" can be used to provide custom encryption key(128 bit).
     * @param filename
     * @param path
     * @param data
     * @param append
     * @param enc_type
     * @param key
     */
    public static void writeData_Block(String filename, String path, String data[],
            boolean append, int enc_type, String key){
     
          File f;
          f = new File(path+"\\"+filename);
          if(!f.exists()){
              try {
                  throw new FileNotFound("No such file");
              } catch (FileNotFound ex) {
                ex.printStackTrace();
              }
          }
            else{
        try {
            FileWriter fs = new FileWriter(f,true);
            BufferedWriter op = new BufferedWriter(fs);
            int x=0;
            for (String data1 : data) {
            x++;    
            }
            String data2[] = new String[x];
             if(enc_type==Writer.ENCRYPT){
               for(int i=0;i<x;i++){
                data2[i] = security.EncDec.encrypt(data[i], key);
               }
              }
              else{
               data2 = data.clone();
              }              
             for (String data21 : data2) {
               if(append == true){
              
                op.append(data21);
                 op.newLine();
                }
               else if(append == false){
               op.write(data21);
              }
            }
            op.close();
            fs.close();
        } catch (Exception ex) {
         ex.printStackTrace();
        }
       }
    }
    
    /*
    NOTE: THESE OVERWRITE METHODS ARE NOT OPTIMISED AS OTHER METHODS IN THE LIBRARY.
    IT WORKS JUST FINE FOR DOING SCHOOL/COLLEGE PROJECTS BUT NOT WITH SEMI-PRO,
    COMPETITIVE(DEFINITELY NOT PRO xD). IT EASILY HANDLES FILES UPTO COUPLE THOUSAND 
    LINES WITHOUT SHOWING MUCH COMPUTATIONAL LAG BUT AFTER THAT IT DEFINITELY DOES.
    
    IF ANYONE OF YOU GETS AN EXTRA TIME TO WRITE AN OPTIMISED CODE FOR THESE METHODS 
    OR ANY OTHER, LET ME KNOW, I WILL UPDATE IT.
    CONTACT@ arora07aman@gmail.com (Subject : Git code update/optimisation).
    Thank you.
    */
    
    /**
     * It can be used to update a file by either deleting certain data from file
     * or by overwriting it with new data.
     * @param filename
     * @param path
     * @param old_data
     * @param new_data
     * @param enc_type
     * @param delete_mode
     */
    public static void overwrite_Line(String filename, String path, String old_data,
            String new_data, int enc_type, int delete_mode){
     
        File f;
          f = new File(path+"\\"+filename);
          if(!f.exists()){         
            try { 
                throw new FileNotFound("No such file");
            } catch (FileNotFound ex) {
                ex.printStackTrace();
            }
          }
          else{           
              String temp_data[] = readlib.Reader.readAll(filename, path, enc_type);
              int size = 0;
              for (String temp_data1 : temp_data) {
                 size++; 
              }               
              String temp_data2[];
              if(delete_mode==Writer.DELETE){
               temp_data2 = new String[size-1];
              }else{
               temp_data2 = new String[size];
              }  
              int counter =0;                       
              if(delete_mode==Writer.DELETE){              
                  for (String temp_data21 : temp_data) {                    
                      if(temp_data21.equals(old_data)){
                         continue;
                      }
                      else{
                          temp_data2[counter] = temp_data21;
                          counter++;
                      }                    
                  }                                
              }
              else if(delete_mode==Writer.UPDATE){                
                  for (String temp_data21 : temp_data) {                     
                      if(temp_data21.equals(old_data)){                      
                          temp_data2[counter] = new_data;
                          counter++;  
                      }
                      else{
                          temp_data2[counter] = temp_data21;
                          counter++;
                      }                     
                  }
              }
                       
        try {
            RandomAccessFile rf = new RandomAccessFile(path+"\\"+filename, "rw");       
//            int new_length = 0;        
//            for (String temp_data21 : temp_data2) {
//                new_length+=temp_data21.length();
//            }          
              rf.setLength(0);                 
              rf.close();          
              writelib.Writer.writeData_Block(filename, path, temp_data2, true, enc_type);
        } catch (Exception ex){
            ex.printStackTrace();   
             }        
          }  
    }
    
    /**
     * It can be used to update a file by either deleting certain data from file
     * or by overwriting it with new data.
     * "key" can be used to provide custom encryption key(128 bit). 
     * @param filename
     * @param path
     * @param old_data
     * @param new_data
     * @param enc_type
     * @param key
     * @param delete_mode 
     */
    public static void overwrite_Line(String filename, String path, String old_data,
            String new_data, int enc_type, String key, int delete_mode){
     
        File f;
          f = new File(path+"\\"+filename);
          if(!f.exists()){         
            try { 
                throw new FileNotFound("No such file");
            } catch (FileNotFound ex) {
              ex.printStackTrace();
            }
          }
          else{           
              String temp_data[] = readlib.Reader.readAll(filename, path, enc_type, key);
              int size = 0;
              for (String temp_data1 : temp_data) {
                 size++; 
              }               
              String temp_data2[];
              if(delete_mode==Writer.DELETE){
               temp_data2 = new String[size-1];
              }else{
               temp_data2 = new String[size];
              }  
              int counter =0;                       
              if(delete_mode==Writer.DELETE){              
                  for (String temp_data21 : temp_data) {                    
                      if(temp_data21.equals(old_data)){
                         continue;
                      }
                      else{
                          temp_data2[counter] = temp_data21;
                          counter++;
                      }                    
                  }                                
              }
              else if(delete_mode==Writer.UPDATE){                
                  for (String temp_data21 : temp_data) {                     
                      if(temp_data21.equals(old_data)){                      
                          temp_data2[counter] = new_data;
                          counter++;  
                      }
                      else{
                          temp_data2[counter] = temp_data21;
                          counter++;
                      }                     
                  }
              }
                       
        try {
            RandomAccessFile rf = new RandomAccessFile(path+"\\"+filename, "rw");       
//            int new_length = 0;        
//            for (String temp_data21 : temp_data2) {
//                new_length+=temp_data21.length();
//            }          
              rf.setLength(0);                 
              rf.close();          
              writelib.Writer.writeData_Block(filename, path, temp_data2, true, enc_type, key);
        } catch (Exception ex){
            ex.printStackTrace();   
             }        
          }  
    }
    
    /**
     * This method can be used to clear all data from file.
     * @param filename
     * @param path
     */
    public static void clearFile(String filename, String path){
        File f;
          f = new File(path+"\\"+filename);
          if(!f.exists()){         
            try { 
                throw new FileNotFound("No such file");
            } catch (FileNotFound ex) {
               ex.printStackTrace();
            }
          }
        try {
            RandomAccessFile rf = new RandomAccessFile(path+"\\"+filename, "rw");      
            rf.setLength(0);     
            rf.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
