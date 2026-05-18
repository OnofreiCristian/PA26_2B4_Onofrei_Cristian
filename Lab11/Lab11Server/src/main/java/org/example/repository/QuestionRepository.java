package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Question;
import org.example.util.PersistenceManager;
import org.example.util.QueryLogger;

import java.util.List;

public class QuestionRepository {

    public void save(Question question) {
        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(question);
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

    public List<Question> findAll() {

        long startTime = System.currentTimeMillis();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
        try {
            List<Question> questions = em.createQuery("SELECT q FROM Question q", Question.class).getResultList();
            QueryLogger.logExecutionTime("QuestionRepository.findAll", startTime);
            return questions;
        }
        catch (Exception e) {
            QueryLogger.logException("QuestionRepository.findAll", e);
            throw e;
        }
        finally {
            em.close();
        }
    }
}