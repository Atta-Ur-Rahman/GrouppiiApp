package com.techease.groupiiapplication.dataModel.getCreditCardResponse;

import com.google.gson.annotations.SerializedName;

public class InvoiceSettings{

	@SerializedName("footer")
	private Object footer;

	@SerializedName("custom_fields")
	private Object customFields;

	@SerializedName("default_payment_method")
	private Object defaultPaymentMethod;

	public void setFooter(Object footer){
		this.footer = footer;
	}

	public Object getFooter(){
		return footer;
	}

	public void setCustomFields(Object customFields){
		this.customFields = customFields;
	}

	public Object getCustomFields(){
		return customFields;
	}

	public void setDefaultPaymentMethod(Object defaultPaymentMethod){
		this.defaultPaymentMethod = defaultPaymentMethod;
	}

	public Object getDefaultPaymentMethod(){
		return defaultPaymentMethod;
	}
}