package uz.pdp.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.chat.entity.Message;
import uz.pdp.chat.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepo extends JpaRepository<Message, UUID> {
    List<Message> findAllByReceiver(User receiver);
}
