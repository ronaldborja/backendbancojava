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
@Table(name = "cuentas", catalog = "sistemabancario", schema = "")
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c"),
    @NamedQuery(name = "Cuenta.findById", query = "SELECT c FROM Cuenta c WHERE c.id = :id"),
    @NamedQuery(name = "Cuenta.findByTipoCuenta", query = "SELECT c FROM Cuenta c WHERE c.tipoCuenta = :tipoCuenta"),
    @NamedQuery(name = "Cuenta.findBySaldoActual", query = "SELECT c FROM Cuenta c WHERE c.saldoActual = :saldoActual"),
    @NamedQuery(name = "Cuenta.findBySaldoMedio", query = "SELECT c FROM Cuenta c WHERE c.saldoMedio = :saldoMedio"),
    @NamedQuery(name = "Cuenta.findByNumeroCuenta", query = "SELECT c FROM Cuenta c WHERE c.numeroCuenta = :numeroCuenta"),
    @NamedQuery(name = "Cuenta.findByFechaApertura", query = "SELECT c FROM Cuenta c WHERE c.fechaApertura = :fechaApertura")})
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "tipo_cuenta", nullable = false, length = 256)
    private String tipoCuenta;
    @Basic(optional = false)
    @Column(name = "saldo_actual", nullable = false)
    private float saldoActual;
    @Basic(optional = false)
    @Column(name = "saldo_medio", nullable = false)
    private float saldoMedio;
    @Basic(optional = false)
    @Column(name = "numero_cuenta", nullable = false)
    private int numeroCuenta;
    @Basic(optional = false)
    @Column(name = "fecha_apertura", nullable = false, length = 255)
    private String fechaApertura;
    @JoinColumn(name = "banco_id", referencedColumnName = "id")
    @ManyToOne
    private Banco bancoId;
    @JoinColumn(name = "sucursal_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Sucursal sucursalId;
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Cliente clienteId;

    public Cuenta() {
    }

    public Cuenta(Integer id) {
        this.id = id;
    }

    public Cuenta(Integer id, String tipoCuenta, float saldoActual, float saldoMedio, int numeroCuenta, String fechaApertura) {
        this.id = id;
        this.tipoCuenta = tipoCuenta;
        this.saldoActual = saldoActual;
        this.saldoMedio = saldoMedio;
        this.numeroCuenta = numeroCuenta;
        this.fechaApertura = fechaApertura;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public float getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(float saldoActual) {
        this.saldoActual = saldoActual;
    }

    public float getSaldoMedio() {
        return saldoMedio;
    }

    public void setSaldoMedio(float saldoMedio) {
        this.saldoMedio = saldoMedio;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Banco getBancoId() {
        return bancoId;
    }

    public void setBancoId(Banco bancoId) {
        this.bancoId = bancoId;
    }

    public Sucursal getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Sucursal sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
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
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Cuenta[ id=" + id + " ]";
    }
    
}
