/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import SQL.DiscountEntity;
import SQL.ExtendedDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplejdbc.DAOException;
import simplejdbc.DataSourceFactory;

/**
 *
 * @author Axel
 */
@WebServlet(name = "DiscountCodeServlet", urlPatterns = {"/DiscountCodeServlet"})
public class DiscountCodeServlet extends HttpServlet {

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
            throws ServletException, IOException, DAOException {
        ExtendedDAO dao = new ExtendedDAO(DataSourceFactory.getDataSource());
        String code = request.getParameter("code");
        Double rate;
        try {
            rate = Double.parseDouble(request.getParameter("rate"));
        } catch (Exception e) {
            rate = null;
        }
        String action = "";
        action += request.getParameter("action");
        String error = "";

        Properties resultat = new Properties();

        try {
            System.out.println(code + rate + action);
            if (action.equals("ADD")){
               error = this.addDiscount(dao,code,rate);
            }
            if (action.equals("DELETE")){
                error = this.deleteDiscount(dao,code);
            }
            resultat.put("records", dao.existingDiscountCode());
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultat.put("records", Collections.EMPTY_LIST);
            resultat.put("message", ex.getMessage());
        }
        try (PrintWriter out = response.getWriter()) {
            // On spécifie que la servlet va générer du JSON
            response.setContentType("application/json;charset=UTF-8");
            // Générer du JSON
            // Gson gson = new Gson();
            // setPrettyPrinting pour que le JSON généré soit plus lisible
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String gsonData = gson.toJson(resultat);
            out.println(gsonData);
        }
    }

    /*
    Essaie de supprimer la réduction, renvoie un string vide si l'opération est un succès, 
    renvoie le message d'erreur sinon.
     */
    private String deleteDiscount(ExtendedDAO dao, String code) {
        try {
            dao.deleteDiscount(code);
            return "";
        } catch (Exception e) {
            return "Impossible de supprimer la réduction, elle est utilisée ailleurs.";
        }
    }

    /*
    Essaie d'ajouter la réduction, renvoie un string vide si l'opération est un succès, 
    renvoie le message d'erreur sinon.
     */
    private String addDiscount(ExtendedDAO dao, String code, Double rate) {
        try {
            if (code.equals("") || rate == null) {
                return "Le code ou le taux n'as pas été renseigné.";
            }
            dao.insertDiscount(new DiscountEntity(code, rate));
            return "";
        } catch (Exception e) {
            return "Impossible d'ajouter la réduction, elle existe déjà.";
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
        } catch (DAOException ex) {
            Logger.getLogger(DiscountCodeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (DAOException ex) {
            Logger.getLogger(DiscountCodeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
