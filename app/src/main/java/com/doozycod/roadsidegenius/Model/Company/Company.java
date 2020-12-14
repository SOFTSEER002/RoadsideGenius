
package com.doozycod.roadsidegenius.Model.Company;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("company_number")
    @Expose
    private String companyNumber;
    @SerializedName("company_email")
    @Expose
    private String companyEmail;
    @SerializedName("l_9_forms")
    @Expose
    private String l9Forms;
    @SerializedName("w_9_forms")
    @Expose
    private String w9Forms;
    @SerializedName("admin_id")
    @Expose
    private String adminId;
    @SerializedName("timestamps")
    @Expose
    private String timestamps;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getL9Forms() {
        return l9Forms;
    }

    public void setL9Forms(String l9Forms) {
        this.l9Forms = l9Forms;
    }

    public String getW9Forms() {
        return w9Forms;
    }

    public void setW9Forms(String w9Forms) {
        this.w9Forms = w9Forms;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(String timestamps) {
        this.timestamps = timestamps;
    }

}
