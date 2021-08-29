package uz.pdp.chat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.chat.entity.Attachment;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse  {
    private String message;
    private boolean success;
    private Object object;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
