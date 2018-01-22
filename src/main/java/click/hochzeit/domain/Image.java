package click.hochzeit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Image.
 */
@Entity
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "image")
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "alt")
    private String alt;

    @Column(name = "caption")
    private String caption;

    @Column(name = "img_original")
    private String imgOriginal;

    @Column(name = "img_large")
    private String imgLarge;

    @Column(name = "img_thumbnail")
    private String imgThumbnail;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Image title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlt() {
        return alt;
    }

    public Image alt(String alt) {
        this.alt = alt;
        return this;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getCaption() {
        return caption;
    }

    public Image caption(String caption) {
        this.caption = caption;
        return this;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImgOriginal() {
        return imgOriginal;
    }

    public Image imgOriginal(String imgOriginal) {
        this.imgOriginal = imgOriginal;
        return this;
    }

    public void setImgOriginal(String imgOriginal) {
        this.imgOriginal = imgOriginal;
    }

    public String getImgLarge() {
        return imgLarge;
    }

    public Image imgLarge(String imgLarge) {
        this.imgLarge = imgLarge;
        return this;
    }

    public void setImgLarge(String imgLarge) {
        this.imgLarge = imgLarge;
    }

    public String getImgThumbnail() {
        return imgThumbnail;
    }

    public Image imgThumbnail(String imgThumbnail) {
        this.imgThumbnail = imgThumbnail;
        return this;
    }

    public void setImgThumbnail(String imgThumbnail) {
        this.imgThumbnail = imgThumbnail;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Image image = (Image) o;
        if (image.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), image.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", alt='" + getAlt() + "'" +
            ", caption='" + getCaption() + "'" +
            ", imgOriginal='" + getImgOriginal() + "'" +
            ", imgLarge='" + getImgLarge() + "'" +
            ", imgThumbnail='" + getImgThumbnail() + "'" +
            "}";
    }
}
