package com.emart.bean;

public class SysOperationLog {

    private String rq = "";
    private String userid = "";
    private String username = "";
    private String truename = "";
    private String opttype = "";
    private String billtype = "";
    private String billnumber = "";
    private String cont = "";
    private String comment="";

	public SysOperationLog() {

    }

    public SysOperationLog(String rq, String userid, String username, String truename, String opttype, String billtype, String billnumber, String cont) {
        this.rq = rq;
        this.userid = userid;
        this.username = username;
        this.truename = truename;
        this.opttype = opttype;
        this.billtype = billtype;
        this.billnumber = billnumber;
        this.cont = cont;
    }

    public SysOperationLog(String userid, String username, String truename, String opttype, String billtype, String billnumber, String cont) {
        this.userid = userid;
        this.username = username;
        this.truename = truename;
        this.opttype = opttype;
        this.billtype = billtype;
        this.billnumber = billnumber;
        this.cont = cont;
    }
    public SysOperationLog(String rq, String userid, String username, String truename, String opttype, String billtype, String billnumber, String cont, String comment) {
        this.rq = rq;
        this.userid = userid;
        this.username = username;
        this.truename = truename;
        this.opttype = opttype;
        this.billtype = billtype;
        this.billnumber = billnumber;
        this.cont = cont;
        this.comment=comment;
    }
    
    
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getOpttype() {
        return opttype;
    }

    public void setOpttype(String opttype) {
        this.opttype = opttype;
    }

    public String getBilltype() {
        return billtype;
    }

    public void setBilltype(String billtype) {
        this.billtype = billtype;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getRq() {
        return rq;
    }

    public void setRq(String rq) {
        this.rq = rq;
    }

    public String getBillnumber() {
        return billnumber;
    }

    public void setBillnumber(String billnumber) {
        this.billnumber = billnumber;
    }
    public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
