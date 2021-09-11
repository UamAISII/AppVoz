/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ontologias;

import PLN.Limpieza;
import java.util.ArrayList;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;

/**
 *
 * @author gabrielagarcia
 */
public class Busqueda {

    public String construccion(ArrayList<String> tupla) {
        String query = "";
        String pr1 = "", pr2 = "";
        String on1 = "", on2 = "";
        String pro = "";
        String prg = "intelligentenviroment";

        //Consulta Espacio fisico o evento
        if (tupla.get(0).equals("physicalspace")) {

            if (tupla.get(6).equals("ObjectProperty")) {

                query = tupla.get(0) + ":" + tupla.get(4) + "(?v1)^" + tupla.get(0) + ":" + tupla.get(2) + "(?v1,\"" + tupla.get(1).trim().toUpperCase() + "\")^" + tupla.get(0) + ":" + tupla.get(5) + "(?v1,?r1)^" + tupla.get(0) + ":hasNamePhysicalSpace(?r1,?r2)->sqwrl:select(?r2)";

                String query2 = tupla.get(0) + ":" + tupla.get(4) + "(?v1)^" + tupla.get(0) + ":" + tupla.get(2) + "(?v1,\"" + tupla.get(1).trim().toUpperCase() + "\")^" + tupla.get(0) + ":" + tupla.get(7) + "(?v1,?r3)^" + tupla.get(0) + ":hasNamePhysicalSpace(?r3,?r4)->sqwrl:select(?r4)";

                query = query + "divide" + query2;

            } else {
                query = tupla.get(0) + ":" + tupla.get(4) + "(?v1)^" + tupla.get(0) + ":" + tupla.get(2) + "(?v1,\"" + tupla.get(1).trim().toUpperCase() + "\")^" + tupla.get(0) + ":" + tupla.get(5) + "(?v1,?r1)->sqwrl:select(?r1)";
            }

        }

        //Consulta Persona
        if (tupla.get(0).equals("person")) {

            query = tupla.get(0) + ":" + tupla.get(4) + "(?v1)^" + tupla.get(0) + ":" + tupla.get(2) + "(?v1,\"" + tupla.get(1).trim().toUpperCase() + "\")^" + tupla.get(0) + ":" + tupla.get(5) + "(?v1,?r1)->sqwrl:select(?r1)";

        }

        //Consulta Evento
        if (tupla.get(0).equals("intelligentenviroment")) {
            if (tupla.get(5).equals("hasDescription")) {
                query = tupla.get(0) + ":" + tupla.get(4) + "(?v1)^" + tupla.get(0) + ":" + tupla.get(2) + "(?v1,\"" + tupla.get(1).trim().toUpperCase() + "\")^" + tupla.get(0) + ":" + tupla.get(5) + "(?v1,?r1)->sqwrl:select(?r1)";
            }
            if (tupla.get(5).equals("hasTemporalEntity")) {
                query = tupla.get(0) + ":Event(?v1)^time:Instant(?v2)^" + tupla.get(0) + ":" + tupla.get(2) + "(?v1,\"" + tupla.get(1).trim().toUpperCase() + "\")^" + tupla.get(0) + ":" + tupla.get(5) + "(?v1,?v2)^time:hasDay(?v2,?r1)^time:hasMonth(?v2,?r2)->sqwrl:select(?r1)^sqwrl:select(?r2)";
            }

        }

        //consulta Espacio Fisico-Persona
        if (tupla.get(0).equals("physicalspace-person")) {
            if (tupla.get(2).equals("hasName")) {
                pr1 = "person";
                pr2 = "physicalspace";
                on1 = "Person";
                on2 = "PhysicalSpace";
                pro = "hasNamePhysicalSpace";
            }
            if (tupla.get(2).equals("hasNamePhysicalSpace")) {

                pr1 = "physicalspace";
                pr2 = "person";
                on1 = "PhysicalSpace";
                on2 = "Person";
                pro = "hasName";
            }
            query = pr1 + ":" + on1 + "(?v1)^" + pr1 + ":" + tupla.get(2) + "(?v1,\"" + tupla.get(1).trim().toUpperCase() + "\")^" + prg + ":" + tupla.get(5) + "(?v1,?v2)^" + pr2 + ":" + on2 + "(?v2)^" + pr2 + ":" + pro + "(?v2,?r1)->sqwrl:select(?r1)";
        }

        //Consulta Espacio Fisico - Evento
        if (tupla.get(0).equals("event-physicalspace")) {

            pr1 = "intelligentenviroment";
            pr2 = "physicalspace";
            on1 = "Event";
            on2 = "PhysicalSpace";
            pro = "hasNamePhysicalSpace";

            query = pr1 + ":" + on1 + "(?v1)^" + pr1 + ":" + tupla.get(2) + "(?v1,\"" + tupla.get(1).trim().toUpperCase() + "\")^" + prg + ":" + tupla.get(5) + "(?v1,?v2)^" + pr2 + ":" + on2 + "(?v2)^" + pr2 + ":" + pro + "(?v2,?r1)->sqwrl:select(?r1)";
        }
        
        //Consulta Evento-Persona
        if (tupla.get(0).equals("event-person")) {

            pr1 = "intelligentenviroment";
            pr2 = "person";
            on1 = "Event";
            on2 = "Person";
            pro = "hasName";

            query = pr1 + ":" + on1 + "(?v1)^" + pr1 + ":" + tupla.get(2) + "(?v1,\"" + tupla.get(1).trim().toUpperCase() + "\")^" + prg + ":" + tupla.get(5) + "(?v1,?v2)^" + pr2 + ":" + on2 + "(?v2)^" + pr2 + ":" + pro + "(?v2,?r1)->sqwrl:select(?r1)";
        }
        
        return query;
    }

    public String digitoMes(String dato) {
        String m = "";
        if (dato.equals("01")) {
            m = "enero";
        }
        if (dato.equals("02")) {
            m = "febrero";
        }
        if (dato.equals("03")) {
            m = "marzo";
        }
        if (dato.equals("04")) {
            m = "abril";
        }
        if (dato.equals("05")) {
            m = "mayo";
        }
        if (dato.equals("06")) {
            m = "junio";
        }
        if (dato.equals("07")) {
            m = "julio";
        }
        if (dato.equals("08")) {
            m = "agosto";
        }
        if (dato.equals("09")) {
            m = "septiembre";
        }
        if (dato.equals("10")) {
            m = "octubre";
        }
        if (dato.equals("11")) {
            m = "noviembre";
        }
        if (dato.equals("12")) {
            m = "diciembre";
        }

        return m;
    }

    public String mesDigito(String dato) {
        String m = "";

        if (dato.equals("enero")) {
            m = "01";
        }
        if (dato.equals("febrero")) {
            m = "02";
        }
        if (dato.equals("marzo")) {
            m = "03";
        }
        if (dato.equals("abril")) {
            m = "04";
        }
        if (dato.equals("mayo")) {
            m = "05";
        }
        if (dato.equals("junio")) {
            m = "06";
        }
        if (dato.equals("julio")) {
            m = "07";
        }
        if (dato.equals("agosto")) {
            m = "08";
        }
        if (dato.equals("septiembre")) {
            m = "09";
        }
        if (dato.equals("octubre")) {
            m = "10";
        }
        if (dato.equals("noviembre")) {
            m = "11";
        }
        if (dato.equals("diciembre")) {
            m = "12";
        }
        return m;
    }

    public String busqueda(ArrayList<String> tupla) throws SWRLParseException, SQWRLException {

        String respuesta = "", resp = "", resp2 = "", resp3 = "", resp4 = "";
        Limpieza l = new Limpieza();
        ArrayList<String> p = new ArrayList<String>();
        p = l.palabrasArchivo("Palabras/plantilla.txt");
        ArrayList<String> o = new ArrayList<String>();
        o = l.palabrasArchivo("Palabras/traduccion.txt");
        String t = "";
        String c;
        String[] a, b;

        try {

            OWLOntology ontologyIE, ontologySN, ontologyPer, ontologyPS, ontologyTIME;
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

            String FileIE, IRIIE, FilePer, IRIPer, FilePM, IRIPM, FilePS, IRIPS, FileSN, IRISN, FileTime, IRITime;
            FileIE = "OntologiasUltimo/IntelligentEnviroment.owl";
            IRIIE = "http://www.semanticweb.org/profesor/ontologies/2018/2/IntelligentEnviroment";
            FilePer = "OntologiasUltimo/Person.owl";
            IRIPer = "http://www.semanticweb.org/gabrielagarcia/ontologies/2019/9/Person";
            FilePM = "OntologiasUltimo/PhysicalMeasure.owl";
            IRIPM = "http://www.semanticweb.org/asus/ontologies/2018/4/PhysicalMeasure";
            FilePS = "OntologiasUltimo/PhysicalSpace.owl";
            IRIPS = "http://www.semanticweb.org/usuario/ontologies/2016/0/PhysicalSpace";
            FileSN = "OntologiasUltimo/SensorNetwork.owl";
            IRISN = "http://www.semanticweb.org/profesor/ontologies/2017/10/SensorNetwork";
            FileTime = "OntologiasUltimo/Time.owl";
            IRITime = "http://www.semanticweb.org/usuario/ontologies/2018/2/Time";

            manager.addIRIMapper(new SimpleIRIMapper(IRI.create(IRIPer), IRI.create(new File(FilePer))));
            manager.addIRIMapper(new SimpleIRIMapper(IRI.create(IRIPM), IRI.create(new File(FilePM))));
            manager.addIRIMapper(new SimpleIRIMapper(IRI.create(IRIPS), IRI.create(new File(FilePS))));
            manager.addIRIMapper(new SimpleIRIMapper(IRI.create(IRISN), IRI.create(new File(FileSN))));
            manager.addIRIMapper(new SimpleIRIMapper(IRI.create(IRITime), IRI.create(new File(FileTime))));

            ontologyPer = manager.loadOntologyFromOntologyDocument(IRI.create(new File(FilePer)));
            ontologySN = manager.loadOntologyFromOntologyDocument(IRI.create(new File(FileSN)));
            ontologyPS = manager.loadOntologyFromOntologyDocument(IRI.create(new File(FilePS)));
            ontologyTIME = manager.loadOntologyFromOntologyDocument(IRI.create(new File(FileTime)));
            ontologyIE = manager.loadOntologyFromOntologyDocument(IRI.create(new File(FileIE)));

            //System.out.println("cargadas");
            SQWRLQueryEngine queryEngine;

//////////////////////////////////////Preguntas sencillas
            //si es tipo persona
            if (tupla.get(0).equals("person")) {

                queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontologyPer);
                String query = construccion(tupla);
                System.out.println();
                System.out.println();
                System.out.println("Consulta: " + query);
                queryEngine.createSQWRLQuery("Q1", query);
                SQWRLResult result = queryEngine.runSQWRLQuery("Q1");

                while (result.next()) {

                    SQWRLLiteralResultValue nameValue = result.getLiteral("r1");
                    resp = nameValue.getString();

                }

                //traduccion de clases (ubicacion 4 de tupla)
                for (int i = 0; i < p.size(); i++) {
                    c = p.get(i);
                    b = c.split(":");
                    for (int j = 0; j < tupla.size(); j++) {
                        if (tupla.get(j).equals(b[1])) {
                            tupla.set(j, b[0]);
                        }
                    }
                }
                if ((tupla.get(5).equals("categoria")) || (tupla.get(5).equals("matricula"))) {
                    respuesta = "LA " + tupla.get(5) + " DE " + tupla.get(1).trim() + " ES " + resp;
                    respuesta = respuesta.toUpperCase();
                } else {
                    respuesta = "EL " + tupla.get(5) + " DE " + tupla.get(1).trim() + " ES " + resp;
                    respuesta = respuesta.toUpperCase();
                }
            }

            //si es tipo espacio fisico
            if (tupla.get(0).equals("physicalspace")) {

                String query2;
                SQWRLResult result, result2;
                queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontologyPS);
                SQWRLQueryEngine queryEngine2 = SWRLAPIFactory.createSQWRLQueryEngine(ontologyPS);
                String query = construccion(tupla);

                if (query.contains("divide")) {
                    a = query.split("divide");
                    query = a[0];
                    query2 = a[1];

                    queryEngine.createSQWRLQuery("Q1", query);
                    result = queryEngine.runSQWRLQuery("Q1");
                    System.out.println("Consulta 1: " + query);

                    while (result.next()) {

                        SQWRLLiteralResultValue nameValue = result.getLiteral("r2");
                        resp = resp + "," + nameValue.getString();

                    }

                    queryEngine2.createSQWRLQuery("Q2", query2);
                    result2 = queryEngine2.runSQWRLQuery("Q2");
                    System.out.println("Consulta 2: " + query2);

                    while (result2.next()) {

                        SQWRLLiteralResultValue nameValue2 = result2.getLiteral("r4");
                        resp2 = resp2 + "," + nameValue2.getString();
                    }

                    //traduccion de clases (ubicacion 4 de tupla)
                    for (int i = 0; i < p.size(); i++) {
                        c = p.get(i);
                        b = c.split(":");
                        for (int j = 0; j < tupla.size(); j++) {
                            if (tupla.get(j).equals(b[1])) {
                                tupla.set(j, b[0]);
                            }
                        }
                    }

                    respuesta = "EL " + tupla.get(1) + " ESTA " + tupla.get(5).trim() + " " + resp + " Y " + tupla.get(7) + " " + resp2;
                    respuesta = respuesta.toUpperCase();

                } else {
                    System.out.println();
                    System.out.println("Consulta: " + query);
                    queryEngine.createSQWRLQuery("Q1", query);
                    result = queryEngine.runSQWRLQuery("Q1");
                    while (result.next()) {

                        SQWRLLiteralResultValue nameValue = result.getLiteral("r1");
                        resp = resp + " " + nameValue.getString();

                    }

                    for (int i = 0; i < p.size(); i++) {
                        c = p.get(i);
                        b = c.split(":");
                        for (int j = 0; j < tupla.size(); j++) {
                            if (tupla.get(j).equals(b[1])) {
                                tupla.set(j, b[0]);
                            }
                        }
                    }
                    respuesta = "EL " + tupla.get(1) + " ESTA " + tupla.get(5).trim() + resp;
                    respuesta = respuesta.toUpperCase();

                }
            }

            //si es tipo evento
            if (tupla.get(0).equals("intelligentenviroment")) {
                //Descripcion de un evento
                if (tupla.get(5).equals("hasDescription")) {
                    queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontologyIE);
                    String query = construccion(tupla);
                    System.out.println();
                    System.out.println("Consulta: " + query);
                    queryEngine.createSQWRLQuery("Q1", query);
                    SQWRLResult result = queryEngine.runSQWRLQuery("Q1");

                    while (result.next()) {

                        SQWRLLiteralResultValue nameValue = result.getLiteral("r1");
                        resp = nameValue.getString();
                    }
                    respuesta = "EL " + tupla.get(1) + " trata de " + resp;
                    respuesta = respuesta.toUpperCase();
                }
                //Fecha de un evento por su nombre
                if (tupla.get(5).equals("hasTemporalEntity")) {
                    SQWRLResult result;
                    queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontologyIE);
                    String query = construccion(tupla);

                    System.out.println();
                    System.out.println("Consulta: " + query);
                    queryEngine.createSQWRLQuery("Q1", query);
                    result = queryEngine.runSQWRLQuery("Q1");

                    while (result.next()) {

                        SQWRLLiteralResultValue nameValue = result.getLiteral("r1");

                        SQWRLLiteralResultValue nameValue2 = result.getLiteral("r2");

                        resp = nameValue.getString();
                        resp2 = nameValue2.getString();
                        resp2 = digitoMes(resp2);
                    }
                    respuesta = "El " + tupla.get(1) + " se realizara el " + resp + " de " + resp2;
                    respuesta = respuesta.toUpperCase();
                }
            //}
                //nombre del evento por su fecha
                if (tupla.get(2).equals("hasTemporalEntity")) {
                    String fecha = tupla.get(1).trim();
                    char[] cadena = fecha.toCharArray();
                    String dia = "", mes = "";

                    for (int i = 0; i < cadena.length; i++) {
                        if (Character.isDigit(cadena[i])) {
                            dia += cadena[i];
                        }

                    }
                    for (int i = 0; i < cadena.length; i++) {
                        if (Character.isLetter(cadena[i])) {
                            mes += cadena[i];
                        }

                    }
                    String digito = mesDigito(mes);

                    queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontologyIE);
                    String query = tupla.get(0) + ":" + tupla.get(4) + "(?v1)^time:Instant(?v2)^time:hasDay(?v2,\"" + dia + "\")^time:hasMonth(?v2,\"" + digito + "\")^" + tupla.get(0) + ":" + tupla.get(2) + "(?v1,?v2)^" + tupla.get(0) + ":" + tupla.get(5) + "(?v1,?r1)->sqwrl:select(?r1)";

                    System.out.println("Consulta: " + query);
                    queryEngine.createSQWRLQuery("Q1", query);
                    SQWRLResult result = queryEngine.runSQWRLQuery("Q1");

                    while (result.next()) {

                        SQWRLLiteralResultValue nameValue = result.getLiteral("r1");
                        resp = nameValue.getString();
                        //respuesta = resp;
                    }

                    if (tupla.get(5).equals("hasEventName")) {
                        respuesta = "El nombre del evento es " + resp;
                        respuesta = respuesta.toUpperCase();
                    } else {
                        respuesta = "el " + tupla.get(1) + " es " + resp;
                        respuesta = respuesta.toUpperCase();
                    }

                }
            }
//////////////////////////////////////Preguntas compuestas
            //Tipo evento-persona
            if (tupla.get(0).equals("event-person")) {
                queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontologyIE);
                String query;
                if (tupla.get(2).equals("hasTemporalEntity")) {
                    String fecha = tupla.get(1).trim();
                    char[] cadena = fecha.toCharArray();
                    String dia = "", mes = "";

                    for (int i = 0; i < cadena.length; i++) {
                        if (Character.isDigit(cadena[i])) {
                            dia += cadena[i];
                        }

                    }
                    for (int i = 0; i < cadena.length; i++) {
                        if (Character.isLetter(cadena[i])) {
                            mes += cadena[i];
                        }

                    }
                    String digito = mesDigito(mes);

                    query = "intelligentenviroment:Event(?v1)^time:Instant(?v2)^time:hasDay(?v2,\"" + dia + "\")^time:hasMonth(?v2,\"" + digito + "\")^intelligentenviroment:" + tupla.get(2) + "(?v1,?v2)^person:Person(?v3)^intelligentenviroment:" + tupla.get(5) + "(?v1,?v3)^person:hasName(?v3,?r1)->sqwrl:select(?r1)";

                } else {
                    query= construccion(tupla);

                }
                    System.out.println();
                    System.out.println("Consulta: " + query);
                    queryEngine.createSQWRLQuery("Q1", query);
                    SQWRLResult result = queryEngine.runSQWRLQuery("Q1");

                    while (result.next()) {

                        SQWRLLiteralResultValue nameValue = result.getLiteral("r1");
                        resp = resp+","+nameValue.getString();
                    }
                    
                    if(tupla.get(2).equals("hasTemporalEntity")){
                        respuesta="En el evento del "+tupla.get(1)+" participa "+resp;
                        respuesta=respuesta.toUpperCase();
                    }else{
                        respuesta="En "+tupla.get(1)+" participa "+resp;
                        respuesta=respuesta.toUpperCase();
                    }
            }

            //Tipo evento-espacio fisico
            if (tupla.get(0).equals("event-physicalspace")) {
                queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontologyIE);
                String query;
                if (tupla.get(2).equals("hasTemporalEntity")) {
                    String fecha = tupla.get(1).trim();
                    char[] cadena = fecha.toCharArray();
                    String dia = "", mes = "";

                    for (int i = 0; i < cadena.length; i++) {
                        if (Character.isDigit(cadena[i])) {
                            dia += cadena[i];
                        }

                    }
                    for (int i = 0; i < cadena.length; i++) {
                        if (Character.isLetter(cadena[i])) {
                            mes += cadena[i];
                        }

                    }
                    String digito = mesDigito(mes);

                    query = "intelligentenviroment:Event(?v1)^time:Instant(?v2)^time:hasDay(?v2,\"" + dia + "\")^time:hasMonth(?v2,\"" + digito + "\")^intelligentenviroment:" + tupla.get(2) + "(?v1,?v2)^physicalspace:PhysicalSpace(?v3)^intelligentenviroment:" + tupla.get(5) + "(?v1,?v3)^physicalspace:hasNamePhysicalSpace(?v3,?r1)->sqwrl:select(?r1)";

                } else {
                    query= construccion(tupla);

                }
                    System.out.println();
                    System.out.println("Consulta: " + query);
                    queryEngine.createSQWRLQuery("Q1", query);
                    SQWRLResult result = queryEngine.runSQWRLQuery("Q1");

                    while (result.next()) {

                        SQWRLLiteralResultValue nameValue = result.getLiteral("r1");
                        resp = nameValue.getString();
                    }
                    
                    if(tupla.get(2).equals("hasTemporalEntity")){
                        respuesta="El evento del "+tupla.get(1)+" se realizara en "+resp;
                        respuesta=respuesta.toUpperCase();
                    }else{
                        respuesta="El "+tupla.get(1)+" se realizara en "+resp;
                        respuesta=respuesta.toUpperCase();
                    }
            }

            //Tipo espacio fisico-persona
            if (tupla.get(0).equals("physicalspace-person")) {
                queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontologyIE);
                String query = construccion(tupla);
                System.out.println();
                System.out.println("Consulta: " + query);
                queryEngine.createSQWRLQuery("Q1", query);
                SQWRLResult result = queryEngine.runSQWRLQuery("Q1");

                while (result.next()) {

                    SQWRLLiteralResultValue nameValue = result.getLiteral("r1");
                    resp = nameValue.getString();
                }
                if (tupla.get(2).equals("hasNamePhysicalSpace")) {
                    respuesta = "EL " + tupla.get(1) + " esta asignado a " + resp;
                    respuesta = respuesta.toUpperCase();
                } else {
                    respuesta = tupla.get(1) + " se encuentra en " + resp;
                    respuesta = respuesta.toUpperCase();
                }

            }

        } catch (OWLOntologyCreationException | SQWRLException ex) {
            Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
}
