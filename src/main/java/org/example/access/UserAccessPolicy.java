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
        if (currentUser.get().isAdmin()) return true;

        User user = repo.findById(id).orElseThrow();
        return user.getOrganization().getId().equals(currentUser.get().getOrganizationId());
    }

    public boolean canUpdate(Long id) {
        if (currentUser.get().isAdmin()) return true;

        return id.equals(currentUser.get().getUserId());
    }

    public boolean canCreate() {
        return currentUser.get().isAdmin();
    }


    public boolean canDelete(Long id) {
        return currentUser.get().isAdmin();
    }
}