/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.uam;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;


@WebServlet(name = "OntologiaServlet", urlPatterns = {"/OntologiaServlet"})
public class OntologiaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SWRLParseException, SQWRLException {
        Controlador c=new Controlador();
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String pregunta = request.getParameter("pregunta").trim();
            
            System.out.println("Soy nuevo y esto recibo: " + pregunta);

            
            response.setContentType("text/plain;charset=UTF-8");
            //aquì va la respuesta de la consulta
            String respuesta = c.proceso(pregunta);
            System.out.println("Respuesta de servlet :"+respuesta);
            if(respuesta.equals("")){
                
                out.print("Por el momento no puedo responder esa consulta, aun estoy afinando detalles");
            }else{
                
            
            out.print("La respuesta a tu consulta es: " +respuesta);

            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SWRLParseException ex) {
            Logger.getLogger(OntologiaServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQWRLException ex) {
            Logger.getLogger(OntologiaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SWRLParseException ex) {
            Logger.getLogger(OntologiaServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQWRLException ex) {
            Logger.getLogger(OntologiaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
