/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import repository.exceptions.NonexistentEntityException;
import repository.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import models.Organizacion;

/**
 *
 * @author usuario
 */
public class OrganizacionCrud implements Serializable {

    public OrganizacionCrud(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Organizacion organizacion) throws PreexistingEntityException, Exception {
        if (organizacion.getClienteList() == null) {
            organizacion.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : organizacion.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getId());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            organizacion.setClienteList(attachedClienteList);
            em.persist(organizacion);
            for (Cliente clienteListCliente : organizacion.getClienteList()) {
                Organizacion oldOrganizacionIdOfClienteListCliente = clienteListCliente.getOrganizacionId();
                clienteListCliente.setOrganizacionId(organizacion);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldOrganizacionIdOfClienteListCliente != null) {
                    oldOrganizacionIdOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldOrganizacionIdOfClienteListCliente = em.merge(oldOrganizacionIdOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrganizacion(organizacion.getId()) != null) {
                throw new PreexistingEntityException("Organizacion " + organizacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Organizacion organizacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Organizacion persistentOrganizacion = em.find(Organizacion.class, organizacion.getId());
            List<Cliente> clienteListOld = persistentOrganizacion.getClienteList();
            List<Cliente> clienteListNew = organizacion.getClienteList();
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getId());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            organizacion.setClienteList(clienteListNew);
            organizacion = em.merge(organizacion);
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.setOrganizacionId(null);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Organizacion oldOrganizacionIdOfClienteListNewCliente = clienteListNewCliente.getOrganizacionId();
                    clienteListNewCliente.setOrganizacionId(organizacion);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldOrganizacionIdOfClienteListNewCliente != null && !oldOrganizacionIdOfClienteListNewCliente.equals(organizacion)) {
                        oldOrganizacionIdOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldOrganizacionIdOfClienteListNewCliente = em.merge(oldOrganizacionIdOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = organizacion.getId();
                if (findOrganizacion(id) == null) {
                    throw new NonexistentEntityException("The organizacion with id " + id + " no longer exists.");
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
            Organizacion organizacion;
            try {
                organizacion = em.getReference(Organizacion.class, id);
                organizacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The organizacion with id " + id + " no longer exists.", enfe);
            }
            List<Cliente> clienteList = organizacion.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.setOrganizacionId(null);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(organizacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Organizacion> findOrganizacionEntities() {
        return findOrganizacionEntities(true, -1, -1);
    }

    public List<Organizacion> findOrganizacionEntities(int maxResults, int firstResult) {
        return findOrganizacionEntities(false, maxResults, firstResult);
    }

    private List<Organizacion> findOrganizacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Organizacion.class));
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

    public Organizacion findOrganizacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Organizacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrganizacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Organizacion> rt = cq.from(Organizacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
