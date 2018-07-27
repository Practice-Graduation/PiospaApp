
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class Customer extends RealmObject implements Serializable {

    @PrimaryKey
    @RealmField(name = "customer_id")
    @SerializedName("customerId")
    @Expose
    private int customerId;

    @RealmField(name = "account")
    @SerializedName("account")
    @Expose
    private String account = "";

    @RealmField(name = "address")
    @SerializedName("address")
    @Expose
    private String address = "";

    @RealmField(name = "birthday")
    @SerializedName("birthday")
    @Expose
    private String birthday = "";

    @RealmField(name = "cmnd")
    @SerializedName("cmnd")
    @Expose
    private String cmnd = "";

    @RealmField(name = "code")
    @SerializedName("code")
    @Expose
    private String code = "";

    @RealmField(name = "created_at")
    @SerializedName("createdAt")
    @Expose
    private String createdAt = "";

    @RealmField(name = "created_by")
    @SerializedName("createdBy")
    @Expose
    private int createdBy;

    @RealmField(name = "cusomer_refer_id")
    @SerializedName("cusomerReferId")
    @Expose
    private int cusomerReferId;

    @RealmField(name = "customer_avatar")
    @SerializedName("customerAvatar")
    @Expose
    private String customerAvatar = "";

    @RealmField(name = "date_last_login")
    @SerializedName("dateLastLogin")
    @Expose
    private String dateLastLogin = "";

    @RealmField(name = "district")
    @SerializedName("district")
    @Expose
    private District district;

    @RealmField(name = "email")
    @SerializedName("email")
    @Expose
    private String email = "";

    @RealmField(name = "facebook")
    @SerializedName("facebook")
    @Expose
    private String facebook = "";

    @RealmField(name = "facebook_id")
    @SerializedName("facebookId")
    @Expose
    private int facebookId = 0;

    @RealmField(name = "fullname")
    @SerializedName("fullname")
    @Expose
    private String fullname = "";

    @RealmField(name = "gender")
    @SerializedName("gender")
    @Expose
    private String gender = "male";

    @RealmField(name = "is_active")
    @SerializedName("isActive")
    @Expose
    private int isActive = 1;

    @RealmField(name = "is_member")
    @SerializedName("isMember")
    @Expose
    private int isMember = 0;

    @RealmField(name = "member_code")
    @SerializedName("memberCode")
    @Expose
    private String memberCode = "";

    @RealmField(name = "password")
    @SerializedName("password")
    @Expose
    private String password = "";

    @RealmField(name = "phone")
    @SerializedName("phone")
    @Expose
    private String phone = "";

    @RealmField(name = "point")
    @SerializedName("point")
    @Expose
    private int point = 0;

    @RealmField(name = "point_used")
    @SerializedName("pointUsed")
    @Expose
    private int pointUsed = 0;

    @RealmField(name = "province")
    @SerializedName("province")
    @Expose
    private Province province;

    @RealmField(name = "updated_at")
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @RealmField(name = "updated_by")
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    @RealmField(name = "ward")
    @SerializedName("ward")
    @Expose
    private Ward ward;

    @RealmField(name = "zalo")
    @SerializedName("zalo")
    @Expose
    private String zalo = "";

    @RealmField(name = "customer_source")
    @SerializedName("customerSource")
    @Expose
    private int customerSource;

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
