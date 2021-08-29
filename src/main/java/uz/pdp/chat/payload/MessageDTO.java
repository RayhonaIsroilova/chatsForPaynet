package uz.pdp.chat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String title;
    private String text;
    private String receiver;
    private MultipartFile attachment;
}
