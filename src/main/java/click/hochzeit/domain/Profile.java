package click.hochzeit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import click.hochzeit.domain.enumeration.ProPackage;

import click.hochzeit.domain.enumeration.Gender;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "url")
    private String url;

    @Column(name = "branch")
    private String branch;

    @Column(name = "title")
    private String title;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "score")
    private Long score;

    @Enumerated(EnumType.STRING)
    @Column(name = "pro_package")
    private ProPackage proPackage;

    @Column(name = "lng")
    private Long lng;

    @Column(name = "lat")
    private Long lat;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "street")
    private String street;

    @Column(name = "post_code")
    private String postCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "agb_check")
    private Boolean agbCheck;

    @Column(name = "status")
    private String status;

    @Column(name = "loc_type")
    private String locType;

    @Column(name = "loc_geo")
    private String locGeo;

    @Column(name = "loc_capacity")
    private String locCapacity;

    @Column(name = "sp_available_region")
    private String spAvailableRegion;

    @Size(max = 512)
    @Column(name = "feature_str", length = 512)
    private String featureStr;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "img_title")
    private String imgTitle;

    @Column(name = "img_alt")
    private String imgAlt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Profile createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Profile lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Boolean isPublished() {
        return published;
    }

    public Profile published(Boolean published) {
        this.published = published;
        return this;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getUrl() {
        return url;
    }

    public Profile url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBranch() {
        return branch;
    }

    public Profile branch(String branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getTitle() {
        return title;
    }

    public Profile title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public Profile email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public Profile firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Profile lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Profile phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getScore() {
        return score;
    }

    public Profile score(Long score) {
        this.score = score;
        return this;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public ProPackage getProPackage() {
        return proPackage;
    }

    public Profile proPackage(ProPackage proPackage) {
        this.proPackage = proPackage;
        return this;
    }

    public void setProPackage(ProPackage proPackage) {
        this.proPackage = proPackage;
    }

    public Long getLng() {
        return lng;
    }

    public Profile lng(Long lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Long lng) {
        this.lng = lng;
    }

    public Long getLat() {
        return lat;
    }

    public Profile lat(Long lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public String getCountry() {
        return country;
    }

    public Profile country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public Profile city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Profile state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public Profile street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public Profile postCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Gender getGender() {
        return gender;
    }

    public Profile gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean isAgbCheck() {
        return agbCheck;
    }

    public Profile agbCheck(Boolean agbCheck) {
        this.agbCheck = agbCheck;
        return this;
    }

    public void setAgbCheck(Boolean agbCheck) {
        this.agbCheck = agbCheck;
    }

    public String getStatus() {
        return status;
    }

    public Profile status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocType() {
        return locType;
    }

    public Profile locType(String locType) {
        this.locType = locType;
        return this;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public String getLocGeo() {
        return locGeo;
    }

    public Profile locGeo(String locGeo) {
        this.locGeo = locGeo;
        return this;
    }

    public void setLocGeo(String locGeo) {
        this.locGeo = locGeo;
    }

    public String getLocCapacity() {
        return locCapacity;
    }

    public Profile locCapacity(String locCapacity) {
        this.locCapacity = locCapacity;
        return this;
    }

    public void setLocCapacity(String locCapacity) {
        this.locCapacity = locCapacity;
    }

    public String getSpAvailableRegion() {
        return spAvailableRegion;
    }

    public Profile spAvailableRegion(String spAvailableRegion) {
        this.spAvailableRegion = spAvailableRegion;
        return this;
    }

    public void setSpAvailableRegion(String spAvailableRegion) {
        this.spAvailableRegion = spAvailableRegion;
    }

    public String getFeatureStr() {
        return featureStr;
    }

    public Profile featureStr(String featureStr) {
        this.featureStr = featureStr;
        return this;
    }

    public void setFeatureStr(String featureStr) {
        this.featureStr = featureStr;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Profile imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgTitle() {
        return imgTitle;
    }

    public Profile imgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
        return this;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }

    public String getImgAlt() {
        return imgAlt;
    }

    public Profile imgAlt(String imgAlt) {
        this.imgAlt = imgAlt;
        return this;
    }

    public void setImgAlt(String imgAlt) {
        this.imgAlt = imgAlt;
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
        Profile profile = (Profile) o;
        if (profile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", published='" + isPublished() + "'" +
            ", url='" + getUrl() + "'" +
            ", branch='" + getBranch() + "'" +
            ", title='" + getTitle() + "'" +
            ", email='" + getEmail() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", score=" + getScore() +
            ", proPackage='" + getProPackage() + "'" +
            ", lng=" + getLng() +
            ", lat=" + getLat() +
            ", country='" + getCountry() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", street='" + getStreet() + "'" +
            ", postCode='" + getPostCode() + "'" +
            ", gender='" + getGender() + "'" +
            ", agbCheck='" + isAgbCheck() + "'" +
            ", status='" + getStatus() + "'" +
            ", locType='" + getLocType() + "'" +
            ", locGeo='" + getLocGeo() + "'" +
            ", locCapacity='" + getLocCapacity() + "'" +
            ", spAvailableRegion='" + getSpAvailableRegion() + "'" +
            ", featureStr='" + getFeatureStr() + "'" +
            ", imgUrl='" + getImgUrl() + "'" +
            ", imgTitle='" + getImgTitle() + "'" +
            ", imgAlt='" + getImgAlt() + "'" +
            "}";
    }
}
