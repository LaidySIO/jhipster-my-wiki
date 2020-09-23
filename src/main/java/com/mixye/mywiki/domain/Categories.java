package com.mixye.mywiki.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Categories.
 */
@Entity
@Table(name = "categories")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Categories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "category_name", length = 25, nullable = false, unique = true)
    private String categoryName;

    @NotNull
    @Size(min = 10)
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "category_image")
    private byte[] categoryImage;

    @Column(name = "category_image_content_type")
    private String categoryImageContentType;

    @NotNull
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    @NotNull
    @Column(name = "user", nullable = false)
    private String user;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Categories categoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public Categories description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getCategoryImage() {
        return categoryImage;
    }

    public Categories categoryImage(byte[] categoryImage) {
        this.categoryImage = categoryImage;
        return this;
    }

    public void setCategoryImage(byte[] categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryImageContentType() {
        return categoryImageContentType;
    }

    public Categories categoryImageContentType(String categoryImageContentType) {
        this.categoryImageContentType = categoryImageContentType;
        return this;
    }

    public void setCategoryImageContentType(String categoryImageContentType) {
        this.categoryImageContentType = categoryImageContentType;
    }

    public Boolean isIsPublic() {
        return isPublic;
    }

    public Categories isPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getUser() {
        return user;
    }

    public Categories user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Categories creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categories)) {
            return false;
        }
        return id != null && id.equals(((Categories) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categories{" +
            "id=" + getId() +
            ", categoryName='" + getCategoryName() + "'" +
            ", description='" + getDescription() + "'" +
            ", categoryImage='" + getCategoryImage() + "'" +
            ", categoryImageContentType='" + getCategoryImageContentType() + "'" +
            ", isPublic='" + isIsPublic() + "'" +
            ", user='" + getUser() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
