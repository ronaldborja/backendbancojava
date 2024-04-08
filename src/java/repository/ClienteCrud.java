/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import repository.exceptions.IllegalOrphanException;
import repository.exceptions.NonexistentEntityException;
import repository.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.ClienteNatural;
import models.Organizacion;
import models.Cuenta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import models.Cliente;

/**
 *
 * @author usuario
 */
public class ClienteCrud implements Serializable {

    public ClienteCrud(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws PreexistingEntityException, Exception {
        if (cliente.getCuentaList() == null) {
            cliente.setCuentaList(new ArrayList<Cuenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteNatural clienteNaturalId = cliente.getClienteNaturalId();
            if (clienteNaturalId != null) {
                clienteNaturalId = em.getReference(clienteNaturalId.getClass(), clienteNaturalId.getId());
                cliente.setClienteNaturalId(clienteNaturalId);
            }
            Organizacion organizacionId = cliente.getOrganizacionId();
            if (organizacionId != null) {
                organizacionId = em.getReference(organizacionId.getClass(), organizacionId.getId());
                cliente.setOrganizacionId(organizacionId);
            }
            List<Cuenta> attachedCuentaList = new ArrayList<Cuenta>();
            for (Cuenta cuentaListCuentaToAttach : cliente.getCuentaList()) {
                cuentaListCuentaToAttach = em.getReference(cuentaListCuentaToAttach.getClass(), cuentaListCuentaToAttach.getId());
                attachedCuentaList.add(cuentaListCuentaToAttach);
            }
            cliente.setCuentaList(attachedCuentaList);
            em.persist(cliente);
            if (clienteNaturalId != null) {
                clienteNaturalId.getClienteList().add(cliente);
                clienteNaturalId = em.merge(clienteNaturalId);
            }
            if (organizacionId != null) {
                organizacionId.getClienteList().add(cliente);
                organizacionId = em.merge(organizacionId);
            }
            for (Cuenta cuentaListCuenta : cliente.getCuentaList()) {
                Cliente oldClienteIdOfCuentaListCuenta = cuentaListCuenta.getClienteId();
                cuentaListCuenta.setClienteId(cliente);
                cuentaListCuenta = em.merge(cuentaListCuenta);
                if (oldClienteIdOfCuentaListCuenta != null) {
                    oldClienteIdOfCuentaListCuenta.getCuentaList().remove(cuentaListCuenta);
                    oldClienteIdOfCuentaListCuenta = em.merge(oldClienteIdOfCuentaListCuenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliente(cliente.getId()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            ClienteNatural clienteNaturalIdOld = persistentCliente.getClienteNaturalId();
            ClienteNatural clienteNaturalIdNew = cliente.getClienteNaturalId();
            Organizacion organizacionIdOld = persistentCliente.getOrganizacionId();
            Organizacion organizacionIdNew = cliente.getOrganizacionId();
            List<Cuenta> cuentaListOld = persistentCliente.getCuentaList();
            List<Cuenta> cuentaListNew = cliente.getCuentaList();
            List<String> illegalOrphanMessages = null;
            for (Cuenta cuentaListOldCuenta : cuentaListOld) {
                if (!cuentaListNew.contains(cuentaListOldCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuenta " + cuentaListOldCuenta + " since its clienteId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteNaturalIdNew != null) {
                clienteNaturalIdNew = em.getReference(clienteNaturalIdNew.getClass(), clienteNaturalIdNew.getId());
                cliente.setClienteNaturalId(clienteNaturalIdNew);
            }
            if (organizacionIdNew != null) {
                organizacionIdNew = em.getReference(organizacionIdNew.getClass(), organizacionIdNew.getId());
                cliente.setOrganizacionId(organizacionIdNew);
            }
            List<Cuenta> attachedCuentaListNew = new ArrayList<Cuenta>();
            for (Cuenta cuentaListNewCuentaToAttach : cuentaListNew) {
                cuentaListNewCuentaToAttach = em.getReference(cuentaListNewCuentaToAttach.getClass(), cuentaListNewCuentaToAttach.getId());
                attachedCuentaListNew.add(cuentaListNewCuentaToAttach);
            }
            cuentaListNew = attachedCuentaListNew;
            cliente.setCuentaList(cuentaListNew);
            cliente = em.merge(cliente);
            if (clienteNaturalIdOld != null && !clienteNaturalIdOld.equals(clienteNaturalIdNew)) {
                clienteNaturalIdOld.getClienteList().remove(cliente);
                clienteNaturalIdOld = em.merge(clienteNaturalIdOld);
            }
            if (clienteNaturalIdNew != null && !clienteNaturalIdNew.equals(clienteNaturalIdOld)) {
                clienteNaturalIdNew.getClienteList().add(cliente);
                clienteNaturalIdNew = em.merge(clienteNaturalIdNew);
            }
            if (organizacionIdOld != null && !organizacionIdOld.equals(organizacionIdNew)) {
                organizacionIdOld.getClienteList().remove(cliente);
                organizacionIdOld = em.merge(organizacionIdOld);
            }
            if (organizacionIdNew != null && !organizacionIdNew.equals(organizacionIdOld)) {
                organizacionIdNew.getClienteList().add(cliente);
                organizacionIdNew = em.merge(organizacionIdNew);
            }
            for (Cuenta cuentaListNewCuenta : cuentaListNew) {
                if (!cuentaListOld.contains(cuentaListNewCuenta)) {
                    Cliente oldClienteIdOfCuentaListNewCuenta = cuentaListNewCuenta.getClienteId();
                    cuentaListNewCuenta.setClienteId(cliente);
                    cuentaListNewCuenta = em.merge(cuentaListNewCuenta);
                    if (oldClienteIdOfCuentaListNewCuenta != null && !oldClienteIdOfCuentaListNewCuenta.equals(cliente)) {
                        oldClienteIdOfCuentaListNewCuenta.getCuentaList().remove(cuentaListNewCuenta);
                        oldClienteIdOfCuentaListNewCuenta = em.merge(oldClienteIdOfCuentaListNewCuenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cuenta> cuentaListOrphanCheck = cliente.getCuentaList();
            for (Cuenta cuentaListOrphanCheckCuenta : cuentaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Cuenta " + cuentaListOrphanCheckCuenta + " in its cuentaList field has a non-nullable clienteId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ClienteNatural clienteNaturalId = cliente.getClienteNaturalId();
            if (clienteNaturalId != null) {
                clienteNaturalId.getClienteList().remove(cliente);
                clienteNaturalId = em.merge(clienteNaturalId);
            }
            Organizacion organizacionId = cliente.getOrganizacionId();
            if (organizacionId != null) {
                organizacionId.getClienteList().remove(cliente);
                organizacionId = em.merge(organizacionId);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
