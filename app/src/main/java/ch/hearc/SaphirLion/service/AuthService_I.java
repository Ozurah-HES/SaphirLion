package ch.hearc.SaphirLion.service;

import ch.hearc.SaphirLion.model.User;
import javax.servlet.http.HttpSession;

public interface AuthService_I {
    User authenticate(String username, String password, HttpSession session);

    User connectedUser(HttpSession session);
}
