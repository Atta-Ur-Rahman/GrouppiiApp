package com.techease.groupiiapplication.dataModel.getCreditCardResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetCreditCardDataModel {

	@SerializedName("metadata")
	private Metadata metadata;

	@SerializedName("subscriptions")
	private Subscriptions subscriptions;

	@SerializedName("account_balance")
	private int accountBalance;

	@SerializedName("livemode")
	private boolean livemode;

	@SerializedName("sources")
	private Sources sources;

	@SerializedName("tax_ids")
	private TaxIds taxIds;

	@SerializedName("next_invoice_sequence")
	private int nextInvoiceSequence;

	@SerializedName("description")
	private String description;

	@SerializedName("discount")
	private Object discount;

	@SerializedName("tax_info_verification")
	private Object taxInfoVerification;

	@SerializedName("preferred_locales")
	private List<Object> preferredLocales;

	@SerializedName("balance")
	private int balance;

	@SerializedName("shipping")
	private Object shipping;

	@SerializedName("delinquent")
	private boolean delinquent;

	@SerializedName("currency")
	private Object currency;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	@SerializedName("invoice_settings")
	private InvoiceSettings invoiceSettings;

	@SerializedName("address")
	private Object address;

	@SerializedName("default_source")
	private String defaultSource;

	@SerializedName("invoice_prefix")
	private String invoicePrefix;

	@SerializedName("tax_exempt")
	private String taxExempt;

	@SerializedName("created")
	private int created;

	@SerializedName("tax_info")
	private Object taxInfo;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("object")
	private String object;

	public void setMetadata(Metadata metadata){
		this.metadata = metadata;
	}

	public Metadata getMetadata(){
		return metadata;
	}

	public void setSubscriptions(Subscriptions subscriptions){
		this.subscriptions = subscriptions;
	}

	public Subscriptions getSubscriptions(){
		return subscriptions;
	}

	public void setAccountBalance(int accountBalance){
		this.accountBalance = accountBalance;
	}

	public int getAccountBalance(){
		return accountBalance;
	}

	public void setLivemode(boolean livemode){
		this.livemode = livemode;
	}

	public boolean isLivemode(){
		return livemode;
	}

	public void setSources(Sources sources){
		this.sources = sources;
	}

	public Sources getSources(){
		return sources;
	}

	public void setTaxIds(TaxIds taxIds){
		this.taxIds = taxIds;
	}

	public TaxIds getTaxIds(){
		return taxIds;
	}

	public void setNextInvoiceSequence(int nextInvoiceSequence){
		this.nextInvoiceSequence = nextInvoiceSequence;
	}

	public int getNextInvoiceSequence(){
		return nextInvoiceSequence;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setDiscount(Object discount){
		this.discount = discount;
	}

	public Object getDiscount(){
		return discount;
	}

	public void setTaxInfoVerification(Object taxInfoVerification){
		this.taxInfoVerification = taxInfoVerification;
	}

	public Object getTaxInfoVerification(){
		return taxInfoVerification;
	}

	public void setPreferredLocales(List<Object> preferredLocales){
		this.preferredLocales = preferredLocales;
	}

	public List<Object> getPreferredLocales(){
		return preferredLocales;
	}

	public void setBalance(int balance){
		this.balance = balance;
	}

	public int getBalance(){
		return balance;
	}

	public void setShipping(Object shipping){
		this.shipping = shipping;
	}

	public Object getShipping(){
		return shipping;
	}

	public void setDelinquent(boolean delinquent){
		this.delinquent = delinquent;
	}

	public boolean isDelinquent(){
		return delinquent;
	}

	public void setCurrency(Object currency){
		this.currency = currency;
	}

	public Object getCurrency(){
		return currency;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setInvoiceSettings(InvoiceSettings invoiceSettings){
		this.invoiceSettings = invoiceSettings;
	}

	public InvoiceSettings getInvoiceSettings(){
		return invoiceSettings;
	}

	public void setAddress(Object address){
		this.address = address;
	}

	public Object getAddress(){
		return address;
	}

	public void setDefaultSource(String defaultSource){
		this.defaultSource = defaultSource;
	}

	public String getDefaultSource(){
		return defaultSource;
	}

	public void setInvoicePrefix(String invoicePrefix){
		this.invoicePrefix = invoicePrefix;
	}

	public String getInvoicePrefix(){
		return invoicePrefix;
	}

	public void setTaxExempt(String taxExempt){
		this.taxExempt = taxExempt;
	}

	public String getTaxExempt(){
		return taxExempt;
	}

	public void setCreated(int created){
		this.created = created;
	}

	public int getCreated(){
		return created;
	}

	public void setTaxInfo(Object taxInfo){
		this.taxInfo = taxInfo;
	}

	public Object getTaxInfo(){
		return taxInfo;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setObject(String object){
		this.object = object;
	}

	public String getObject(){
		return object;
	}
}