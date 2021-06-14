package com.techease.groupiiapplication.dataModel.tripDetial.getPaymentExpenses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalExpendituresItem {

	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("userid")
	@Expose
	private Integer userid;
	@SerializedName("tripid")
	@Expose
	private Integer tripid;
	@SerializedName("amount")
	@Expose
	private Integer amount;
	@SerializedName("source")
	@Expose
	private String source;
	@SerializedName("type_image")
	@Expose
	private String typeImage;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("date")
	@Expose
	private String date;
	@SerializedName("short_desc")
	@Expose
	private String shortDesc;
	@SerializedName("is_personal")
	@Expose
	private Integer isPersonal;
	@SerializedName("paid_by")
	@Expose
	private Integer paidBy;
	@SerializedName("created_date")
	@Expose
	private String createdDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getTripid() {
		return tripid;
	}

	public void setTripid(Integer tripid) {
		this.tripid = tripid;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTypeImage() {
		return typeImage;
	}

	public void setTypeImage(String typeImage) {
		this.typeImage = typeImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public Integer getIsPersonal() {
		return isPersonal;
	}

	public void setIsPersonal(Integer isPersonal) {
		this.isPersonal = isPersonal;
	}

	public Integer getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(Integer paidBy) {
		this.paidBy = paidBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

}
