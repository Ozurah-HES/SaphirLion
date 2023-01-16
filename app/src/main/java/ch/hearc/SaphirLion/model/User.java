package ch.hearc.SaphirLion.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Profile(value="secure-db")
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserMedia> usermedias = new ArrayList<UserMedia>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswordAndCrypt(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public List<UserMedia> getUserMedias() {
        return usermedias;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        return Objects.equals(id, ((User) obj).id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("HEYO8");

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        System.out.println("HEYO7");

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        System.out.println("HEYO6");

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        System.out.println("HEYO5");

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEnabled() {
        System.out.println("HEYO4");

        // TODO Auto-generated method stub
        return false;
    }
}