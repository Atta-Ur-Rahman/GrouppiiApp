package com.techease.groupiiapplication.dataModel.addPaymentExpenses;

public class AddPaymentData {
	private String date;
	private String amount;
	private String name;
	private String typeImage;
	private String tripid;
	private String shortDesc;
	private String source;
	private int id;
	private String paidBy;
	private String userid;
	private String isPersonal;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setTypeImage(String typeImage){
		this.typeImage = typeImage;
	}

	public String getTypeImage(){
		return typeImage;
	}

	public void setTripid(String tripid){
		this.tripid = tripid;
	}

	public String getTripid(){
		return tripid;
	}

	public void setShortDesc(String shortDesc){
		this.shortDesc = shortDesc;
	}

	public String getShortDesc(){
		return shortDesc;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPaidBy(String paidBy){
		this.paidBy = paidBy;
	}

	public String getPaidBy(){
		return paidBy;
	}

	public void setUserid(String userid){
		this.userid = userid;
	}

	public String getUserid(){
		return userid;
	}

	public void setIsPersonal(String isPersonal){
		this.isPersonal = isPersonal;
	}

	public String getIsPersonal(){
		return isPersonal;
	}


}
