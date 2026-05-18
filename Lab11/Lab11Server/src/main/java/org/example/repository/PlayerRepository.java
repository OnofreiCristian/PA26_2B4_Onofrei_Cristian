package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.model.Player;
import org.example.util.PersistenceManager;
import org.example.util.QueryLogger;

import java.util.List;

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

        long startTime = System.currentTimeMillis();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
        try {
            Player player = em.createQuery("SELECT p FROM Player p WHERE p.name = :name", Player.class)
                    .setParameter("name", name)
                    .getSingleResult();
            QueryLogger.logExecutionTime("PlayerRepository.findByName", startTime);
            return player;
        } catch (NoResultException e) {
            QueryLogger.logExecutionTime("PlayerRepository.findByName (Not Found)", startTime);
            return null;
        }
        catch (Exception e) {
            QueryLogger.logException("PlayerRepository.findByName", e);
            return null;
        }
        finally {
            em.close();
        }
    }

    public List<Player> findPlayersByGameId(Long gameId) {
        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT r.player FROM Result r WHERE r.game.id = :gameId ORDER BY r.score DESC";
            return em.createQuery(jpql, Player.class)
                    .setParameter("gameId", gameId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public boolean updatePlayerName(Long playerId, String newName) {
        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            String jpql = "UPDATE Player p SET p.name = :newName WHERE p.id = :id";
            int updatedCount = em.createQuery(jpql)
                    .setParameter("newName", newName)
                    .setParameter("id", playerId)
                    .executeUpdate(); // executeUpdate() makes it a modifying query!

            em.getTransaction().commit();
            return updatedCount > 0;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e; // We will catch and log this later
        } finally {
            em.close();
        }
    }
}