/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import repository.exceptions.IllegalOrphanException;
import repository.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Cuenta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import models.Empleado;
import models.Sucursal;

/**
 *
 * @author usuario
 */
public class SucursalCrud implements Serializable {

    public SucursalCrud(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sucursal sucursal) {
        if (sucursal.getCuentaList() == null) {
            sucursal.setCuentaList(new ArrayList<Cuenta>());
        }
        if (sucursal.getEmpleadoList() == null) {
            sucursal.setEmpleadoList(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cuenta> attachedCuentaList = new ArrayList<Cuenta>();
            for (Cuenta cuentaListCuentaToAttach : sucursal.getCuentaList()) {
                cuentaListCuentaToAttach = em.getReference(cuentaListCuentaToAttach.getClass(), cuentaListCuentaToAttach.getId());
                attachedCuentaList.add(cuentaListCuentaToAttach);
            }
            sucursal.setCuentaList(attachedCuentaList);
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : sucursal.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getDni());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            sucursal.setEmpleadoList(attachedEmpleadoList);
            em.persist(sucursal);
            for (Cuenta cuentaListCuenta : sucursal.getCuentaList()) {
                Sucursal oldSucursalIdOfCuentaListCuenta = cuentaListCuenta.getSucursalId();
                cuentaListCuenta.setSucursalId(sucursal);
                cuentaListCuenta = em.merge(cuentaListCuenta);
                if (oldSucursalIdOfCuentaListCuenta != null) {
                    oldSucursalIdOfCuentaListCuenta.getCuentaList().remove(cuentaListCuenta);
                    oldSucursalIdOfCuentaListCuenta = em.merge(oldSucursalIdOfCuentaListCuenta);
                }
            }
            for (Empleado empleadoListEmpleado : sucursal.getEmpleadoList()) {
                Sucursal oldSucursalidOfEmpleadoListEmpleado = empleadoListEmpleado.getSucursalid();
                empleadoListEmpleado.setSucursalid(sucursal);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldSucursalidOfEmpleadoListEmpleado != null) {
                    oldSucursalidOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldSucursalidOfEmpleadoListEmpleado = em.merge(oldSucursalidOfEmpleadoListEmpleado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sucursal sucursal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal persistentSucursal = em.find(Sucursal.class, sucursal.getId());
            List<Cuenta> cuentaListOld = persistentSucursal.getCuentaList();
            List<Cuenta> cuentaListNew = sucursal.getCuentaList();
            List<Empleado> empleadoListOld = persistentSucursal.getEmpleadoList();
            List<Empleado> empleadoListNew = sucursal.getEmpleadoList();
            List<String> illegalOrphanMessages = null;
            for (Cuenta cuentaListOldCuenta : cuentaListOld) {
                if (!cuentaListNew.contains(cuentaListOldCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuenta " + cuentaListOldCuenta + " since its sucursalId field is not nullable.");
                }
            }
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoListOldEmpleado + " since its sucursalid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cuenta> attachedCuentaListNew = new ArrayList<Cuenta>();
            for (Cuenta cuentaListNewCuentaToAttach : cuentaListNew) {
                cuentaListNewCuentaToAttach = em.getReference(cuentaListNewCuentaToAttach.getClass(), cuentaListNewCuentaToAttach.getId());
                attachedCuentaListNew.add(cuentaListNewCuentaToAttach);
            }
            cuentaListNew = attachedCuentaListNew;
            sucursal.setCuentaList(cuentaListNew);
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getDni());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            sucursal.setEmpleadoList(empleadoListNew);
            sucursal = em.merge(sucursal);
            for (Cuenta cuentaListNewCuenta : cuentaListNew) {
                if (!cuentaListOld.contains(cuentaListNewCuenta)) {
                    Sucursal oldSucursalIdOfCuentaListNewCuenta = cuentaListNewCuenta.getSucursalId();
                    cuentaListNewCuenta.setSucursalId(sucursal);
                    cuentaListNewCuenta = em.merge(cuentaListNewCuenta);
                    if (oldSucursalIdOfCuentaListNewCuenta != null && !oldSucursalIdOfCuentaListNewCuenta.equals(sucursal)) {
                        oldSucursalIdOfCuentaListNewCuenta.getCuentaList().remove(cuentaListNewCuenta);
                        oldSucursalIdOfCuentaListNewCuenta = em.merge(oldSucursalIdOfCuentaListNewCuenta);
                    }
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Sucursal oldSucursalidOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getSucursalid();
                    empleadoListNewEmpleado.setSucursalid(sucursal);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldSucursalidOfEmpleadoListNewEmpleado != null && !oldSucursalidOfEmpleadoListNewEmpleado.equals(sucursal)) {
                        oldSucursalidOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldSucursalidOfEmpleadoListNewEmpleado = em.merge(oldSucursalidOfEmpleadoListNewEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sucursal.getId();
                if (findSucursal(id) == null) {
                    throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.");
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
            Sucursal sucursal;
            try {
                sucursal = em.getReference(Sucursal.class, id);
                sucursal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cuenta> cuentaListOrphanCheck = sucursal.getCuentaList();
            for (Cuenta cuentaListOrphanCheckCuenta : cuentaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursal (" + sucursal + ") cannot be destroyed since the Cuenta " + cuentaListOrphanCheckCuenta + " in its cuentaList field has a non-nullable sucursalId field.");
            }
            List<Empleado> empleadoListOrphanCheck = sucursal.getEmpleadoList();
            for (Empleado empleadoListOrphanCheckEmpleado : empleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursal (" + sucursal + ") cannot be destroyed since the Empleado " + empleadoListOrphanCheckEmpleado + " in its empleadoList field has a non-nullable sucursalid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sucursal> findSucursalEntities() {
        return findSucursalEntities(true, -1, -1);
    }

    public List<Sucursal> findSucursalEntities(int maxResults, int firstResult) {
        return findSucursalEntities(false, maxResults, firstResult);
    }

    private List<Sucursal> findSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sucursal.class));
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

    public Sucursal findSucursal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sucursal> rt = cq.from(Sucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
