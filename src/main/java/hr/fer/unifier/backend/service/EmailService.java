package hr.fer.unifier.backend.service;

public interface EmailService {
    void testEmail();

    void sendRecoveryMail(String email, String token);
}
