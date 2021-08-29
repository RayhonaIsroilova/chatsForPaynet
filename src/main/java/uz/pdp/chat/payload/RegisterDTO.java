package uz.pdp.chat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotBlank(message = "Firstname can't be empty!")
    private String firstName;

    @NotBlank(message = "Lastname can't be empty!")
    private String lastName;

    @Email
    @NotBlank(message = "Email can't be empty!")
    private String email;


    @NotBlank(message = "Password can't be empty!")
    private String password;
}
