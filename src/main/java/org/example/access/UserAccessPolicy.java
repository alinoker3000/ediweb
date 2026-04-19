package org.example.access;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.security.CurrentUserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccessPolicy {

    private final UserRepository repo;
    private final CurrentUserService currentUser;

    public boolean canRead(Long id) {
        if (currentUser.isAdmin()) return true;

        User user = repo.findById(id).orElseThrow();
        return user.getOrganization().getId().equals(currentUser.companyId());
    }

    public boolean canUpdate(Long id) {
        if (currentUser.isAdmin()) return true;

        return id.equals(currentUser.userId());
    }

    public boolean canCreate() {
        return currentUser.isAdmin();
    }

    public boolean canDelete(Long id) {
        return currentUser.isAdmin();
    }
}