package com.techease.groupiiapplication.dataModel.getCreditCardResponse;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("address_zip_check")
	private Object addressZipCheck;

	@SerializedName("country")
	private String country;

	@SerializedName("last4")
	private String last4;

	@SerializedName("funding")
	private String funding;

	@SerializedName("metadata")
	private Metadata metadata;

	@SerializedName("address_country")
	private Object addressCountry;

	@SerializedName("address_state")
	private Object addressState;

	@SerializedName("exp_month")
	private int expMonth;

	@SerializedName("exp_year")
	private int expYear;

	@SerializedName("address_city")
	private Object addressCity;

	@SerializedName("tokenization_method")
	private Object tokenizationMethod;

	@SerializedName("cvc_check")
	private String cvcCheck;

	@SerializedName("address_line2")
	private Object addressLine2;

	@SerializedName("address_line1")
	private Object addressLine1;

	@SerializedName("fingerprint")
	private String fingerprint;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("address_line1_check")
	private Object addressLine1Check;

	@SerializedName("address_zip")
	private Object addressZip;

	@SerializedName("dynamic_last4")
	private Object dynamicLast4;

	@SerializedName("brand")
	private String brand;

	@SerializedName("object")
	private String object;

	@SerializedName("customer")
	private String customer;

	public void setAddressZipCheck(Object addressZipCheck){
		this.addressZipCheck = addressZipCheck;
	}

	public Object getAddressZipCheck(){
		return addressZipCheck;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setLast4(String last4){
		this.last4 = last4;
	}

	public String getLast4(){
		return last4;
	}

	public void setFunding(String funding){
		this.funding = funding;
	}

	public String getFunding(){
		return funding;
	}

	public void setMetadata(Metadata metadata){
		this.metadata = metadata;
	}

	public Metadata getMetadata(){
		return metadata;
	}

	public void setAddressCountry(Object addressCountry){
		this.addressCountry = addressCountry;
	}

	public Object getAddressCountry(){
		return addressCountry;
	}

	public void setAddressState(Object addressState){
		this.addressState = addressState;
	}

	public Object getAddressState(){
		return addressState;
	}

	public void setExpMonth(int expMonth){
		this.expMonth = expMonth;
	}

	public int getExpMonth(){
		return expMonth;
	}

	public void setExpYear(int expYear){
		this.expYear = expYear;
	}

	public int getExpYear(){
		return expYear;
	}

	public void setAddressCity(Object addressCity){
		this.addressCity = addressCity;
	}

	public Object getAddressCity(){
		return addressCity;
	}

	public void setTokenizationMethod(Object tokenizationMethod){
		this.tokenizationMethod = tokenizationMethod;
	}

	public Object getTokenizationMethod(){
		return tokenizationMethod;
	}

	public void setCvcCheck(String cvcCheck){
		this.cvcCheck = cvcCheck;
	}

	public String getCvcCheck(){
		return cvcCheck;
	}

	public void setAddressLine2(Object addressLine2){
		this.addressLine2 = addressLine2;
	}

	public Object getAddressLine2(){
		return addressLine2;
	}

	public void setAddressLine1(Object addressLine1){
		this.addressLine1 = addressLine1;
	}

	public Object getAddressLine1(){
		return addressLine1;
	}

	public void setFingerprint(String fingerprint){
		this.fingerprint = fingerprint;
	}

	public String getFingerprint(){
		return fingerprint;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAddressLine1Check(Object addressLine1Check){
		this.addressLine1Check = addressLine1Check;
	}

	public Object getAddressLine1Check(){
		return addressLine1Check;
	}

	public void setAddressZip(Object addressZip){
		this.addressZip = addressZip;
	}

	public Object getAddressZip(){
		return addressZip;
	}

	public void setDynamicLast4(Object dynamicLast4){
		this.dynamicLast4 = dynamicLast4;
	}

	public Object getDynamicLast4(){
		return dynamicLast4;
	}

	public void setBrand(String brand){
		this.brand = brand;
	}

	public String getBrand(){
		return brand;
	}

	public void setObject(String object){
		this.object = object;
	}

	public String getObject(){
		return object;
	}

	public void setCustomer(String customer){
		this.customer = customer;
	}

	public String getCustomer(){
		return customer;
	}
}