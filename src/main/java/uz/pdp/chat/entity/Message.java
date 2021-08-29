package uz.pdp.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String title;

    @Column
    private String text;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @CreationTimestamp
    private Timestamp sentTime;

    @ManyToOne
    private Attachment attachment;


}
