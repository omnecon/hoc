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
 * Criteria class for the Statistic entity. This class is used in StatisticResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /statistics?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StatisticCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter noOfFag;

    private IntegerFilter noOfGalleries;

    private IntegerFilter noOfInquiries;

    private IntegerFilter noOfLinkedProvider;

    private IntegerFilter noOfPortfolioImg;

    private IntegerFilter viewsTotalWP;

    private LongFilter profileId;

    public StatisticCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getNoOfFag() {
        return noOfFag;
    }

    public void setNoOfFag(IntegerFilter noOfFag) {
        this.noOfFag = noOfFag;
    }

    public IntegerFilter getNoOfGalleries() {
        return noOfGalleries;
    }

    public void setNoOfGalleries(IntegerFilter noOfGalleries) {
        this.noOfGalleries = noOfGalleries;
    }

    public IntegerFilter getNoOfInquiries() {
        return noOfInquiries;
    }

    public void setNoOfInquiries(IntegerFilter noOfInquiries) {
        this.noOfInquiries = noOfInquiries;
    }

    public IntegerFilter getNoOfLinkedProvider() {
        return noOfLinkedProvider;
    }

    public void setNoOfLinkedProvider(IntegerFilter noOfLinkedProvider) {
        this.noOfLinkedProvider = noOfLinkedProvider;
    }

    public IntegerFilter getNoOfPortfolioImg() {
        return noOfPortfolioImg;
    }

    public void setNoOfPortfolioImg(IntegerFilter noOfPortfolioImg) {
        this.noOfPortfolioImg = noOfPortfolioImg;
    }

    public IntegerFilter getViewsTotalWP() {
        return viewsTotalWP;
    }

    public void setViewsTotalWP(IntegerFilter viewsTotalWP) {
        this.viewsTotalWP = viewsTotalWP;
    }

    public LongFilter getProfileId() {
        return profileId;
    }

    public void setProfileId(LongFilter profileId) {
        this.profileId = profileId;
    }

    @Override
    public String toString() {
        return "StatisticCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (noOfFag != null ? "noOfFag=" + noOfFag + ", " : "") +
                (noOfGalleries != null ? "noOfGalleries=" + noOfGalleries + ", " : "") +
                (noOfInquiries != null ? "noOfInquiries=" + noOfInquiries + ", " : "") +
                (noOfLinkedProvider != null ? "noOfLinkedProvider=" + noOfLinkedProvider + ", " : "") +
                (noOfPortfolioImg != null ? "noOfPortfolioImg=" + noOfPortfolioImg + ", " : "") +
                (viewsTotalWP != null ? "viewsTotalWP=" + viewsTotalWP + ", " : "") +
                (profileId != null ? "profileId=" + profileId + ", " : "") +
            "}";
    }

}
