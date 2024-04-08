/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import repository.exceptions.NonexistentEntityException;
import repository.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Banco;
import models.Sucursal;
import models.Cliente;
import models.Cuenta;

/**
 *
 * @author usuario
 */
public class CuentaCrud implements Serializable {

    public CuentaCrud(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuenta cuenta) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Banco bancoId = cuenta.getBancoId();
            if (bancoId != null) {
                bancoId = em.getReference(bancoId.getClass(), bancoId.getId());
                cuenta.setBancoId(bancoId);
            }
            Sucursal sucursalId = cuenta.getSucursalId();
            if (sucursalId != null) {
                sucursalId = em.getReference(sucursalId.getClass(), sucursalId.getId());
                cuenta.setSucursalId(sucursalId);
            }
            Cliente clienteId = cuenta.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getId());
                cuenta.setClienteId(clienteId);
            }
            em.persist(cuenta);
            if (bancoId != null) {
                bancoId.getCuentaList().add(cuenta);
                bancoId = em.merge(bancoId);
            }
            if (sucursalId != null) {
                sucursalId.getCuentaList().add(cuenta);
                sucursalId = em.merge(sucursalId);
            }
            if (clienteId != null) {
                clienteId.getCuentaList().add(cuenta);
                clienteId = em.merge(clienteId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuenta(cuenta.getId()) != null) {
                throw new PreexistingEntityException("Cuenta " + cuenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuenta cuenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta persistentCuenta = em.find(Cuenta.class, cuenta.getId());
            Banco bancoIdOld = persistentCuenta.getBancoId();
            Banco bancoIdNew = cuenta.getBancoId();
            Sucursal sucursalIdOld = persistentCuenta.getSucursalId();
            Sucursal sucursalIdNew = cuenta.getSucursalId();
            Cliente clienteIdOld = persistentCuenta.getClienteId();
            Cliente clienteIdNew = cuenta.getClienteId();
            if (bancoIdNew != null) {
                bancoIdNew = em.getReference(bancoIdNew.getClass(), bancoIdNew.getId());
                cuenta.setBancoId(bancoIdNew);
            }
            if (sucursalIdNew != null) {
                sucursalIdNew = em.getReference(sucursalIdNew.getClass(), sucursalIdNew.getId());
                cuenta.setSucursalId(sucursalIdNew);
            }
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getId());
                cuenta.setClienteId(clienteIdNew);
            }
            cuenta = em.merge(cuenta);
            if (bancoIdOld != null && !bancoIdOld.equals(bancoIdNew)) {
                bancoIdOld.getCuentaList().remove(cuenta);
                bancoIdOld = em.merge(bancoIdOld);
            }
            if (bancoIdNew != null && !bancoIdNew.equals(bancoIdOld)) {
                bancoIdNew.getCuentaList().add(cuenta);
                bancoIdNew = em.merge(bancoIdNew);
            }
            if (sucursalIdOld != null && !sucursalIdOld.equals(sucursalIdNew)) {
                sucursalIdOld.getCuentaList().remove(cuenta);
                sucursalIdOld = em.merge(sucursalIdOld);
            }
            if (sucursalIdNew != null && !sucursalIdNew.equals(sucursalIdOld)) {
                sucursalIdNew.getCuentaList().add(cuenta);
                sucursalIdNew = em.merge(sucursalIdNew);
            }
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getCuentaList().remove(cuenta);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getCuentaList().add(cuenta);
                clienteIdNew = em.merge(clienteIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuenta.getId();
                if (findCuenta(id) == null) {
                    throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta cuenta;
            try {
                cuenta = em.getReference(Cuenta.class, id);
                cuenta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.", enfe);
            }
            Banco bancoId = cuenta.getBancoId();
            if (bancoId != null) {
                bancoId.getCuentaList().remove(cuenta);
                bancoId = em.merge(bancoId);
            }
            Sucursal sucursalId = cuenta.getSucursalId();
            if (sucursalId != null) {
                sucursalId.getCuentaList().remove(cuenta);
                sucursalId = em.merge(sucursalId);
            }
            Cliente clienteId = cuenta.getClienteId();
            if (clienteId != null) {
                clienteId.getCuentaList().remove(cuenta);
                clienteId = em.merge(clienteId);
            }
            em.remove(cuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuenta> findCuentaEntities() {
        return findCuentaEntities(true, -1, -1);
    }

    public List<Cuenta> findCuentaEntities(int maxResults, int firstResult) {
        return findCuentaEntities(false, maxResults, firstResult);
    }

    private List<Cuenta> findCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuenta.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cuenta findCuenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuenta> rt = cq.from(Cuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
