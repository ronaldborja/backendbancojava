/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "empleados", catalog = "sistemabancario", schema = "")
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByDni", query = "SELECT e FROM Empleado e WHERE e.dni = :dni"),
    @NamedQuery(name = "Empleado.findByNombre", query = "SELECT e FROM Empleado e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Empleado.findByDireccion", query = "SELECT e FROM Empleado e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Empleado.findByTelefono", query = "SELECT e FROM Empleado e WHERE e.telefono = :telefono"),
    @NamedQuery(name = "Empleado.findByFechanacimiento", query = "SELECT e FROM Empleado e WHERE e.fechanacimiento = :fechanacimiento"),
    @NamedQuery(name = "Empleado.findBySexo", query = "SELECT e FROM Empleado e WHERE e.sexo = :sexo")})
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "DNI", nullable = false)
    private Integer dni;
    @Basic(optional = false)
    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Direccion", nullable = false, length = 255)
    private String direccion;
    @Basic(optional = false)
    @Column(name = "Telefono", nullable = false, length = 255)
    private String telefono;
    @Basic(optional = false)
    @Column(name = "Fecha_nacimiento", nullable = false, length = 255)
    private String fechanacimiento;
    @Basic(optional = false)
    @Column(name = "Sexo", nullable = false, length = 20)
    private String sexo;
    @JoinColumn(name = "Sucursal_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Sucursal sucursalid;

    public Empleado() {
    }

    public Empleado(Integer dni) {
        this.dni = dni;
    }

    public Empleado(Integer dni, String nombre, String direccion, String telefono, String fechanacimiento, String sexo) {
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechanacimiento = fechanacimiento;
        this.sexo = sexo;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Sucursal getSucursalid() {
        return sucursalid;
    }

    public void setSucursalid(Sucursal sucursalid) {
        this.sucursalid = sucursalid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dni != null ? dni.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.dni == null && other.dni != null) || (this.dni != null && !this.dni.equals(other.dni))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Empleado[ dni=" + dni + " ]";
    }
    
}
