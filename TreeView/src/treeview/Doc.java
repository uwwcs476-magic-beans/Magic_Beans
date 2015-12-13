/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treeview;
import java.util.ArrayList; 

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.StringTokenizer;
import java.io.*;
/**
 *
 * @author Alex
 */
public class Doc {
    String filename="";
    ArrayList<String> fileBreakdown = new ArrayList<String>();
    ArrayList<String> scopeChart = new ArrayList<String>();
    ArrayList<ScopedSection> sections = new ArrayList<ScopedSection>();
    
    
    public void buildSC(){
    scopeChart.add("/part");
    scopeChart.add("/chapter");
    scopeChart.add("/section");
    scopeChart.add("/subsection");
    scopeChart.add("/subsubsection");
    scopeChart.add("/paragraph");
    scopeChart.add("/subparagraph");
    }
    
    public Doc(String filename){
        this.filename=filename;
    }
    
    public void populate(String fileName){
        //fileBreakdown.add("/chapter{hello}");
        //fileBreakdown.add("what's up");
        //fileBreakdown.add("/section{a section}");
        
        // The name of the file to open.
        //String fileName = "sample.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                fileBreakdown.add(line);
                
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        
    }
    public String analyze(String ex){
        String Pat = "^[/]\\w+[{][\\w|\\s]+[}]";
        Pattern x = Pattern.compile(Pat);
        String foundVal ="";
        String result = "NO MATCH";
        
        Matcher m = x.matcher(ex);
      if (m.find( )) {
         //System.out.println("Found value: " + m.group(0) );
         
         StringTokenizer st1 = new StringTokenizer(m.group(0), "{}");
         foundVal = st1.nextToken();
         if(foundVal.equals("/part") || foundVal.equals("/chapter") || foundVal.equals("/section") || foundVal.equals("/subsection") || foundVal.equals("/subsubsection") || foundVal.equals("/paragraph") || foundVal.equals("/subparagraph")){
             //System.out.println(st1.nextToken());
             result = st1.nextToken();
         }
      } else {
         //System.out.println("NO MATCH");
          result = "NO MATCH";
      }
      return result;
    }
    
    public void initiate(){
      int x = 0;
      //Doc document = new Doc(name);
     
       //System.out.println(document.fileBreakdown.size());
       populate(filename);
       while(x < fileBreakdown.size()){
           if(!analyze(fileBreakdown.get(x)).equals("NO MATCH")){
               System.out.println(analyze(fileBreakdown.get(x)) + " " + x);
              
           }
           x++;
       }
    }
    
}
