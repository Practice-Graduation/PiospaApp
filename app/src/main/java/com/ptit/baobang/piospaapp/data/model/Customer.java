
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Customer  implements Serializable {

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

    @SerializedName("district")
    @Expose
    private District district;

    @SerializedName("email")
    @Expose
    private String email = "";

    @SerializedName("fullname")
    @Expose
    private String fullname = "";

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

    @SerializedName("province")
    @Expose
    private Province province;

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

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public String getFullAddress(){
        String fullAdress =  address;
        if(ward != null)
            fullAdress +=  ", " + ward.getType() + " " + ward.getName();
        if(district != null)
            fullAdress += ","+ district.getType() + " " + district.getName();
        if(province != null)
            fullAdress += ", "+ province.getType() + " " + province.getName();
        return fullAdress;
    }
}
