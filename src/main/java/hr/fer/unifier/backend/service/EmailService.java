package hr.fer.unifier.backend.service;

public interface EmailService {

    void sendRecoveryMail(String email, String token);
}
