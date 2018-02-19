package setup;

import customexceptions.DeleteFolderError;
import customexceptions.FileNotFound;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SetupManager {
    
    /**
     * Can be used to create folder
     * Note: The original way to accomplish this is also easy but just to add 
     * more usability. 
     * @param folder_name
     * @param path 
     */
    public static void createFolder(String folder_name, String path){
        new File(path+"\\"+folder_name).mkdir();                         
    }
  
    /**
     * Removes the folder if empty.
     * @param folder_name
     * @param path
     */
    public static void removeFolder(String folder_name, String path){
        File folderName = new File(path+"//"+folder_name);
        if (!folderName.exists()) {
            try {
                throw new FileNotFound("Folder not found");
            } catch (FileNotFound ex) {
                ex.printStackTrace();
            }
        }else {
           folderName.delete();
           if(!folderName.delete()){
               try {
                   throw new DeleteFolderError("Cant delete NON-EMPTY folders");
               } catch (DeleteFolderError ex) {
                   ex.printStackTrace();
               }
           }
        }
    }
    
    /**
     * This methods loops in folder to get list of all folders and files within
     * and removes them.
     * @param file 
     */
    private static void deleteDir(File file) {
    File[] contents = file.listFiles();
    if (contents != null) {
        for (File f : contents) {
            deleteDir(f);
        }
    }
    file.delete();
    }
    
    /**
     * Removes the folder regardless of its contents.
     * @param folder_name
     * @param path
     */
    public static void removeFolder_contents(String folder_name, String path){
        File folderName = new File(path+"//"+folder_name);
        if (!folderName.exists()) {
            try {
                throw new FileNotFound("Folder not found");
            } catch (FileNotFound ex) {
                ex.printStackTrace();
            }
        }else {
            deleteDir(folderName);
        }
    }
    
    /**
     * Can be used to create file at target location.
     * @param filename
     * @param path
     */
    public static void createFile(String filename, String path){
        File f;
          f = new File(path+"\\"+filename);
          if(!f.exists()){
            try {
             f.createNewFile();
            } catch (IOException ex) {
             ex.printStackTrace();
            }
          }
    } 
       
    /**
     * Deletes the file
     * @param filename
     * @param path 
     */
    public static void removeFile(String filename, String path) {
       File f;
          f = new File(path+"\\"+filename);
          if(!f.exists()){
           try {
               throw new FileNotFound("File not found");
           } catch (FileNotFound ex) {
              ex.printStackTrace();
           }
          }
          else{
              f.delete();
          }
       } 
    
    /**
     * This method can rename both file and folder.
     * @param filename
     * @param path
     * @param new_name 
     */        
    public static void rename(String filename, String path, String new_name){
        File f;
          f = new File(path+"\\"+filename);
          if(!f.exists()){
            try {
                throw new FileNotFound("File/folder not found");
            } catch (FileNotFound ex) {
               ex.printStackTrace();
            }
          }
          else{
              File f2 = new File(path+"//"+new_name);
              f.renameTo(f2);
          }
    }
    
    /**
     * Returns popular path that you might need while working on files.
     * saves a bit of time and effort to get these paths right every time.
     * @param str
     * @return 
     */
    public static String getDir(String str){
        String val = "";
        switch (str) {
            case "home":
                val = System.getProperty("user.home");
                break;
            case "documents":
                val = System.getProperty("user.home")+"\\Documents";
                break;
            case "downloads":
                val = System.getProperty("user.home")+"\\Downloads";
                break;
            case "desktop":
                val = System.getProperty("user.home")+"\\Desktop";
                break;
            case "username":
                val = System.getProperty("user.name");
        }
        return val;
    }
    
    /**
     * This method helps to copy/backup an entire directory to a destination
     * location/path.
     * @param target1
     * @param destination1 
     */
    public static void backupFolder(String target1, String destination1){
        File target = new File(target1);
    	File destination = new File(destination1);
    	if(!target.exists()){
            try {
                throw new FileNotFound("No such folder");
            } catch (FileNotFound ex) {
                ex.printStackTrace();
            }
        }else{
           try{
        	copyFolder(target,destination);
           }catch(IOException e){
        	e.printStackTrace();
           }
        }
    }
    
    /**
     * This methods loops through contents of a folder to observe its file
     * structure. After that a new similar structure is created at destination
     * and content is copied.
     * @param src
     * @param dest
     * @throws IOException 
     */
    private static void copyFolder(File src, File dest)
    	throws IOException{

    	if(src.isDirectory()){  		
    		if(!dest.exists()){
    		   dest.mkdir();
    		}   		
    		String files[] = src.list();
    		for (String file : files) {  		 
    		   File srcFile = new File(src, file);
    		   File destFile = new File(dest, file);   		  
    		   copyFolder(srcFile,destFile);
    		}
    	}else{
    		InputStream in = new FileInputStream(src);
    	        OutputStream out = new FileOutputStream(dest);
    	        byte[] buffer = new byte[1024];
    	        int length;   	       
    	        while ((length = in.read(buffer)) > 0){
    	    	   out.write(buffer, 0, length);
    	        }
    	        in.close();
    	        out.close();
    	}           
    }   
}
