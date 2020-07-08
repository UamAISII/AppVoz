/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ontologias;

import PLN.Procesamiento;
import PLN.Limpieza;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gabrielagarcia
 */
public class Tupla {
    
    public String instante(String instante,ArrayList<String> extraccion){

                           
                           Pattern lu =Pattern.compile("^[0-9]{1,2}");
                           String fecha="",palabra,dia="",mes="";
                           Boolean bdia=false,bmes=false;
                           String []sujeto;
                           sujeto=instante.split(" ");
                           
                           for (int i = 0; i < sujeto.length; i++) {  
                            Matcher f;
                            palabra=sujeto[i];
                            f = lu.matcher(palabra);
                            if(f.matches()){
                                dia=palabra;
                           }else{
                                if(sujeto[i].contains("enero")||sujeto[i].contains("febrero")||sujeto[i].contains("marzo")||sujeto[i].contains("agosto")||
                                        sujeto[i].contains("abril")||sujeto[i].contains("mayo")||sujeto[i].contains("junio")||sujeto[i].contains("julio")||
                                        sujeto[i].contains("septiembre")||sujeto[i].contains("octubre")||sujeto[i].contains("noviembre")||sujeto[i].contains("diciembre")){
                                    mes=palabra;
                                }
                            }   
                           }
                           if(dia.equals("")){
                               bdia=true;

                           }
                           if(mes.equals("")){
                                   bmes=true;
                           }
                           
                           if(bdia.equals(false)){
                               fecha=fecha+dia;
                           }
                           if(bmes.equals(false)){
                               fecha=fecha+" "+mes;
                           }
                           
                           
                           return fecha;
                         
                           
                       
    }
    
    public String construccionInstante(ArrayList<String> tupla){
        String instanteN="";
        String fecha[]=tupla.get(1).split(" ");
        String sujeto=fecha[1];
        Busqueda b=new Busqueda();
        String mes=b.mesDigito(sujeto);
        String dia=sujeto=fecha[0];
        
        
        
        
        
        return instanteN;
    }

    public ArrayList<String> tuplaOntologica (ArrayList<String> extraccion){
        ArrayList<String> tupla = new ArrayList<String>();
        String clase;
        
        ArrayList<String> traduccion = new ArrayList<String>();
        Limpieza l = new Limpieza(); 
        traduccion=l.palabrasArchivo("Palabras/traduccion.txt");
        String t=extraccion.get(3);
        String c;
        String [] a;
        
        //traduccion de clases (ubicacion 4 de tupla)
        for (int i = 0; i < traduccion.size(); i++) {
            c=traduccion.get(i);
            a=c.split(":");
            if(t.equalsIgnoreCase(a[1])){
                 t=a[0];
            } 
        }
        
        
        //tipo Persona
        
       if(extraccion.get(0).equalsIgnoreCase("Persona")){
           tupla.add("person");
           tupla.add(extraccion.get(1));
           //traduccion de dataproperty y objectproperty
           if(extraccion.get(2).contains("nombre")){
               if(extraccion.get(3).equalsIgnoreCase("administrativo")||extraccion.get(3).equalsIgnoreCase("profesor")||extraccion.get(3).equalsIgnoreCase("ayudante")||
                       extraccion.get(3).equalsIgnoreCase("alumno")||extraccion.get(3).equalsIgnoreCase("alumno o ayudante")||extraccion.get(3).equalsIgnoreCase("profesor o ayudante")||
                       extraccion.get(3).equalsIgnoreCase("profesor-ayudante-alumno")){
                   tupla.add("hasName");
               }
           }else{
               if(extraccion.get(2).equalsIgnoreCase("numero economico")){
                   tupla.add("hasEconomicNumber");
               }else{
                   if(extraccion.get(2).equalsIgnoreCase("matricula")){
                       tupla.add("hasStudentID");
                   }else{
                       if(extraccion.get(2).equalsIgnoreCase("correo")){
                           tupla.add("hasEmail");
                       }
                   }
               }
           }

           //solo pueden ser dataproperty
           tupla.add("DataProperty");   
           tupla.add(t);
           
           String r=extraccion.get(4);
           if(r.contains("nombre")){
               tupla.add("hasName");
           }else{
               if(r.contains("numero economico")){
                   tupla.add("hasEconomicNumber");
               }else{
                   if(r.contains("matricula")){
                       tupla.add("hasStudentID");
                   }else{
                       if(r.contains("correo")){
                           tupla.add("hasEmail");
                       }else{
                           if(r.contains("quien")){
                               if(tupla.get(4).equals("Professor")&&tupla.get(2).equals("hasName")){
                                   tupla.add("hasCategory");
                               }else{
                                   tupla.add("hasName");
                               } 
                           }else{
                               if(r.contains("categoria")){
                                   tupla.add("hasCategory");
                               }else{
                                   tupla.add("hasName");
                               }
                           }
                       }
                   }
               } 
           }
           tupla.add("DataProperty");
       }else{
           
           //tipo espacio fisico
           if(extraccion.get(0).equals("Espacio Fisico")){
               tupla.add("physicalspace");
               tupla.add(extraccion.get(1));
               if(extraccion.get(2).contains("nombre")){
                   //te da el nombre del espacio
                   tupla.add("hasNamePhysicalSpace");
               }
               else{
                   tupla.add(" ");
               }
               tupla.add("DataProperty");
               tupla.add(t);
               
               if(tupla.get(4).contains("Building")||tupla.get(4).contains("Corridor")||
                       tupla.get(4).contains("GreenArea")||tupla.get(4).contains("Parking")||tupla.get(4).contains("PublicPlaza")){
                   tupla.add("isBesideOf");
                   tupla.add("ObjectProperty");
                   tupla.add("containsPhysicalSpace");
                   tupla.add("ObjectProperty");
               }else{
                   tupla.add("hasLevel");
                   tupla.add("DataProperty");
               }

           }else{
               
               //tipo evento
               if(extraccion.get(0).equals("Evento")){
                   tupla.add("intelligentenviroment");
                   
                   //creacion del instante
                       if(extraccion.get(2).contains("instante")){
                           //datos de instante
                           String instante=extraccion.get(1);
                           String fechaE=instante(instante,extraccion);

                           tupla.add(fechaE);
                           tupla.add("hasTemporalEntity");
                           tupla.add("DataProperty");
                           tupla.add(t);
                           tupla.add("hasEventName");
                           tupla.add("DataProperty");
                           
                       }
                       //modificiacion evento evaluaciones 6 de febrero
                   if((extraccion.get(2).contains("nombre"))||(extraccion.contains("cuando"))){
                       tupla.add(extraccion.get(1));
                       tupla.add("hasEventName");
                       tupla.add("DataProperty");
                       tupla.add(t);
                       tupla.add("hasTemporalEntity");
                       tupla.add("ObjectProperty");
                       
                   }
                   if(extraccion.contains("descripcion")){
                       tupla.add(extraccion.get(1));
                       tupla.add("hasEventName");
                       tupla.add("DataProperty");
                       tupla.add(t);
                       tupla.add("hasDescription");
                       tupla.add("DataProperty");
                    }
               }else{
                   //tipo Espacio Fisico - Persona
                   if(extraccion.get(0).equals("Espacio Fisico-Persona")){
                       tupla.add("physicalspace-person");
                       tupla.add(extraccion.get(1));
                       if(extraccion.get(3).contains("profesor")&&extraccion.get(2).contains("nombre")){
                           tupla.add("hasName");
                           tupla.add("DataProperty");
                       }
                       if(extraccion.get(2).contains("cubiculo")){
                           tupla.add("hasNamePhysicalSpace");
                           tupla.add("DataProperty");
                       }
                       
                       tupla.add(t);
                       
                       if(extraccion.get(4).contains("cubiculo")||extraccion.get(4).contains("donde")){
                           tupla.add("isAssignedTo");
                           tupla.add("ObjectProperty");
                       }
                       if(extraccion.get(4).contains("quien")||extraccion.get(4).contains("profesor")){
                           tupla.add("hasPersonAssigned");
                           tupla.add("ObjectProperty");
                       }
                       
                       
                   }else{
                       //tipo Evento - Espacio Fisico
                       if(extraccion.get(0).equals("Evento-Espacio Fisico")){
                           tupla.add("event-physicalspace");
                           if(extraccion.get(2).contains("instante")){
                              String fechaEF=instante(extraccion.get(1),extraccion);
                              tupla.add(fechaEF); 
                              tupla.add("hasTemporalEntity");
                              tupla.add("DataProperty");
                              tupla.add(t);
                              if(extraccion.contains("donde")||extraccion.contains("ubicacion")){
                                  tupla.add("happensIn");
                                  tupla.add("ObjectProperty");
                              }
                           }
                           if(extraccion.get(2).contains("nombre")){
                               tupla.add(extraccion.get(1));
                               tupla.add("hasEventName");
                               tupla.add("DataProperty");
                               tupla.add(t);
                               if(extraccion.contains("donde")){
                                   tupla.add("happensIn");
                                   tupla.add("ObjectProperty");
                               }
                            }  
                       }else{
                           //tipo Evento - Persona
                           if(extraccion.get(0).equals("Evento-Persona")){
                               tupla.add("event-person");
                               if(extraccion.get(2).contains("instante")){
                                   String fechaE=instante(extraccion.get(1),extraccion);
                                   tupla.add(fechaE);
                                   tupla.add("hasTemporalEntity");
                                   tupla.add("DataProperty");
                                   //traduccion de clases (ubicacion 4 de tupla)
                                   t=extraccion.get(4);
                                     for (int i = 0; i < traduccion.size(); i++) {
                                         c=traduccion.get(i);
                                         a=c.split(":");
                                         if(t.equals(a[1])){
                                              t=a[0];
                                         } 
                                     }
                                   tupla.add(t);
                                   tupla.add("hasPersonInvolved");
                                   tupla.add("ObjectProperty");
                               }else{
                                   if(extraccion.contains("quien")){
                                   tupla.add(extraccion.get(1));
                                   tupla.add("hasEventName");
                                   tupla.add("DataProperty");
                                   tupla.add(t);
                                   tupla.add("hasPersonInvolved");
                                   tupla.add("ObjectProperty");
                               }
                               }
                               
                               
                               
                           }else{
                               tupla.add("No se encontro una tupla ontologica adecuada");
                           }
                       }
                   }
               }
           }
           
       }
        
       
     
        
        return tupla;
    }
    
}
