package uz.pdp.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.chat.payload.ApiResponse;
import uz.pdp.chat.payload.LoginDTO;
import uz.pdp.chat.payload.RegisterDTO;
import uz.pdp.chat.service.AuthService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDTO registerDTO){
        ApiResponse apiResponse=authService.register(registerDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDTO loginDTO){
        ApiResponse apiResponse=authService.login(loginDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email){
        ApiResponse apiResponse = authService.verifyEmail(emailCode,email);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
