package uz.pdp.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AttachmentContent {

    @Id
    @GeneratedValue
    private UUID id;

    private byte[] bytes;

    @OneToOne
    private Attachment attachment;
}
