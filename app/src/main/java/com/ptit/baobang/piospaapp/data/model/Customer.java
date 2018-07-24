
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("cmnd")
    @Expose
    private String cmnd = "";
    @SerializedName("code")
    @Expose
    private String code = "";
    @SerializedName("createdAt")
    @Expose
    private String createdAt = "";
    @SerializedName("createdBy")
    @Expose
    private int createdBy;
    @SerializedName("cusomerReferId")
    @Expose
    private int cusomerReferId;
    @SerializedName("customerAvatar")
    @Expose
    private String customerAvatar = "";
    @SerializedName("dateLastLogin")
    @Expose
    private String dateLastLogin = "";
    @SerializedName("district")
    @Expose
    private District district;
    @SerializedName("email")
    @Expose
    private String email = "";
    @SerializedName("facebook")
    @Expose
    private String facebook = "";
    @SerializedName("facebookId")
    @Expose
    private int facebookId = 0;
    @SerializedName("fullname")
    @Expose
    private String fullname = "";
    @SerializedName("gender")
    @Expose
    private String gender = "male";
    @SerializedName("isActive")
    @Expose
    private int isActive = 1;
    @SerializedName("isMember")
    @Expose
    private int isMember = 0;
    @SerializedName("memberCode")
    @Expose
    private String memberCode = "";
    @SerializedName("password")
    @Expose
    private String password = "";
    @SerializedName("phone")
    @Expose
    private String phone = "";
    @SerializedName("point")
    @Expose
    private int point = 0;
    @SerializedName("pointUsed")
    @Expose
    private int pointUsed = 0;
    @SerializedName("province")
    @Expose
    private Province province;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;
    @SerializedName("ward")
    @Expose
    private Ward ward;
    @SerializedName("zalo")
    @Expose
    private String zalo = "";
    @SerializedName("customerSource")
    @Expose
    private int customerSource;

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

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getCusomerReferId() {
        return cusomerReferId;
    }

    public void setCusomerReferId(int cusomerReferId) {
        this.cusomerReferId = cusomerReferId;
    }

    public String getCustomerAvatar() {
        return customerAvatar;
    }

    public void setCustomerAvatar(String customerAvatar) {
        this.customerAvatar = customerAvatar;
    }

    public String getDateLastLogin() {
        return dateLastLogin;
    }

    public void setDateLastLogin(String dateLastLogin) {
        this.dateLastLogin = dateLastLogin;
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

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public int getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(int facebookId) {
        this.facebookId = facebookId;
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

    public int getIsMember() {
        return isMember;
    }

    public void setIsMember(int isMember) {
        this.isMember = isMember;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPointUsed() {
        return pointUsed;
    }

    public void setPointUsed(int pointUsed) {
        this.pointUsed = pointUsed;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public String getZalo() {
        return zalo;
    }

    public void setZalo(String zalo) {
        this.zalo = zalo;
    }

    public int getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(int customerSource) {
        this.customerSource = customerSource;
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
