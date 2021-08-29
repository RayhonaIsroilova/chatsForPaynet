package uz.pdp.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.chat.entity.User;
import uz.pdp.chat.payload.ApiResponse;
import uz.pdp.chat.payload.LoginDTO;
import uz.pdp.chat.payload.RegisterDTO;
import uz.pdp.chat.repository.UserRepo;
import uz.pdp.chat.security.JwtProvider;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.Security;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService implements  UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JavaMailSender javaMailSender;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepo.findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            user.setOnline(true);
            userRepo.save(user);
            return user;
        }
        throw new UsernameNotFoundException(email + " not found ):");
    }


    public ApiResponse register(RegisterDTO registerDTO) {
        if (userRepo.existsByEmail(registerDTO.getEmail()))
            return new ApiResponse("User already exist!", false);
        User user = new User();
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmailCode(String.valueOf(UUID.randomUUID()));
        userRepo.save(user);
        sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("You have registered successfully!", true);
    }

    public ApiResponse login(LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            String token = jwtProvider.generateToken(loginDTO.getEmail());
            return new ApiResponse("Token", true, token);
        } catch (BadCredentialsException e) {
            return new ApiResponse("Password or username incorrect!", false);
        }
    }

    public ApiResponse verifyEmail(String emailCode, String email) {
        Optional<User> optionalUser = userRepo.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            userRepo.save(user);
            return new ApiResponse("Account verrified!", true);
        }
        return new ApiResponse("Account already verrified!", false);
    }

    public void sendEmail(String sendingEmail, String emailCode) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setSubject("Click the button below to verify your account:");
            helper.setFrom("osiyoadilova03@gmail.com");
            helper.setTo(sendingEmail);
            helper.setText("<div style='text-align:center'><button type='button' style='background: #73ccff; cursor: pointer; outline: none; border-radius:2px; border:2px solid #005282; color:#005282; padding:10px 20px'><a style='text-decoration:none' href='http://localhost:80/api/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "'>Verify</a></button>" +
                    "<p style='margin-top:30px; font-weight:bold; font-size:28px'>If the button doesn't work, Click the link below!</p>" +
                    "<a style='font-size:20px' href='http://localhost:80/api/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "'>Verify link</a></div>", true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
