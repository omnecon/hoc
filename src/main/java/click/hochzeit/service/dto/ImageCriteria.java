package click.hochzeit.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Image entity. This class is used in ImageResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /images?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ImageCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter title;

    private StringFilter alt;

    private StringFilter caption;

    private StringFilter imgOriginal;

    private StringFilter imgLarge;

    private StringFilter imgThumbnail;

    public ImageCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getAlt() {
        return alt;
    }

    public void setAlt(StringFilter alt) {
        this.alt = alt;
    }

    public StringFilter getCaption() {
        return caption;
    }

    public void setCaption(StringFilter caption) {
        this.caption = caption;
    }

    public StringFilter getImgOriginal() {
        return imgOriginal;
    }

    public void setImgOriginal(StringFilter imgOriginal) {
        this.imgOriginal = imgOriginal;
    }

    public StringFilter getImgLarge() {
        return imgLarge;
    }

    public void setImgLarge(StringFilter imgLarge) {
        this.imgLarge = imgLarge;
    }

    public StringFilter getImgThumbnail() {
        return imgThumbnail;
    }

    public void setImgThumbnail(StringFilter imgThumbnail) {
        this.imgThumbnail = imgThumbnail;
    }

    @Override
    public String toString() {
        return "ImageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (alt != null ? "alt=" + alt + ", " : "") +
                (caption != null ? "caption=" + caption + ", " : "") +
                (imgOriginal != null ? "imgOriginal=" + imgOriginal + ", " : "") +
                (imgLarge != null ? "imgLarge=" + imgLarge + ", " : "") +
                (imgThumbnail != null ? "imgThumbnail=" + imgThumbnail + ", " : "") +
            "}";
    }

}
