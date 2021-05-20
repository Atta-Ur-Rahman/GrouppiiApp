package com.techease.groupiiapplication.dataModel.getPaymentExpenses;

public class GroupExpendituresItem{
	private String date;
	private int amount;
	private String name;
	private String typeImage;
	private int tripid;
	private int id;
	private String source;
	private String shortDesc;
	private String createdDate;
	private int paidBy;
	private int userid;
	private int isPersonal;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setAmount(int amount){
		this.amount = amount;
	}

	public int getAmount(){
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

	public void setTripid(int tripid){
		this.tripid = tripid;
	}

	public int getTripid(){
		return tripid;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	public void setShortDesc(String shortDesc){
		this.shortDesc = shortDesc;
	}

	public String getShortDesc(){
		return shortDesc;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setPaidBy(int paidBy){
		this.paidBy = paidBy;
	}

	public int getPaidBy(){
		return paidBy;
	}

	public void setUserid(int userid){
		this.userid = userid;
	}

	public int getUserid(){
		return userid;
	}

	public void setIsPersonal(int isPersonal){
		this.isPersonal = isPersonal;
	}

	public int getIsPersonal(){
		return isPersonal;
	}
}
