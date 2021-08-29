package uz.pdp.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.chat.entity.Attachment;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, UUID> {
    Optional<Attachment> findById(Integer id);
}
