package ch.hearc.SaphirLion.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * The user of the application
 * 
 * @note The table name is "UserAccount" because "User" is a reserved word in H2
 *       DB
 */
@Entity
@Table(name = "UserAccount")
@JsonIgnoreProperties(value = {"accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled", "authorities"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserMedia> usermedias = new ArrayList<UserMedia>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // TODO (just a note, not for this version) : if we want to be able to read the
    // password from an admin for exemple, replace @JsonIgnore by @JsonView(...)
    // https://www.baeldung.com/jackson-json-view-annotation
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPasswordAndCrypt(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    @JsonIgnore
    public List<UserMedia> getUserMedias() {
        return usermedias;
    }

    // Getter to have the id of the media when Json serializing
    public List<Long> getUserMediasIds() {
        return usermedias.stream().map(UserMedia::getId).toList();
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
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}