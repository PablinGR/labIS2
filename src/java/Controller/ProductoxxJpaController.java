/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import BB.Productoxx;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author pablingr
 */
public class ProductoxxJpaController implements Serializable {

    public ProductoxxJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    //Control Vacio
    public ProductoxxJpaController(){
        
    }
    //
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;
    public EntityManager getEntityManager() {
        //
        if(emf==null)
        {
            emf = Persistence.createEntityManagerFactory("LaboratorioPU");
        }
        //
        return emf.createEntityManager();
    }

    public void create(Productoxx productoxx) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        //
        System.out.println("Antes de ..." + productoxx.toString() );
        Context context = new InitialContext();
        utx = (UserTransaction)context.lookup("java:comp/env/UserTransaction");
        //
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(productoxx);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProductoxx(productoxx.getCodigo()) != null) {
                throw new PreexistingEntityException("Productoxx " + productoxx + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productoxx productoxx) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            productoxx = em.merge(productoxx);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productoxx.getCodigo();
                if (findProductoxx(id) == null) {
                    throw new NonexistentEntityException("The productoxx with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Productoxx productoxx;
            try {
                productoxx = em.getReference(Productoxx.class, id);
                productoxx.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoxx with id " + id + " no longer exists.", enfe);
            }
            em.remove(productoxx);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productoxx> findProductoxxEntities() {
        return findProductoxxEntities(true, -1, -1);
    }

    public List<Productoxx> findProductoxxEntities(int maxResults, int firstResult) {
        return findProductoxxEntities(false, maxResults, firstResult);
    }

    private List<Productoxx> findProductoxxEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productoxx.class));
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

    public Productoxx findProductoxx(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productoxx.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoxxCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productoxx> rt = cq.from(Productoxx.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
