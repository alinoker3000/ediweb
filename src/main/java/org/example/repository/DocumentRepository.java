package org.example.repository;

import org.example.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("""
        SELECT d FROM Document d
        WHERE :isAdmin = true
           OR d.header.sender.id = :orgId
           OR d.header.receiver.id = :orgId
        """)
    List<Document> findAccessibleDocument(Long orgId, boolean isAdmin);
}
