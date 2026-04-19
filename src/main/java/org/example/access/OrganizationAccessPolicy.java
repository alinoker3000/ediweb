package org.example.access;

import lombok.RequiredArgsConstructor;
import org.example.repository.OrganizationRepository;
import org.example.security.CurrentUserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizationAccessPolicy {

    private final CurrentUserService currentUser;

    public boolean canRead(Long id) {
        return currentUser.get().isAdmin()
                || id.equals(currentUser.get().getOrganizationId());
    }

    public boolean canUpdate(Long id) {
        return canRead(id);
    }

    public boolean canCreate() {
        return currentUser.get().isAdmin();
    }

    public boolean canDelete(Long id) {
        return currentUser.get().isAdmin();
    }
}