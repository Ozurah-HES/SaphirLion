package ch.hearc.SaphirLion.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * <p>A media, like "One Piece", "Naruto", ...</p>
 * <p>It is linked to a type and a category</p>
 */
@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 1, max = 255, message = "Le nom du média ne doit pas dépasser 255 caractères")
    @NotBlank(message = "Le nom du média ne doit pas être vide")
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "media")
    private List<UserMedia> usermedias = new ArrayList<UserMedia>();

    @PositiveOrZero(message = "Le nombre de parution doit être positif")
    @NotNull(message = "Le dernier vu ne doit pas être vide")  
    @NumberFormat(style= Style.NUMBER) 
    private int nbPublished;

    private String imgUrl;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonIgnore
    public List<UserMedia> getUserMedias() {
        return usermedias;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        if (imgUrl == null || imgUrl.isBlank()) {
            return "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg";
        }
        return imgUrl;
    }

    public int getNbPublished() {
        return nbPublished;
    }

    public void setNbPublished(int nbPublished) {
        this.nbPublished = nbPublished;
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

        return Objects.equals(id, ((Media) obj).id);
    }
}
