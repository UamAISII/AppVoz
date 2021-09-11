/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import Ontologias.Busqueda;
import Ontologias.Tupla;
import PLN.Procesamiento;
import java.util.ArrayList;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 *
 * @author gabrielagarcia
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SWRLParseException, SQWRLException {

        //Procesamiento de lenguaje natural
        ArrayList<String> procesamiento = new ArrayList<String>();
        Procesamiento p = new Procesamiento();

        //Ontologias
        ArrayList<String> tuplaOntologia = new ArrayList<String>();
        Tupla t = new Tupla();
        Busqueda b = new Busqueda();
        String respuesta;

///////////////////////////////////////         1 pregunta          /////////////////////////////

        //Persona
        //String pregunta="Dime el nombre de 35691";
        //String pregunta="Dime el nombre del alumno con matricula 206 205 88 8";
        //String pregunta="Dime el nombre del estudiante con matricula 2074 00656";
        //String pregunta="¿Cual es el numero economico del profesor Rodriguez Diaz Jaime";
        //String pregunta="Dime el correo de la profesora Bravo Contreras Maricela Claudia";
        //String pregunta="Dime la matricula de Figueroa Torres Eduardo";
        //String pregunta="la categoría de alcántara nava josé luis";
        //String pregunta="¿Quien es Reyes Ortiz Jose Alejandro?";
        
        //Espacio Fisico
        //String pregunta="¿Donde esta el edificio W?";
        //String pregunta="Cuando es el seminario de fisica";
        //String pregunta="Dime los lugares del edificio C";
        //String pregunta="dime donde esta el cubiculo h 244";
        //String pregunta="Donde se encuentra la libreria";
        
        //Evento
        //String pregunta="¿Cuando se realizara el seminario de fisica?";
        //String pregunta = "Dame informacion del seminario del 06 junio";
        //String pregunta="Dime el nombre del seminario del 06 de junio"; 
        //String pregunta="Dame la descripcion del seminario de fisica";
        //String pregunta="Dime la fecha del curso de procesamiento de lenguaje natural";
        String pregunta="Cuando es el curso de seminario 2 de maestria";
        
        //Evento-Persona
        //String pregunta="¿Quienes participan en la asesoria de sistemas distribuidos?";
        //String pregunta="¿Quienes participan en la asesoria del 06 de junio?";
         
        //Evento-Espacio Fisico
        //String pregunta="¿Donde se realizara el seminario de fisica?";
        //String pregunta="¿Donde se realizara el seminario del 06 de junio?";
        
        //Espacio Fisico-Persona
        //String pregunta="De quien es la oficina h254";
        //String pregunta="¿Cual es el cubiculo del profe reyes ortiz jose alejandro";
        
        procesamiento = p.extraccionFinal(pregunta);
        tuplaOntologia = t.tuplaOntologica(procesamiento);

        System.out.println("Pregunta: " + pregunta + "\n");

        System.out.println("Extraccion: " + procesamiento + "\n");

        System.out.println("Tupla: " + tuplaOntologia + "\n" + "\n");

        respuesta = b.busqueda(tuplaOntologia);
        System.out.println("\nRespuesta: " + respuesta);

        //System.out.println(procesamiento);
        //System.out.println(tuplaOntologia);
        //System.out.println(respuesta);
        
        
        
////////////////////////////////        corpus de preguntas         //////////////////////////////////


//       ArrayList<String> preguntas = new ArrayList<String>();
//       Limpieza l=new Limpieza();
//       preguntas=l.palabrasArchivo("Palabras/corpus100.txt");
//        
//        for (int i = 0; i < preguntas.size(); i++) {
// 
//        procesamiento=p.extraccionFinal(preguntas.get(i));
//        tuplaOntologia=t.tuplaOntologica(procesamiento);
//        respuesta=b.busqueda(tuplaOntologia);
//           System.out.println("Pregunta: "+preguntas.get(i));
//           System.out.println("Respuesta: "+respuesta);
//           System.out.println();
//            
//        }
    }

}
