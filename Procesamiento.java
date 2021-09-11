/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PLN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gabrielagarcia
 */
public class Procesamiento {

    Limpieza l = new Limpieza();

    //arreglo de palabras clave dentro de la pregunta
    public ArrayList<String> palabrasClave(String oracion) {

        ArrayList<String> claves = new ArrayList<String>();
        claves = l.palabrasArchivo("Palabras/clave.txt");
        ArrayList<String> clave = new ArrayList<String>();

        String c;
        String[] a;

        for (int j = 0; j < claves.size(); j++) {

            c = claves.get(j);
            a = c.split(":");

            for (int i = 0; i < a.length; i++) {
                //se cambio equals
                if (oracion.contains(a[i])) {
                    clave.add(a[i]);
                    clave.add(a[0]);
                }
            }

        }

        return clave;

    }

    //tipo de pregunta
    public String tipo(ArrayList<String> clave) {

        String tipo;
        if (clave.contains("Evento") && clave.contains("Persona") && clave.contains("Espacio Fisico")) {
            tipo = "Evento-Persona-Espacio Fisico";
        } else if (clave.contains("Evento") && clave.contains("Persona")) {
            tipo = "Evento-Persona";
        } else if (clave.contains("Evento") && clave.contains("Espacio Fisico")) {
            tipo = "Evento-Espacio Fisico";
        } else if (clave.contains("Espacio Fisico") && clave.contains("Persona")) {
            tipo = "Espacio Fisico-Persona";
        } else if (clave.contains("Persona")) {
            tipo = "Persona";
        } else if (clave.contains("Evento")) {
            tipo = "Evento";
        } else if (clave.contains("Espacio Fisico")) {
            tipo = "Espacio Fisico";
        } else {
            tipo = "No se encontro el tipo";
        }

        return tipo;
    }
    public String tipoSujeto;

    //sujeto de pregunta Persona
    public String sujetoPersona(String pregunta, ArrayList<String> nombres) {

        String su = "";
        String[] p;
        String p1;

        String palabra, nombre;
        //lugar de trabajo
        Pattern lugarT = Pattern.compile("^[A-Z a-z][0-9].*");

        //matricula o numero economico
        Pattern numero = Pattern.compile("[0-9].*");

        //email
        //Pattern email = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
        Matcher mat;
        p = pregunta.split(" ");

        for (int i = 0; i < p.length; i++) {
            p1 = p[i];
            mat = lugarT.matcher(p1);
            if (mat.matches()) {
                //aqui
                su = "cubiculo " + p1;
                tipoSujeto = "cubiculo";
            } else {
                mat = numero.matcher(p1);
                if (mat.matches()) {
                    su = p1;
                    if (su.length() > 8) {
                        tipoSujeto = "matricula";
                    } else {
                        tipoSujeto = "numero economico";
                    }
               } else{   
                    //mat=email.matcher(p1);
                    if (p1.contains("@")) {
                    su = p1;
                    tipoSujeto = "correo";
                    } else {

                        for (int j = 0; j < nombres.size(); j++) {
                            nombre = nombres.get(j);
                            //if (p1.contains(nombre)) {
                            if (p1.equals(nombre)) {
                                //su = su + " " + p1;
                                su += " " + p1;
                                System.out.println("Su: "+su);
                            }
                        }
                        tipoSujeto = "nombre";
                    }
                }
            }
        }
        return su;
    }

    //sujeto de pregunta Lugar
    public String sujetoLugar(String pregunta, ArrayList<String> lugares, ArrayList<String> claves) {

        String su = "";
        String[] l, p;
        String lugar, palabra;
        int bandera = 0;

        //lugar de trabajo
        Pattern lu = Pattern.compile("^[a-z]{1,2}[0-9].*");
        Pattern salon = Pattern.compile("^[b|d|e|f|k|l][0-9].*");
        Pattern cubiculo = Pattern.compile("^[h|p|w][0-9].*");
        Pattern a1 = Pattern.compile("^hp[0-9].*");
        Pattern a2 = Pattern.compile("^ho[0-9].*");
        Pattern la = Pattern.compile("^[a-z]{1,2}");

        //catalogo de lugares
        for (int i = 0; i < lugares.size(); i++) {
            lugar = lugares.get(i);
            l = lugar.split(":");
            for (int j = 0; j < l.length; j++) {
                if (pregunta.contains(l[j])) {
                    su = su + " " + l[j];
                    tipoSujeto = "nombre";
                    bandera++;
                }
            }
        }

        //si no esta en el catalogo se busca expresion regular
        if (bandera == 0) {
            p = pregunta.split(" ");
            for (int k = 0; k < p.length; k++) {
                Matcher m, n, o, r, g, f;
                palabra = p[k];
                m = lu.matcher(palabra);
                f = la.matcher(palabra);
                //letras y numeros
                if (m.matches() || f.matches()) {
                    su = palabra;
                    m = salon.matcher(su);
                    r = cubiculo.matcher(su);
                    n = a1.matcher(su);
                    o = a2.matcher(su);

                    if (claves.contains("salon") || m.matches() || claves.contains("cubiculo") || r.matches() || n.matches() || o.matches() || claves.contains("edificio")) {
                        tipoSujeto = "nombre";
                        //aqui
                        if (r.matches() || n.matches() || o.matches() || claves.contains("cubiculo")) {
                            su = "cubiculo " + palabra;
                        } else {
                            if (claves.contains("salon")) {
                                su = "salon " + palabra;
                            } else {
                                if (claves.contains("edificio")) {
                                    su = "edificio " + palabra;
                                }
                            }
                        }
                    } else {
                        tipoSujeto = "no se encontro el tipo de lugar";
                    }
                } else {
                    su = "no se encontro el tipo de lugar";
                    tipoSujeto = "no se encontro el tipo de lugar";
                }
            }
        }

        return su;
    }

    //sujeto de pregunta Evento
    public String sujetoEvento(String pregunta, ArrayList<String> claves) {

        String su = "";
        List<String> al = new ArrayList<String>();
        String[] p;
        int j = 0, k = 0, i = 0;

        p = pregunta.split(" ");
        j = claves.indexOf("Evento");

        su = claves.get(j - 1);

        al = Arrays.asList(p);
        k = al.indexOf(su) + 1;

        for (i = k; i < al.size(); i++) {

            su = su + " " + al.get(i);
        }
        if (su.contains("enero") || su.contains("febrero") || su.contains("marzo") || su.contains("abril")
                || su.contains("mayo") || su.contains("junio") || su.contains("julio") || su.contains("agosto") || su.contains("septiembre")
                || su.contains("octubre") || su.contains("noviembre") || su.contains("diciembre")) {
            tipoSujeto = "instante";
        } else {
            tipoSujeto = "nombre";
        }

        return su;
    }

    public String tipoSujeto(ArrayList<String> clave, String preguntaLimpia, String tipo, ArrayList<String> nombres, ArrayList<String> lugares) {
        String sujeto = "";

        if (clave.contains("Evento") && clave.contains("Persona")) {
            sujeto = sujetoEvento(preguntaLimpia, clave);
        } else {
            if (clave.contains("Evento") && clave.contains("Espacio Fisico")) {
                sujeto = sujetoEvento(preguntaLimpia, clave);
            } else {
                if ((clave.contains("Persona")) && (clave.contains("Espacio Fisico"))) {
                    sujeto = sujetoPersona(preguntaLimpia, nombres);
                } else {
                    if (tipo == "Persona") {
                        sujeto = sujetoPersona(preguntaLimpia, nombres);
                    } else {
                        if (tipo == "Espacio Fisico") {
                            sujeto = sujetoLugar(preguntaLimpia, lugares, clave);

                        } else {
                            if (tipo == "Evento") {
                                sujeto = sujetoEvento(preguntaLimpia, clave);
                            } else {
                                sujeto = "El tipo es diferente de Persona";
                            }

                        }

                    }
                }
            }
        }

        return sujeto;
    }

    //tipo de sujeto
    public String tipo() {
        String tipo;

        tipo = tipoSujeto;

        return tipo;
    }

    //clase de ontologia
    public String clase(ArrayList<String> clave, String tipo, String tipoSujeto, String pregunta, ArrayList<String> lugares, String su) {

        String c = "";
//        if (tipo.contains("Persona")) {
//            if (clave.contains("profesor")) {
//                c = "profesor";
//            } else {
//                if (clave.contains("alumno")) {
//                    c = "alumno";
//                } else {
//                    if (clave.contains("ayudante")) {
//                        c = "ayudante";
//                    } else {
//                        if (tipoSujeto == "matricula") {
//                            c = "alumno o ayudante";
//                        } else {
//                            if (tipoSujeto == "numero economico") {
//                                c = "profesor o ayudante";
//                            } else {
//                                if (tipoSujeto == "cubiculo") {
//                                    c = "profesor";
//                                } else {
//                                    if (tipoSujeto == "nombre") {
//                                        c = "profesor-ayudante-alumno";
//                                    } else {
//                                        if (tipoSujeto == "correo") {
//                                            c = "profesor";
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                    }
//                }
//            }
        
            if (tipo.contains("Persona")) {
            if (clave.contains("profesor")||tipoSujeto=="cubiculo") {
                c = "profesor";
            } else {
                if (clave.contains("alumno")||tipoSujeto=="matricula"||clave.contains("matricula")) {
                    c = "alumno";
                } else {
                    if (clave.contains("ayudante")||tipoSujeto=="matricula"||clave.contains("matricula")) {
                        c = "ayudante";
                    }  else {
                            if (tipoSujeto == "numero economico") {
                                c = "profesor o ayudante";
                            }  else {
                                    if (tipoSujeto == "nombre") {
                                        c = "profesor-ayudante-alumno";
                                    } else {
                                        if (tipoSujeto == "correo") {
                                            c = "profesor";
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            else {
                
                if (tipo.contains("Espacio Fisico")) {

                String[] l, p;
                String lugar, palabra;
                int bandera = 0;

                //lugar de trabajo
                Pattern lu = Pattern.compile("^[a-z]{1,2}[0-9].*");
                Pattern salon = Pattern.compile("^[b|d|e|f|k|l][0-9].*");
                Pattern cubiculo = Pattern.compile("^[h|p|w][0-9].*");
                Pattern a1 = Pattern.compile("^hp[0-9].*");
                Pattern a2 = Pattern.compile("^ho[0-9].*");
                Pattern la = Pattern.compile("^[a-z]{1,2}");

                //catalogo de lugares
                for (int i = 0; i < lugares.size(); i++) {
                    lugar = lugares.get(i);
                    l = lugar.split(":");
                    for (int j = 0; j < l.length; j++) {
                        if (pregunta.contains(l[j])) {
                            c = l[0];
                            bandera++;
                        }
                    }
                }

                //si no esta en el catalogo se busca expresion regular
                if (bandera == 0) {
                    p = pregunta.split(" ");
                    for (int k = 0; k < p.length; k++) {
                        Matcher m, n, o, f;
                        palabra = p[k];
                        m = lu.matcher(palabra);
                        f = la.matcher(palabra);
                        //letras y numeros
                        if (m.matches() || f.matches() || tipoSujeto == "nombre") {
                            su = palabra;
                            m = salon.matcher(su);
                            if (clave.contains("salon") || m.matches()) {
                                c = "salon";
                            } else {
                                m = cubiculo.matcher(su);
                                n = a1.matcher(su);
                                o = a2.matcher(su);
                                if (clave.contains("cubiculo") || m.matches() || n.matches() || o.matches()) {
                                    c = "cubiculo";
                                } else {
                                    if (clave.contains("edificio")) {
                                        c = "edificio";
                                    } else {
                                        if (tipo == "Evento-Espacio Fisico") {
                                            int j = 0;
                                            j = clave.indexOf("Evento");
                                            c = clave.get(j - 1);

                                        } else {
                                            c = "tipo de sujeto no localizado1";
                                        }
                                    }
                                }
                            }

                        } else {
                            if (tipo == "Evento-Espacio Fisico") {
                                if (su.contains("enero") || su.contains("febrero") || su.contains("marzo") || su.contains("abril")
                                        || su.contains("mayo") || su.contains("junio") || su.contains("julio") || su.contains("agosto") || su.contains("septiembre")
                                        || su.contains("octubre") || su.contains("noviembre") || su.contains("diciembre")) {
                                    c = "instante";
                                }
                            } else {
                                c = "tipo de sujeto no localizado";
                            }

                        }
                    }
                }
            } else {
                if (tipo.contains("Evento")) {
                    int j = 0;
                    j = clave.indexOf("Evento");
                    c = clave.get(j - 1);
                } else {
                    c = "clase no localizada";
                }
            }
        }

        return c;
    }

    //tupla
    public ArrayList<String> tupla(String tipo, String sujeto, String tipoSujeto, String clase, ArrayList<String> clave) {

        ArrayList<String> tupla = new ArrayList<String>();

        tupla.add(tipo);
        tupla.add(sujeto);
        tupla.add(tipoSujeto);
        tupla.add(clase);

        for (int i = 0; i < clave.size(); i++) {

            if (clave.get(i).contains("Persona") || clave.get(i).contains("Evento") || clave.get(i).contains("Espacio Fisico")) {

            } else {
                if (tupla.contains(clave.get(i))) {

                } else {
                    tupla.add(clave.get(i));
                }

            }

        }

        return tupla;
    }

    public ArrayList<String> extraccionFinal(String pregunta) {

        ArrayList<String> extraccion = new ArrayList<String>();

        String preguntaLimpia = "";

        //limpieza de pregunta
        preguntaLimpia = l.limpiezaPregunta(pregunta);
        System.out.println("Pregunta limpia: "+preguntaLimpia);
        
        //comparacion con palabras clave
        ArrayList<String> claves = new ArrayList<String>();
        claves = palabrasClave(preguntaLimpia);
        System.out.println(claves);

        //tipo de pregunta
        String tipo;
        tipo = tipo(claves);

        //tipoSujeto
        String tipoSujeto;
        ArrayList<String> nombres = new ArrayList<String>();
        nombres = l.palabrasArchivo("Palabras/nombres.txt");

        ArrayList<String> lugares = new ArrayList<String>();
        lugares = l.palabrasArchivo("Palabras/lugares.txt");

        tipoSujeto = tipoSujeto(claves, preguntaLimpia, tipo, nombres, lugares);

        //agregar tipo de sujeto
        String sujeto;
        sujeto = tipo();

        //clase de ontologia
        String claseOntologia;
        claseOntologia = clase(claves, tipo, sujeto, preguntaLimpia, lugares, tipoSujeto);

        //tupla de extraccion
        extraccion = tupla(tipo, tipoSujeto, sujeto, claseOntologia, claves);

        return extraccion;
    }

}
