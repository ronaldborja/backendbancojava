/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Sucursal;
import org.apache.catalina.Session;
import repository.exceptions.NonexistentEntityException;
import services.SucursalService;

/**
 *
 * @author usuario
 */
@WebServlet(name = "SucursalController", urlPatterns = {"/csucursal"})
public class SucursalController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        
        String accion = request.getParameter("accion"); //Retorna el parametro de la petición
        
        switch(accion) {
            case "Guardar":
                guardar(request, response);
                break;
                
            case "Buscar": 
                buscar(request, response);
                break; 
                
            case "Eliminar":
                eliminar(request, response);
                break;
                
            case "Editar":
                editar(request, response);
                break;
                
            case "todo":
                listar_todo(request, response); 
                break;
             
            default:
                response.sendRedirect("view/error.jsp?msg=Accion incorrecta");
                break;
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
            ejecutarAccion(request, response);
        } catch (Exception e) {
            e.printStackTrace();
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
            ejecutarAccion(request, response);
        } catch (Exception e) {
            e.printStackTrace();
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

    private void guardar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1. Recuperamos los campos del form -> Capa vista:  
        String direccion = request.getParameter("dir"); 
        String codigoPostal = request.getParameter("cod");
        
        //2. Se crea el objeto 
        Sucursal suc = new Sucursal();
        suc.setDireccion(direccion);
        suc.setCodigoPostal(codigoPostal);
        
        //3. Asignar los valores al objeto: 

        //Intentar guardar el objeto en la bd -> Servicio 
        SucursalService sucursalService = SucursalService.getInstanciaUnica();
        
        try { 
            int total = sucursalService.guardarSucursal(suc);
            response.sendRedirect("view/sucursales/guardar.jsp?msg=Sucursal guardada, Total: "+total);

        } catch (Exception error) {
            error.printStackTrace();
            response.sendRedirect("view/sucursales/guardar.jsp?msg="+error.getMessage());
        }
    }

    private void buscar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Recuperamos el codigo de la sucursal: 
        Integer codigoSucursal = Integer.valueOf(request.getParameter("id"));
        SucursalService sucursalService = SucursalService.getInstanciaUnica();
    
        try { 
            Sucursal suc = sucursalService.buscarSucursal(codigoSucursal);
            
            //Enviamos la sucursal por la sesión
            HttpSession sesion = request.getSession();
            sesion.setAttribute("sucursal.buscar", suc);
            request.getRequestDispatcher("/view/sucursales/buscar.jsp").forward(request, response);

        } catch (Exception error) {
            error.printStackTrace();
            response.sendRedirect("view/sucursales/buscar.jsp?msg="+error.getMessage());
        } 
    }
    
   private void editar(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
       Sucursal suc = (Sucursal) request.getSession().getAttribute("sucursal.buscar"); 
       
       if (suc == null) {
            response.sendRedirect("view/sucursales/buscar.jsp?msg=Sucursal no encontrado");
       }
       
       else {
           String direccion = request.getParameter("dir");
           String codigoPostal = request.getParameter("cod");

           //2. Se crea el objeto 
           suc.setDireccion(direccion);
           suc.setCodigoPostal(codigoPostal);
           
           try {
               SucursalService sucursalService = SucursalService.getInstanciaUnica();
               sucursalService.editarSucursal(suc); 
               HttpSession sesion = request.getSession();
               sesion.setAttribute("usuario.buscar", suc);
               response.sendRedirect("view/sucursales/buscar.jsp?msg=Sucursal editada");
           }
           catch (NonexistentEntityException ex){
               response.sendRedirect("view/sucursales/buscar.jsp?msg=Sucursal no encontrada");
           }
           catch (Exception ex) {
               response.sendRedirect("view/sucursales/buscar.jsp?msg=Error al editar");
           }
       }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer codigoSucursal = Integer.valueOf(request.getParameter("id"));
        SucursalService sucursalService = SucursalService.getInstanciaUnica();
        
        try {
            int total = sucursalService.eliminarSucursal(codigoSucursal);
            response.sendRedirect("view/empleados/buscar.jsp?msg=Sucursal eliminada, Total: "+total);
            //Enviamos la sucursal por la sesión
            
        } catch (Exception error) {
            error.printStackTrace();
            response.sendRedirect("view/empleados/buscar.jsp?msg="+error.getMessage());
        }
    }

    private void listar_todo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SucursalService sucursalService = SucursalService.getInstanciaUnica();
        try { 
            List<Sucursal> lista = sucursalService.obtenerTodo();
            HttpSession sesion = request.getSession();
            sesion.setAttribute("sucursal.all", lista);
            request.getRequestDispatcher("/view/sucursales/listar_todo.jsp").forward(request, response);
        } catch (Exception error) {
            error.printStackTrace();
            response.sendRedirect("view/sucursales/listar_todo.jsp?msg="+error.getMessage());
        }
    }

}
