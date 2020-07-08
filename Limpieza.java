/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PLN;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gabrielagarcia
 */
public class Limpieza {
    
        //palabras archivo con arraylist 
        public ArrayList<String> palabrasArchivo(String archivo){       
        
        ArrayList<String> pArchivo = new ArrayList<String>();
        File z = new File(archivo);
        BufferedReader entrada;
        
        try {
            entrada = new BufferedReader(new FileReader(z));
            while (entrada.ready()) {
                pArchivo.add(entrada.readLine());
                 
            }  
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pArchivo;
        }
    
        
        //nombres de lugares cubiculos
        public String lugares(String oracionC){

        String palabra=oracionC;
        String oracionF="";
        Matcher c1,c2,c3,l2;
        String oracion2[];
        
        Pattern lu2 = Pattern.compile(".*[a-z]{1,2}[ x0Bf ][0-9].*");
        Pattern cub2 = Pattern.compile(".*[ x0Bf ][h|p|w][ x0Bf ][0-9].*");
        Pattern cub3 = Pattern.compile(".*[ x0Bf ]hp[ x0Bf ][0-9].*");
        Pattern cub4 = Pattern.compile(".*[ x0Bf ]ho[ x0Bf ][0-9].*");
        
        l2=lu2.matcher(palabra);
        c1 = cub2.matcher(palabra);
        c2 = cub3.matcher(palabra);
        c3 = cub4.matcher(palabra);

                    if(l2.matches()||c1.matches()||c2.matches()||c3.matches()){
                        oracion2=palabra.split(" ");
                        Pattern uno = Pattern.compile("[h|p|w|g|b|d]");
                        Matcher dos;
                        for (int i = 0; i < oracion2.length; i++) {
                            dos=uno.matcher(oracion2[i]);
                            if(dos.matches()||(oracion2[i].equals("hp"))||(oracion2[i].equals("ho"))){
                                //oracionC=oracionC+" "+oracion2[i]+oracion2[i+1];
                                oracionF+=" "+oracion2[i]+oracion2[i+1];
                                i=i+1;
                            }else{
                                //oracionC=oracionC+" "+oracion2[i];
                                oracionF+=" "+oracion2[i];
                                
                            }
                            
                        }
                        System.out.println("Encontrado:"+oracionF);
                    }else{
                        oracionF=oracionC;
                    }
            
            return oracionF;
        }
        
        //juntar digitos de numero economico o matricula        
        public String matriculas(String oracionC){
        String oracionF="";
        Pattern matricula2 = Pattern.compile("^[0-9]{2,3}");
        Pattern matricula =Pattern.compile("^[0-9].*");
        String[] oracionS=oracionC.split(" ");
                        if(oracionC.contains("matricula")||oracionC.contains("alumno")||oracionC.contains("numero economico")||oracionC.contains("quien es")||oracionC.contains("nombre")){
                            int bandera=0;
                            for (int i = 0; i < oracionS.length; i++) {
                                Matcher m,m2;
                                m = matricula.matcher(oracionS[i]);
                                m2 = matricula2.matcher(oracionS[i]);
                                if (m.matches() || m2.matches()) { 
                                    if(bandera==0){
                                        //oracionC=oracionC+" "+oracionS[i];
                                        oracionF+=" "+oracionS[i];
                                        bandera++;
                                    }else{
                                        //oracionC=oracionC+oracionS[i];
                                        oracionF+=oracionS[i];
                                    }
                                }else{
                                    if(i==0){
                                        //oracionC=oracionS[i];
                                        oracionF=oracionS[i];
                                    }else{
                                         //oracionC=oracionC+" "+oracionS[i];
                                        oracionF+=" "+oracionS[i];
                                    }

                                }


                            }
                                    System.out.println("2="+oracionF);
                        }else{
                            oracionF=oracionC;
                        }
            
            return oracionF;
        }
        
        
     //Acronimos y palabras limpiezaPregunta
     public String limpiezaPregunta(String oracion){
         
         ArrayList<String> coloquiales = new ArrayList<String>();   
        String oracionC=oracion;
        //coloquiales=l.palabrasArchivo("Palabras/coloquiales.txt");
        coloquiales=palabrasArchivo("Palabras/coloquiales.txt");
        String[] coloquial;
        String c;
        for (int i = 0; i < coloquiales.size(); i++) {
            c=coloquiales.get(i);
            coloquial=c.split(":");
            for (int j = 0; j < coloquial.length; j++) {
                oracionC=oracionC.replace(coloquial[j], coloquial[0]);
                oracionC=oracionC.replace("á","a");
                oracionC=oracionC.replace("é","e");
                oracionC=oracionC.replace("í","i");
                oracionC=oracionC.replace("ó","o");
                oracionC=oracionC.replace("ú","u");
                oracionC=oracionC.replace("-","");
                oracionC=oracionC.replace("¿","");
                oracionC=oracionC.replace("?","");
                oracionC=oracionC.toLowerCase();
            }
            
        }
        oracionC=lugares(oracionC);
        
        oracionC=matriculas(oracionC);        

        return oracionC;
    }
}
