package click.hochzeit.service.dto;

import java.io.Serializable;
import click.hochzeit.domain.enumeration.ProPackage;
import click.hochzeit.domain.enumeration.Gender;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;



import io.github.jhipster.service.filter.ZonedDateTimeFilter;


/**
 * Criteria class for the Profile entity. This class is used in ProfileResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /profiles?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProfileCriteria implements Serializable {
    /**
     * Class for filtering ProPackage
     */
    public static class ProPackageFilter extends Filter<ProPackage> {
    }

    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter lastUpdatedDate;

    private BooleanFilter published;

    private StringFilter url;

    private StringFilter branch;

    private StringFilter title;

    private StringFilter email;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter phoneNumber;

    private LongFilter score;

    private ProPackageFilter proPackage;

    private LongFilter lng;

    private LongFilter lat;

    private StringFilter country;

    private StringFilter city;

    private StringFilter state;

    private StringFilter street;

    private StringFilter postCode;

    private GenderFilter gender;

    private BooleanFilter agbCheck;

    private StringFilter status;

    private StringFilter locType;

    private StringFilter locGeo;

    private StringFilter locCapacity;

    private StringFilter spAvailableRegion;

    private StringFilter featureStr;

    private LongFilter featuresId;

    public ProfileCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTimeFilter createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTimeFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTimeFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public BooleanFilter getPublished() {
        return published;
    }

    public void setPublished(BooleanFilter published) {
        this.published = published;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public StringFilter getBranch() {
        return branch;
    }

    public void setBranch(StringFilter branch) {
        this.branch = branch;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LongFilter getScore() {
        return score;
    }

    public void setScore(LongFilter score) {
        this.score = score;
    }

    public ProPackageFilter getProPackage() {
        return proPackage;
    }

    public void setProPackage(ProPackageFilter proPackage) {
        this.proPackage = proPackage;
    }

    public LongFilter getLng() {
        return lng;
    }

    public void setLng(LongFilter lng) {
        this.lng = lng;
    }

    public LongFilter getLat() {
        return lat;
    }

    public void setLat(LongFilter lat) {
        this.lat = lat;
    }

    public StringFilter getCountry() {
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getState() {
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getStreet() {
        return street;
    }

    public void setStreet(StringFilter street) {
        this.street = street;
    }

    public StringFilter getPostCode() {
        return postCode;
    }

    public void setPostCode(StringFilter postCode) {
        this.postCode = postCode;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public BooleanFilter getAgbCheck() {
        return agbCheck;
    }

    public void setAgbCheck(BooleanFilter agbCheck) {
        this.agbCheck = agbCheck;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getLocType() {
        return locType;
    }

    public void setLocType(StringFilter locType) {
        this.locType = locType;
    }

    public StringFilter getLocGeo() {
        return locGeo;
    }

    public void setLocGeo(StringFilter locGeo) {
        this.locGeo = locGeo;
    }

    public StringFilter getLocCapacity() {
        return locCapacity;
    }

    public void setLocCapacity(StringFilter locCapacity) {
        this.locCapacity = locCapacity;
    }

    public StringFilter getSpAvailableRegion() {
        return spAvailableRegion;
    }

    public void setSpAvailableRegion(StringFilter spAvailableRegion) {
        this.spAvailableRegion = spAvailableRegion;
    }

    public StringFilter getFeatureStr() {
        return featureStr;
    }

    public void setFeatureStr(StringFilter featureStr) {
        this.featureStr = featureStr;
    }

    public LongFilter getFeaturesId() {
        return featuresId;
    }

    public void setFeaturesId(LongFilter featuresId) {
        this.featuresId = featuresId;
    }

    @Override
    public String toString() {
        return "ProfileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (published != null ? "published=" + published + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (branch != null ? "branch=" + branch + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (score != null ? "score=" + score + ", " : "") +
                (proPackage != null ? "proPackage=" + proPackage + ", " : "") +
                (lng != null ? "lng=" + lng + ", " : "") +
                (lat != null ? "lat=" + lat + ", " : "") +
                (country != null ? "country=" + country + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (street != null ? "street=" + street + ", " : "") +
                (postCode != null ? "postCode=" + postCode + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (agbCheck != null ? "agbCheck=" + agbCheck + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (locType != null ? "locType=" + locType + ", " : "") +
                (locGeo != null ? "locGeo=" + locGeo + ", " : "") +
                (locCapacity != null ? "locCapacity=" + locCapacity + ", " : "") +
                (spAvailableRegion != null ? "spAvailableRegion=" + spAvailableRegion + ", " : "") +
                (featureStr != null ? "featureStr=" + featureStr + ", " : "") +
                (featuresId != null ? "featuresId=" + featuresId + ", " : "") +
            "}";
    }

}
