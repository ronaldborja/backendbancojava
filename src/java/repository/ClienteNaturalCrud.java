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
import models.ClienteNatural;

/**
 *
 * @author usuario
 */
public class ClienteNaturalCrud implements Serializable {

    public ClienteNaturalCrud(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClienteNatural clienteNatural) throws PreexistingEntityException, Exception {
        if (clienteNatural.getClienteList() == null) {
            clienteNatural.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : clienteNatural.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getId());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            clienteNatural.setClienteList(attachedClienteList);
            em.persist(clienteNatural);
            for (Cliente clienteListCliente : clienteNatural.getClienteList()) {
                ClienteNatural oldClienteNaturalIdOfClienteListCliente = clienteListCliente.getClienteNaturalId();
                clienteListCliente.setClienteNaturalId(clienteNatural);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldClienteNaturalIdOfClienteListCliente != null) {
                    oldClienteNaturalIdOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldClienteNaturalIdOfClienteListCliente = em.merge(oldClienteNaturalIdOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClienteNatural(clienteNatural.getId()) != null) {
                throw new PreexistingEntityException("ClienteNatural " + clienteNatural + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClienteNatural clienteNatural) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteNatural persistentClienteNatural = em.find(ClienteNatural.class, clienteNatural.getId());
            List<Cliente> clienteListOld = persistentClienteNatural.getClienteList();
            List<Cliente> clienteListNew = clienteNatural.getClienteList();
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getId());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            clienteNatural.setClienteList(clienteListNew);
            clienteNatural = em.merge(clienteNatural);
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.setClienteNaturalId(null);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    ClienteNatural oldClienteNaturalIdOfClienteListNewCliente = clienteListNewCliente.getClienteNaturalId();
                    clienteListNewCliente.setClienteNaturalId(clienteNatural);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldClienteNaturalIdOfClienteListNewCliente != null && !oldClienteNaturalIdOfClienteListNewCliente.equals(clienteNatural)) {
                        oldClienteNaturalIdOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldClienteNaturalIdOfClienteListNewCliente = em.merge(oldClienteNaturalIdOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clienteNatural.getId();
                if (findClienteNatural(id) == null) {
                    throw new NonexistentEntityException("The clienteNatural with id " + id + " no longer exists.");
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
            ClienteNatural clienteNatural;
            try {
                clienteNatural = em.getReference(ClienteNatural.class, id);
                clienteNatural.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clienteNatural with id " + id + " no longer exists.", enfe);
            }
            List<Cliente> clienteList = clienteNatural.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.setClienteNaturalId(null);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(clienteNatural);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClienteNatural> findClienteNaturalEntities() {
        return findClienteNaturalEntities(true, -1, -1);
    }

    public List<ClienteNatural> findClienteNaturalEntities(int maxResults, int firstResult) {
        return findClienteNaturalEntities(false, maxResults, firstResult);
    }

    private List<ClienteNatural> findClienteNaturalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClienteNatural.class));
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

    public ClienteNatural findClienteNatural(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClienteNatural.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteNaturalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClienteNatural> rt = cq.from(ClienteNatural.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
