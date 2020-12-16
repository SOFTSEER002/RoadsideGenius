
package com.doozycod.roadsidegenius.Model.JobList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Job implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_number")
    @Expose
    private String customerNumber;
    @SerializedName("customer_pickup")
    @Expose
    private String customerPickup;
    @SerializedName("customer_dropoff")
    @Expose
    private String customerDropoff;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("service_needed")
    @Expose
    private String serviceNeeded;
    @SerializedName("customer_notes")
    @Expose
    private String customerNotes;
    @SerializedName("amount_quoted")
    @Expose
    private String amountQuoted;
    @SerializedName("status")
    @Expose
    private String status;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerPickup() {
        return customerPickup;
    }

    public void setCustomerPickup(String customerPickup) {
        this.customerPickup = customerPickup;
    }

    public String getCustomerDropoff() {
        return customerDropoff;
    }

    public void setCustomerDropoff(String customerDropoff) {
        this.customerDropoff = customerDropoff;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getServiceNeeded() {
        return serviceNeeded;
    }

    public void setServiceNeeded(String serviceNeeded) {
        this.serviceNeeded = serviceNeeded;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }

    public String getAmountQuoted() {
        return amountQuoted;
    }

    public void setAmountQuoted(String amountQuoted) {
        this.amountQuoted = amountQuoted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(String timestamps) {
        this.timestamps = timestamps;
    }

}
