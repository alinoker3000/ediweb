package org.example.access;

import lombok.RequiredArgsConstructor;
import org.example.repository.OrganizationRepository;
import org.example.security.CurrentUserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizationAccessPolicy {

    private final OrganizationRepository repo;
    private final CurrentUserService currentUser;

    public boolean canRead(Long id) {
        if (currentUser.isAdmin()) return true;

        return id.equals(currentUser.companyId());
    }

    public boolean canUpdate(Long id) {
        if (currentUser.isAdmin()) return true;

        return id.equals(currentUser.companyId());
    }

    public boolean canCreate() {
        return currentUser.isAdmin();
    }

    public boolean canDelete() {
        return currentUser.isAdmin();
    }


}