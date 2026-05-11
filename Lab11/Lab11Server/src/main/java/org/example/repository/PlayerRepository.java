package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.model.Player;
import org.example.util.PersistenceManager;

public class PlayerRepository {

    public void save(Player player) {
        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(player);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Player findByName(String name) {
        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Player p WHERE p.name = :name", Player.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}