package org.example.access;

import lombok.RequiredArgsConstructor;
import org.example.entity.Document;
import org.example.repository.DocumentRepository;
import org.example.security.CurrentUserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentAccessPolicy {

    private final CurrentUserService currentUser;
    private final DocumentRepository repo;

    private boolean isSameOrg(Long orgId) {
        return currentUser.get().getOrganizationId().equals(orgId);
    }

    public boolean canRead(Long id) {

        if (currentUser.get().isAdmin()) {
            return true;
        }

        return repo.findByIdAndOrganizationAccess(id, currentUser.get().getOrganizationId())
                .isPresent();
    }

    public boolean canCreate(Long senderId, Long receiverId) {

        if (currentUser.get().isAdmin()) {
            return true;
        }

        Long myOrg = currentUser.get().getOrganizationId();

        return senderId.equals(myOrg) || receiverId.equals(myOrg);
    }

    public boolean canUpdate(Long id) {
        return canRead(id);
    }

    public boolean canDelete(Long id) {
        return canRead(id);
    }
}