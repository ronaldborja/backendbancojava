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
@Table(name = "clientes_naturales", catalog = "sistemabancario", schema = "")
@NamedQueries({
    @NamedQuery(name = "ClienteNatural.findAll", query = "SELECT c FROM ClienteNatural c"),
    @NamedQuery(name = "ClienteNatural.findById", query = "SELECT c FROM ClienteNatural c WHERE c.id = :id"),
    @NamedQuery(name = "ClienteNatural.findByFechaNacimiento", query = "SELECT c FROM ClienteNatural c WHERE c.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "ClienteNatural.findBySexo", query = "SELECT c FROM ClienteNatural c WHERE c.sexo = :sexo"),
    @NamedQuery(name = "ClienteNatural.findByNombre", query = "SELECT c FROM ClienteNatural c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ClienteNatural.findByDireccion", query = "SELECT c FROM ClienteNatural c WHERE c.direccion = :direccion")})
public class ClienteNatural implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha_nacimiento", nullable = false, length = 255)
    private String fechaNacimiento;
    @Basic(optional = false)
    @Column(name = "sexo", nullable = false, length = 256)
    private String sexo;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "direccion", nullable = false, length = 255)
    private String direccion;
    @OneToMany(mappedBy = "clienteNaturalId")
    private List<Cliente> clienteList;

    public ClienteNatural() {
    }

    public ClienteNatural(Integer id) {
        this.id = id;
    }

    public ClienteNatural(Integer id, String fechaNacimiento, String sexo, String nombre, String direccion) {
        this.id = id;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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
        if (!(object instanceof ClienteNatural)) {
            return false;
        }
        ClienteNatural other = (ClienteNatural) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.ClienteNatural[ id=" + id + " ]";
    }
    
}
