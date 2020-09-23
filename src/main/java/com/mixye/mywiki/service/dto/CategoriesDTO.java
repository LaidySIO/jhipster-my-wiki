package com.mixye.mywiki.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.mixye.mywiki.domain.Categories} entity.
 */
public class CategoriesDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String categoryName;

    @NotNull
    @Size(min = 10)
    private String description;

    @Lob
    private byte[] categoryImage;

    private String categoryImageContentType;
    @NotNull
    private Boolean isPublic;

    @NotNull
    private String user;

    @NotNull
    private LocalDate creationDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(byte[] categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryImageContentType() {
        return categoryImageContentType;
    }

    public void setCategoryImageContentType(String categoryImageContentType) {
        this.categoryImageContentType = categoryImageContentType;
    }

    public Boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriesDTO)) {
            return false;
        }

        return id != null && id.equals(((CategoriesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriesDTO{" +
            "id=" + getId() +
            ", categoryName='" + getCategoryName() + "'" +
            ", description='" + getDescription() + "'" +
            ", categoryImage='" + getCategoryImage() + "'" +
            ", isPublic='" + isIsPublic() + "'" +
            ", user='" + getUser() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
