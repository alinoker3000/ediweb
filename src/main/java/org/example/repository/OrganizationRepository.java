package org.example.repository;

import org.example.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query("""
        SELECT o FROM Organization o
        WHERE :isAdmin = true
           OR o.id = :orgId
        """)
    List<Organization> findAccessibleOrganizations(Long orgId, boolean isAdmin);
}
