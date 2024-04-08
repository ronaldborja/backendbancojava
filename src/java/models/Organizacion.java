/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "organizaciones", catalog = "sistemabancario", schema = "")
@NamedQueries({
    @NamedQuery(name = "Organizacion.findAll", query = "SELECT o FROM Organizacion o"),
    @NamedQuery(name = "Organizacion.findById", query = "SELECT o FROM Organizacion o WHERE o.id = :id"),
    @NamedQuery(name = "Organizacion.findByTipoOrganizacion", query = "SELECT o FROM Organizacion o WHERE o.tipoOrganizacion = :tipoOrganizacion"),
    @NamedQuery(name = "Organizacion.findByRepresentante", query = "SELECT o FROM Organizacion o WHERE o.representante = :representante"),
    @NamedQuery(name = "Organizacion.findByNumEmpleados", query = "SELECT o FROM Organizacion o WHERE o.numEmpleados = :numEmpleados"),
    @NamedQuery(name = "Organizacion.findByNombre", query = "SELECT o FROM Organizacion o WHERE o.nombre = :nombre"),
    @NamedQuery(name = "Organizacion.findByDireccion", query = "SELECT o FROM Organizacion o WHERE o.direccion = :direccion")})
public class Organizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "tipo_organizacion", nullable = false, length = 256)
    private String tipoOrganizacion;
    @Basic(optional = false)
    @Column(name = "representante", nullable = false, length = 256)
    private String representante;
    @Basic(optional = false)
    @Column(name = "num_empleados", nullable = false)
    private int numEmpleados;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "direccion", nullable = false, length = 255)
    private String direccion;
    @OneToMany(mappedBy = "organizacionId")
    private List<Cliente> clienteList;

    public Organizacion() {
    }

    public Organizacion(Integer id) {
        this.id = id;
    }

    public Organizacion(Integer id, String tipoOrganizacion, String representante, int numEmpleados, String nombre, String direccion) {
        this.id = id;
        this.tipoOrganizacion = tipoOrganizacion;
        this.representante = representante;
        this.numEmpleados = numEmpleados;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoOrganizacion() {
        return tipoOrganizacion;
    }

    public void setTipoOrganizacion(String tipoOrganizacion) {
        this.tipoOrganizacion = tipoOrganizacion;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public int getNumEmpleados() {
        return numEmpleados;
    }

    public void setNumEmpleados(int numEmpleados) {
        this.numEmpleados = numEmpleados;
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

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Organizacion)) {
            return false;
        }
        Organizacion other = (Organizacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Organizacion[ id=" + id + " ]";
    }
    
}
