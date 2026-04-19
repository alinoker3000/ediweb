package org.example.repository;

import org.example.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("""
        SELECT d FROM Document d
        WHERE d.header.sender.id = :orgId
           or d.header.receiver.id = :orgId
        """)
    List<Document> findByOrganizationAccess(Long orgId);

    @Query("""
        SELECT d FROM Document d
        WHERE d.id = :id
        AND (d.header.sender.id = :orgId
        OR d.header.receiver.id = :orgId)
        """)
    Optional<Document> findByIdAndOrganizationAccess(Long id, Long orgId);
}
