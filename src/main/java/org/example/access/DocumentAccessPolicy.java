package org.example.access;

import lombok.RequiredArgsConstructor;
import org.example.entity.Document;
import org.example.repository.DocumentRepository;
import org.example.security.CurrentUserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentAccessPolicy {

    private final DocumentRepository repo;
    private final CurrentUserService currentUser;

    private boolean belongs(Document doc) {
        Long orgId = currentUser.companyId();
        return doc.getHeader().getSender().getId().equals(orgId)
                || doc.getHeader().getReceiver().getId().equals(orgId);
    }

    public boolean canRead(Long id) {
        if (currentUser.isAdmin()) return true;

        Document doc = repo.findById(id).orElseThrow();
        return belongs(doc);
    }

    public boolean canCreate(Long senderId, Long receiverId) {
        if (currentUser.isAdmin()) return true;

        Long myOrg = currentUser.companyId();
        return senderId.equals(myOrg) || receiverId.equals(myOrg);
    }

    public boolean canUpdate(Long id) {
        return canRead(id);
    }

    public boolean canDelete(Long id) {
        return canRead(id);
    }
}