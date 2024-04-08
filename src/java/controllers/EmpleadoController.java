/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Empleado;
import repository.EmpleadoCrud;
import repository.SucursalCrud;
import repository.exceptions.NonexistentEntityException;
import services.EmpleadoService;

/**
 *
 * @author usuario
 */
@WebServlet(name = "EmpleadoController", urlPatterns = {"/cempleado"})
public class EmpleadoController extends HttpServlet {
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
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion"); //Retorna el parametro de la petición
        
        switch(accion) {
            case "Guardar":
                guardar(request, response);
                break;

            case "Buscar":
                buscar(request, response); 
                break;
                
            case "Editar":
                editar(request, response);
                break; 
                
            case "Eliminar":
                eliminar(request, response);
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
        ejecutarAccion(request, response);
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
        ejecutarAccion(request, response);
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
        Integer DNI = Integer.valueOf(request.getParameter("dni")); 
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("dir");
        String telefono = request.getParameter("cel");
        String fecha_nacimiento = request.getParameter("fn");
        String sexo = request.getParameter("sex");
        String sucursal_id = request.getParameter("suc_id");
        
        //2. Se crea el objeto 
        Empleado e = new Empleado();
        e.setDni(DNI);
        e.setNombre(nombre);
        e.setDireccion(direccion);
        e.setTelefono(telefono);
        e.setFechanacimiento(fecha_nacimiento);
        e.setSexo(sexo);
       
        
        EntityManagerFactory conBD = Persistence.createEntityManagerFactory("sistemabancarioPU");
        SucursalCrud sucursalCrud = new SucursalCrud(conBD); //Objeto para accede a métodos CRUD 
        e.setSucursalid(sucursalCrud.findSucursal(Integer.valueOf(sucursal_id)));
        
        //Intentar guardar el objeto en la bd -> Servicio 
        EmpleadoService empleadoService = EmpleadoService.getInstanciaUnica();
        
        try { 
            int total = empleadoService.guardarEmpleado(e);
            response.sendRedirect("view/empleados/guardar.jsp?msg=Empleado guardado, Total: "+total);

        } catch (Exception error) {
            error.printStackTrace();
            response.sendRedirect("view/empleados/guardar.jsp?msg="+error.getMessage());
        }
    }

    private void buscar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Recuperamos el codigo de la sucursal: 
        Integer dni = Integer.valueOf(request.getParameter("dni"));
        EmpleadoService empleadoService = EmpleadoService.getInstanciaUnica();
    
        try { 
            Empleado e = empleadoService.buscarEmpleado(dni);
            
            //Enviamos la sucursal por la sesión
            HttpSession sesion = request.getSession();
            sesion.setAttribute("empleado.buscar", e);
            request.getRequestDispatcher("/view/empleados/buscar.jsp").forward(request, response);

        } catch (Exception error) {
            error.printStackTrace();
            response.sendRedirect("view/empleados/buscar.jsp?msg="+error.getMessage());
        } 
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer dni = Integer.valueOf(request.getParameter("dni"));
        EmpleadoService empleadoService = EmpleadoService.getInstanciaUnica();
        
        try {
            int total = empleadoService.eliminarSucursal(dni);
            response.sendRedirect("view/sucursales/buscar.jsp?msg=Sucursal eliminada, Total: "+total);
            //Enviamos la sucursal por la sesión
            
        } catch (Exception error) {
            error.printStackTrace();
            response.sendRedirect("view/sucursales/buscar.jsp?msg="+error.getMessage());
        }
    }
    private void listar_todo(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws IOException {
       Empleado emp = (Empleado) request.getSession().getAttribute("empleado.buscar"); 
       
       if (emp == null) {
            response.sendRedirect("view/empleados/buscar.jsp?msg=Empleado no encontrado");
       }
       
       else {
            String nombre = request.getParameter("nombre");
            String direccion = request.getParameter("dir");
            String telefono = request.getParameter("cel");
            String fecha_nacimiento = request.getParameter("fn");
            String sexo = request.getParameter("sex");
            String sucursal_id = request.getParameter("suc_id");

            emp.setNombre(nombre);
            emp.setDireccion(direccion);
            emp.setTelefono(telefono);
            emp.setFechanacimiento(fecha_nacimiento);
            emp.setSexo(sexo);


            EntityManagerFactory conBD = Persistence.createEntityManagerFactory("sistemabancarioPU");
            SucursalCrud sucursalCrud = new SucursalCrud(conBD); //Objeto para accede a métodos CRUD 
            emp.setSucursalid(sucursalCrud.findSucursal(Integer.valueOf(sucursal_id)));
            
           
           try {
               EmpleadoService empleadoService = EmpleadoService.getInstanciaUnica();
               empleadoService.editarEmpleado(emp); 
               HttpSession sesion = request.getSession();
               sesion.setAttribute("empleado.buscar", emp);
               response.sendRedirect("view/empleados/buscar.jsp?msg=Empleado editado");
           }
           catch (NonexistentEntityException ex){
               response.sendRedirect("view/empleados/buscar.jsp?msg=Empleado no encontrada");
           }
           catch (Exception ex) {
               response.sendRedirect("view/empleados/buscar.jsp?msg=Error al editar el empleado");
           }
       }
    }


}
