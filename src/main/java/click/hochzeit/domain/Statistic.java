package click.hochzeit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Statistic.
 */
@Entity
@Table(name = "statistic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "statistic")
public class Statistic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "no_of_fag")
    private Integer noOfFag;

    @Column(name = "no_of_galleries")
    private Integer noOfGalleries;

    @Column(name = "no_of_inquiries")
    private Integer noOfInquiries;

    @Column(name = "no_of_linked_provider")
    private Integer noOfLinkedProvider;

    @Column(name = "no_of_portfolio_img")
    private Integer noOfPortfolioImg;

    @Column(name = "views_total_wp")
    private Integer viewsTotalWP;

    @OneToOne
    @JoinColumn(unique = true)
    private Profile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNoOfFag() {
        return noOfFag;
    }

    public Statistic noOfFag(Integer noOfFag) {
        this.noOfFag = noOfFag;
        return this;
    }

    public void setNoOfFag(Integer noOfFag) {
        this.noOfFag = noOfFag;
    }

    public Integer getNoOfGalleries() {
        return noOfGalleries;
    }

    public Statistic noOfGalleries(Integer noOfGalleries) {
        this.noOfGalleries = noOfGalleries;
        return this;
    }

    public void setNoOfGalleries(Integer noOfGalleries) {
        this.noOfGalleries = noOfGalleries;
    }

    public Integer getNoOfInquiries() {
        return noOfInquiries;
    }

    public Statistic noOfInquiries(Integer noOfInquiries) {
        this.noOfInquiries = noOfInquiries;
        return this;
    }

    public void setNoOfInquiries(Integer noOfInquiries) {
        this.noOfInquiries = noOfInquiries;
    }

    public Integer getNoOfLinkedProvider() {
        return noOfLinkedProvider;
    }

    public Statistic noOfLinkedProvider(Integer noOfLinkedProvider) {
        this.noOfLinkedProvider = noOfLinkedProvider;
        return this;
    }

    public void setNoOfLinkedProvider(Integer noOfLinkedProvider) {
        this.noOfLinkedProvider = noOfLinkedProvider;
    }

    public Integer getNoOfPortfolioImg() {
        return noOfPortfolioImg;
    }

    public Statistic noOfPortfolioImg(Integer noOfPortfolioImg) {
        this.noOfPortfolioImg = noOfPortfolioImg;
        return this;
    }

    public void setNoOfPortfolioImg(Integer noOfPortfolioImg) {
        this.noOfPortfolioImg = noOfPortfolioImg;
    }

    public Integer getViewsTotalWP() {
        return viewsTotalWP;
    }

    public Statistic viewsTotalWP(Integer viewsTotalWP) {
        this.viewsTotalWP = viewsTotalWP;
        return this;
    }

    public void setViewsTotalWP(Integer viewsTotalWP) {
        this.viewsTotalWP = viewsTotalWP;
    }

    public Profile getProfile() {
        return profile;
    }

    public Statistic profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
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
        Statistic statistic = (Statistic) o;
        if (statistic.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), statistic.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Statistic{" +
            "id=" + getId() +
            ", noOfFag=" + getNoOfFag() +
            ", noOfGalleries=" + getNoOfGalleries() +
            ", noOfInquiries=" + getNoOfInquiries() +
            ", noOfLinkedProvider=" + getNoOfLinkedProvider() +
            ", noOfPortfolioImg=" + getNoOfPortfolioImg() +
            ", viewsTotalWP=" + getViewsTotalWP() +
            "}";
    }
}
