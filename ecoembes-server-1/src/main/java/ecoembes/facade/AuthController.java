package ecoembes.facade;

import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ecoembes.dto.CredentialsDTO;
import ecoembes.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization Controller", description = "Login and logout operations")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
        summary = "Login",
        description = "Employee login using email and password",
        responses = {
            @ApiResponse(responseCode = "200", description = "Login ok, returns token"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
        }
    )
    @PostMapping("/login") //llama a authService.login()
    public ResponseEntity<String> login(@RequestBody CredentialsDTO credentials) {
        Optional<String> token = authService.login(credentials.getEmail(), credentials.getPassword());
        return token
                .map(t -> new ResponseEntity<>(t, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @Operation(
        summary = "Logout",
        description = "Logout employee using token",
        responses = {
            @ApiResponse(responseCode = "204", description = "Logout ok"),
            @ApiResponse(responseCode = "401", description = "Invalid token")
        }
    )

    @PostMapping("/logout")//llama a authService.logout()
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        // Extract token from "Bearer <token>"
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        
        Optional<Boolean> result = authService.logout(token);
        if (result.isPresent() && result.get()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
