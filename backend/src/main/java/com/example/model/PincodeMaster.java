package com.example.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entity class representing PIN code master data
 */
public class PincodeMaster {
    
    private String pincode;
    private String officeName;
    private String officeType;
    private String deliveryStatus;
    private String divisionName;
    private String regionName;
    private String circleName;
    private String taluk;
    private String districtName;
    private String stateName;
    private String latitude;
    private String longitude;
    private String active; // "Yes" or "No"
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    
    // Default constructor
    public PincodeMaster() {
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
        this.active = "Yes"; // Default to active
    }
    
    // Constructor with essential fields
    public PincodeMaster(String pincode, String officeName, String districtName, String stateName) {
        this();
        this.pincode = pincode;
        this.officeName = officeName;
        this.districtName = districtName;
        this.stateName = stateName;
    }
    
    // Getters and Setters
    public String getPincode() {
        return pincode;
    }
    
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
    
    public String getOfficeName() {
        return officeName;
    }
    
    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }
    
    public String getOfficeType() {
        return officeType;
    }
    
    public void setOfficeType(String officeType) {
        this.officeType = officeType;
    }
    
    public String getDeliveryStatus() {
        return deliveryStatus;
    }
    
    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    
    public String getDivisionName() {
        return divisionName;
    }
    
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
    
    public String getRegionName() {
        return regionName;
    }
    
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    
    public String getCircleName() {
        return circleName;
    }
    
    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }
    
    public String getTaluk() {
        return taluk;
    }
    
    public void setTaluk(String taluk) {
        this.taluk = taluk;
    }
    
    public String getDistrictName() {
        return districtName;
    }
    
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
    
    public String getStateName() {
        return stateName;
    }
    
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
    
    public String getLatitude() {
        return latitude;
    }
    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    public String getLongitude() {
        return longitude;
    }
    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    public String getActive() {
        return active;
    }
    
    public void setActive(String active) {
        this.active = active;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
    
    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    // Utility methods
    public boolean isActive() {
        return "Yes".equalsIgnoreCase(this.active);
    }
    
    public void markAsActive() {
        this.active = "Yes";
        this.modifiedDate = LocalDateTime.now();
    }
    
    public void markAsInactive() {
        this.active = "No";
        this.modifiedDate = LocalDateTime.now();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PincodeMaster that = (PincodeMaster) o;
        return Objects.equals(pincode, that.pincode);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(pincode);
    }
    
    @Override
    public String toString() {
        return "PincodeMaster{" +
                "pincode='" + pincode + '\'' +
                ", officeName='" + officeName + '\'' +
                ", districtName='" + districtName + '\'' +
                ", stateName='" + stateName + '\'' +
                ", active='" + active + '\'' +
                '}';
    }
}