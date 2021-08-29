package uz.pdp.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.chat.entity.AttachmentContent;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttachmentContentRepo extends JpaRepository<AttachmentContent, UUID> {
    Optional<AttachmentContent> findByAttachmentId(UUID id);
}
