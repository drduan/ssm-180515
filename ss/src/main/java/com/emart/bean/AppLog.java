package com.emart.bean;

public class AppLog {
    private String storeCode = "";
    private String orderNumber = "";
    private String orderType = "";
    private String optType = "";
    private String userId = "";

    public AppLog() {

    }

    public AppLog(String storeCode, String orderNumber, String orderType, String optType, String userId) {
        this.storeCode = storeCode;
        this.orderNumber = orderNumber;
        this.orderType = orderType;
        this.optType = optType;
        this.userId = userId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}