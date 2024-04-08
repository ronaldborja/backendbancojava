/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.Sucursal;
import repository.SucursalCrud;
import repository.exceptions.IllegalOrphanException;
import repository.exceptions.NonexistentEntityException;

public class SucursalService {
    
    //Crear una variable estatica del mismo tipo 
    private static SucursalService instanciaUnica; 
    
    //Constructor privado por defecto -> No permite crear multiples objetos
    private SucursalService() {
 
    }
    

    //Para crear el objeto -> Actua como constructor
    public static SucursalService getInstanciaUnica() {
        if (instanciaUnica == null) {
            instanciaUnica = new SucursalService();
        }
        
        //Si la instancia existe, retorna la misma. 
        return instanciaUnica; 
    }
    
    /**
     * Guardar ususarios en la bd: 
     *  * Si existe previamente, lanza una Excepción 
     * @param suc corresponde a la sucursal que se desea crear en la bd
     * @return el conteo de sucursales despues de se guardada 
     * @throws Exception la información de la excepción
     */
    public int guardarSucursal(Sucursal suc) throws Exception {
        if (suc == null) {
            throw new Exception("La sucursal no puede ser nulo");
        }
        //Intentar guardar en la bd 
        try {
            //Si el usuario no es nulo -> Usar la capa repo: 
            // 1. Buscar si existe el usuario (Validación) -> Esta excepción ya la crea JPA

            //Var EntityManagerFactory -> ¿Qué es? 
            EntityManagerFactory conBD = Persistence.createEntityManagerFactory("sistemabancarioPU");
            SucursalCrud sucursalCrud = new SucursalCrud(conBD); //Objeto para accede a métodos CRUD 
            sucursalCrud.create(suc); //INSERT INTO Sucursales VALUES(attrs); 
            return sucursalCrud.getSucursalCount(); //SELECT COUNT(*) FROM Sucursales; 
        } catch(Exception error) {
            throw new Exception("Ocurrió un error al guardar la sucursal");
        }
    }
    
    public Sucursal buscarSucursal(int id) throws Exception {
        try { 
            EntityManagerFactory conBD = Persistence.createEntityManagerFactory("sistemabancarioPU");
            SucursalCrud sucursalCrud = new SucursalCrud(conBD); //Objeto para accede a métodos CRUD  
            Sucursal suc = sucursalCrud.findSucursal(id);
            
            if (suc == null) {
                throw new Exception("Sucursal no encontrada");
            }
            return suc; 
        }
        
        catch(Exception error) {
            String msg = "Error al encontrar la sucursal: " + error.getMessage();
            throw new Exception(msg, error); // Propagar la excepción original como causa
        }
    }
    
    public void editarSucursal(Sucursal sucursal) throws Exception {
        try { 
            EntityManagerFactory conBD = Persistence.createEntityManagerFactory("sistemabancarioPU");
            SucursalCrud sucursalCrud = new SucursalCrud(conBD); //Objeto para accede a métodos CRUD  
            sucursalCrud.edit(sucursal);
            
        }
        
        catch(NonexistentEntityException error) {
            String msg = "Sucursal no encontrada: " + error.getMessage();
            throw new Exception(msg, error); // Propagar la excepción original como causa
        }
        
        catch(Exception error) {
            String msg = "Error al editar la sucursal: " + error.getMessage();
            throw new Exception(msg, error); // Propagar la excepción original como causa
        }
    }
    
    public int eliminarSucursal(int id) throws Exception {
        try { 
            EntityManagerFactory conBD = Persistence.createEntityManagerFactory("sistemabancarioPU");
            SucursalCrud sucursalCrud = new SucursalCrud(conBD); //Objeto para accede a métodos CRUD  
            sucursalCrud.destroy(id);
            return sucursalCrud.getSucursalCount();   
        }
        
        catch(IllegalOrphanException | NonexistentEntityException error) {
            String msg = "Error al eliminar la sucursal: " + error.getMessage();
            throw new Exception(msg, error); // Propagar la excepción original como causa
        }
    }
    
    public List<Sucursal> obtenerTodo() throws Exception {
        try {
            EntityManagerFactory conBD = Persistence.createEntityManagerFactory("sistemabancarioPU");
            SucursalCrud sucursalCrud = new SucursalCrud(conBD); //Objeto para accede a métodos CRUD 

            //Retornar las entidades tipo Sucursal: 
            //SELECT * FROM Sucursales; 
            List<Sucursal> listaSucursales = sucursalCrud.findSucursalEntities();
        
            if (listaSucursales == null || listaSucursales.isEmpty()) {
                throw new Exception("No hay sucursales en la bd");
            } 
        return listaSucursales;    
        } catch (Exception error) {
            String msg = ""; 
            
            if (error.getMessage().contains("No hay Sucursal")) {
                msg = error.getMessage(); 
            }
            else {
               msg = "Error al listar todas las sucursales";  
            }
            throw new Exception(msg);
        }
        
    }
}
