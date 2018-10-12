
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.io.Serializable;

public class Customer implements Serializable {

    @SerializedName("customerId")
    @Expose
    private int customerId;

    @SerializedName("account")
    @Expose
    private String account = "";

    @SerializedName("address")
    @Expose
    private String address = "";

    @SerializedName("birthday")
    @Expose
    private String birthday = "";

    @SerializedName("createdAt")
    @Expose
    private String createdAt = "";

    @SerializedName("customerAvatar")
    @Expose
    private String customerAvatar = "";

    @SerializedName("email")
    @Expose
    private String email = "";

    @SerializedName("fullname")
    @Expose
    private String fullName = "";

    @SerializedName("gender")
    @Expose
    private String gender = "male";

    @SerializedName("isActive")
    @Expose
    private int isActive = 1;

    @SerializedName("password")
    @Expose
    private String password = "";

    @SerializedName("phone")
    @Expose
    private String phone = "";

    @SerializedName("ward")
    @Expose
    private Ward ward;

    public Customer() {
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomerAvatar() {
        return customerAvatar;
    }

    public void setCustomerAvatar(String customerAvatar) {
        this.customerAvatar = customerAvatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public String getFullAddress() {
        String fullAdress = address;
        if (ward != null) {
            District district = ward.getDistrict();
            Province province = district.getProvince();
            fullAdress += AppConstants.COMMA_SYMBOL + AppConstants.SPACE_SYMBOL + ward.getType()
                        + AppConstants.SPACE_SYMBOL + ward.getName();
            fullAdress += AppConstants.COMMA_SYMBOL + AppConstants.SPACE_SYMBOL + district.getType()
                        + AppConstants.SPACE_SYMBOL + district.getName();
            fullAdress += AppConstants.COMMA_SYMBOL + AppConstants.SPACE_SYMBOL + province.getType()
                        + AppConstants.SPACE_SYMBOL + province.getName();

        }
        return fullAdress;
    }
}
