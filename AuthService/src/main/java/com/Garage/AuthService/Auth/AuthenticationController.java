package com.Garage.AuthService.Auth;


import com.Garage.AuthService.Config.JwtService;
import com.Garage.AuthService.User.User;
import com.Garage.AuthService.User.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private UserRepository userRepository;

    private final AuthenticationService service;
    private final JavaMailSender javaMailSender; // Inject JavaMailSender
    private final PasswordEncoder passwordEncoder;


    @Autowired
    private JwtService jwtService;


    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        jwtService.invalidateToken(email); // Invalidate JWT token
        return ResponseEntity.ok("Logged out successfully");
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/validateToken")
    public ResponseEntity<String> validateToken(@NonNull HttpServletRequest request){
        return jwtService.validateToken(request);
    }

    @GetMapping("/checkEmailExists/{email}")
    public ResponseEntity<?> checkEmailExists(@PathVariable String email){
        return jwtService.checkEmailExists(email);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            try {
                // Generate a unique token for password reset using JwtService
                String token = jwtService.generatePasswordResetToken(user.getEmail());
                user.setResetToken(token);
                userRepository.save(user);

                sendPasswordResetEmail(email, token);

                return ResponseEntity.ok("Password reset email sent"+token);
            } catch (MessagingException e) {
                System.err.println("Error sending email: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to send password reset email");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with email " + email + " not found");
        }
    }
    private void sendPasswordResetEmail(String email, String token) throws MessagingException {

        String resetLink = "http://localhost:3000/reset-password/" + token;
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("Password Reset");
        String emailBody = "Click the following link to reset your password: " + resetLink;
        helper.setText(emailBody);

        javaMailSender.send(message);
    }
//    @PostMapping("/reset-password/confirm")
//    public ResponseEntity<String> resetPasswordConfirm(@RequestBody Map<String, String> request) {
//        String token = request.get("token");
//        String newPassword = request.get("password");
//        // Logic to verify token and update user's password
//        // You'll need to validate the token and update the user's password in your database
//
//        // For now, let's just print the token and new password
//        System.out.println("Password reset confirmed for token: " + token);
//        System.out.println("New Password: " + newPassword);
//
//        return ResponseEntity.ok("Password reset confirmed");
//    }




    @PostMapping("/reset-password/confirm")
    public ResponseEntity<String> resetPasswordConfirm(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("password");

        // Logic to verify token and update user's password
        boolean passwordUpdated = updatePassword(token, newPassword);

        if (passwordUpdated) {
            return ResponseEntity.ok("Password reset confirmed");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update password");
        }
    }

    private boolean updatePassword(String token, String newPassword) {
        try {
            Optional<User> optionalUser = userRepository.findByResetToken(token);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
//                user.setPassword(newPassword);
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetToken(null); // Clear reset token after password update
                userRepository.save(user);

                System.out.println("Password updated for user: " + user.getEmail());
                return true;
            } else {
                // Token not found or expired
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error updating password: " + e.getMessage());
            return false;
        }
    }

    // Helper method to extract token from request headers
//    @GetMapping("/extractUserEmail")
//    public ResponseEntity<?> extractUserEmailFromToken(HttpServletRequest request) {
//        try {
//            String userEmail = jwtService.extractUserEmailFromToken(request);
//            if (userEmail != null) {
//                return ResponseEntity.ok(userEmail);
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error extracting user email");
//        }
//    }

}
