package ch.hearc.SaphirLion.model;

import java.util.Set;
import java.util.TreeSet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;

@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    
    @ManyToOne
    @JoinColumn(name = "type_id", nullable=false)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable=false)
    private Category category;

    @OneToMany(mappedBy = "media")
    private Set<UserMedia> usermedias = new TreeSet<UserMedia>();

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

    public Set<UserMedia> getUsermedias() {
        return usermedias;
    }
}
