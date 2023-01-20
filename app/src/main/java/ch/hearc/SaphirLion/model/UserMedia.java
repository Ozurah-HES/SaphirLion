package ch.hearc.SaphirLion.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
public class UserMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;

    @PositiveOrZero(message = "Le nombre de parution doit être positif")
    private int nbPublished;

    @PositiveOrZero(message = "Le nombre de possession doit être positif")
    private int nbOwned;

    @PositiveOrZero(message = "Le dernier vu doit être positif")
    private int lastSeen;

    @Size(max = 255, message = "La remarque ne doit pas dépasser 255 caractères")
    private String remark;

    @AssertTrue(message = "Le nombre de parution doit être supérieur au nombre de possession")
    public boolean isNbPublishedGreaterThanNbOwned() {
        return nbPublished >= nbOwned;
    }

    @AssertTrue(message = "Le nombre de parution doit être supérieur au nombre de vu")
    public boolean isNbPublishedGreaterThanLastSeen() {
        return nbPublished >= lastSeen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getNbPublished() {
        return nbPublished;
    }

    public void setNbPublished(int nbPublished) {
        this.nbPublished = nbPublished;
    }

    public int getNbOwned() {
        return nbOwned;
    }

    public void setNbOwned(int nbOwned) {
        this.nbOwned = nbOwned;
    }

    public int getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(int lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

        return Objects.equals(id, ((UserMedia) obj).id);
    }
}