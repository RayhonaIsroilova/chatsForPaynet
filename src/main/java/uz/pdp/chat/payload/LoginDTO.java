package uz.pdp.chat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "Email can't be empty!")
    private String email;

    @NotBlank(message = "Password can't be empty!")
    private String password;
}
