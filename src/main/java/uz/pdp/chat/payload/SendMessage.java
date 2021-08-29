package uz.pdp.chat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessage {
    private String title;
    private String text;
    private String user;
    private Timestamp sentTime;

}
