package org.example.model;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditListener {

    @PrePersist
    public void beforeInsert(Object entity) {
        System.out.println("[AUDIT] Inserting new record into database: " + entity.getClass().getSimpleName() + " at " + LocalDateTime.now());
    }

    @PreUpdate
    public void beforeUpdate(Object entity) {
        System.out.println("[AUDIT] Updating existing record in database: " + entity.getClass().getSimpleName() + " at " + LocalDateTime.now());
    }
}