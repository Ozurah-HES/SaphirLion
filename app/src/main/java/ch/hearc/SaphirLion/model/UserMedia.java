package ch.hearc.SaphirLion.model;

import java.util.Objects;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * <p>The pivot table between user and media.</p>
 * <p>It contains the current information about the progression of the user on the media</p>
 */
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

    @PositiveOrZero(message = "Le nombre de possession doit être positif")
    @NotNull(message = "Le dernier vu ne doit pas être vide")  
    @NumberFormat(style= Style.NUMBER) 
    private int nbOwned;

    @PositiveOrZero(message = "Le dernier vu doit être positif") 
    @NotNull(message = "Le dernier vu ne doit pas être vide")  
    private int lastSeen;

    @Size(max = 255, message = "La remarque ne doit pas dépasser 255 caractères")
    private String remark;

    /**
     * Is the number of published is greater than the number of owned.
     * Note this field is no longer required because the nb published is managed by an admin.
     *    So, if admin forgot to update the nb published, the user can always update his nb owned.
     * @return
     */
    // @AssertTrue(message = "Le nombre de parution doit être supérieur au nombre de possession")
    public boolean isNbPublishedGreaterThanNbOwned() {
        return media.getNbPublished() >= nbOwned;
    }

    /**
     * Is the number of published is greater than the number of last seen.
     * Note this field is no longer required because the nb published is managed by an admin.
     *   So, if admin forgot to update the nb published, the user can always update his nb owned.
     * @return
     */
    // @AssertTrue(message = "Le nombre de parution doit être supérieur au nombre de vu")
    public boolean isNbPublishedGreaterThanLastSeen() {
        return media.getNbPublished() >= lastSeen;
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