/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.Empleado;
import models.Sucursal;
import repository.EmpleadoCrud;
import repository.SucursalCrud;
import repository.exceptions.IllegalOrphanException;
import repository.exceptions.NonexistentEntityException;

/**
 *
 * @author usuario
 */
public class EmpleadoService {
    //Crear una variable estatica del mismo tipo 
    private static EmpleadoService instanciaUnica; 
    
    //Constructor privado por defecto -> No permite crear multiples objetos
    private EmpleadoService() {
 
    }
    
 
    //Para crear el objeto -> Actua como constructor
    public static EmpleadoService getInstanciaUnica() {
        if (instanciaUnica == null) {
            instanciaUnica = new EmpleadoService();
        }
        
        //Si la instancia existe, retorna la misma. 
        return instanciaUnica; 
    }
    
        public int guardarEmpleado(Empleado e) throws Exception {
        if (e == null) {
            throw new Exception("La sucursal no puede ser nulo");
        }
        //Intentar guardar en la bd 
        try {
            //Si el usuario no es nulo -> Usar la capa repo: 
            // 1. Buscar si existe el usuario (Validación) -> Esta excepción ya la crea JPA

            //Var EntityManagerFactory -> ¿Qué es? 
            EntityManagerFactory conBD = Persistence.createEntityManagerFactory("sistemabancarioPU");
            EmpleadoCrud empleadoCrud = new EmpleadoCrud(conBD); //Objeto para accede a métodos CRUD 
            empleadoCrud.create(e); //INSERT INTO Sucursales VALUES(attrs); 
            return empleadoCrud.getEmpleadoCount(); //SELECT COUNT(*) FROM Sucursales; 
        } catch(Exception error) {
            throw new Exception("Ocurrió un error al guardar el empleado");
        }
    }
       
        
    public Empleado buscarEmpleado(int id) throws Exception {
        try { 
            EntityManagerFactory conBD = Persistence.createEntityManagerFactory("sistemabancarioPU");
            EmpleadoCrud empleadoCrud = new EmpleadoCrud(conBD); //Objeto para accede a métodos CRUD  
            Empleado e = empleadoCrud.findEmpleado(id);
            
            if (e == null) {
                throw new Exception("Empleado no encontrado");
            }
            return e; 
        }
        
        catch(Exception error) {
            String msg = "Error al encontrar el empleado: " + error.getMessage();
            throw new Exception(msg, error); // Propagar la excepción original como causa
        }
    }
    
    public int eliminarSucursal(int id) throws Exception {
        try { 
            EntityManagerFactory conBD = Persistence.createEntityManagerFactory("sistemabancarioPU");
            EmpleadoCrud empleadoCrud = new EmpleadoCrud(conBD); //Objeto para accede a métodos CRUD  
            empleadoCrud.destroy(id);
            return empleadoCrud.getEmpleadoCount();   
        }
        
        catch(NonexistentEntityException error) {
            String msg = "Error al eliminar la sucursal: " + error.getMessage();
            throw new Exception(msg, error); // Propagar la excepción original como causa
        }
    }
    
        public void editarEmpleado(Empleado empleado) throws Exception {
        try { 
            EntityManagerFactory conBD = Persistence.createEntityManagerFactory("sistemabancarioPU");
            EmpleadoCrud empleadoCrud = new EmpleadoCrud(conBD); //Objeto para accede a métodos CRUD  
            empleadoCrud.edit(empleado);
            
        }
        
        catch(NonexistentEntityException error) {
            String msg = "Empleado no encontrada: " + error.getMessage();
            throw new Exception(msg, error); // Propagar la excepción original como causa
        }
        
        catch(Exception error) {
            String msg = "Error al editar el empleado: " + error.getMessage();
            throw new Exception(msg, error); // Propagar la excepción original como causa
        }
    }
}
