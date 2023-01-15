package ch.hearc.SaphirLion.service;

import ch.hearc.SaphirLion.model.User;
import jakarta.servlet.http.HttpSession;

public interface AuthService_I {
    User authenticate(String username, String password, HttpSession session);
}
