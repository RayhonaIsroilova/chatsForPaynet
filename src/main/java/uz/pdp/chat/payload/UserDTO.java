package uz.pdp.chat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private boolean online;
}
