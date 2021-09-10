package com.neoris.tcl.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;

@Entity
@Subselect("select num, orgid, companyid,hfmcode,costcenternm,costcenter,oracleacct,accountnm,futureuse2,futureuse2nm ,accounttype,enabled,enddateactive from ROLLUP_VORACLEACCOUNTS")
public class HfmOracleAcc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3849211262592129052L;
	@Id
	private Long num;
	private int orgid;
	private String companyid;
	private String hfmcode;
	private String costcenternm;
	private String costcenter;
	private String oracleacct;
	private String accountnm;
	private String futureuse2;
	private String futureuse2nm;
	private String accounttype;
	private String enabled;

	@Column(name = "enddateactive", columnDefinition = "DATE")
	private LocalDate enddateactive;

	public HfmOracleAcc() {

	}

	public HfmOracleAcc(Long num, int orgid, String companyid, String hfmcode, String costcenternm, String costcenter,
			String oracleacct, String accountnm, String futureuse2, String futureuse2nm, String accounttype,
			String enabled, LocalDate enddateactive) {

		this.num = num;
		this.orgid = orgid;
		this.companyid = companyid;
		this.hfmcode = hfmcode;
		this.costcenternm = costcenternm;
		this.costcenter = costcenter;
		this.oracleacct = oracleacct;
		this.accountnm = accountnm;
		this.futureuse2 = futureuse2;
		this.futureuse2nm = futureuse2nm;
		this.accounttype = accounttype;
		this.enabled = enabled;
		this.enddateactive = enddateactive;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getHfmcode() {
		return hfmcode;
	}

	public void setHfmcode(String hfmcode) {
		this.hfmcode = hfmcode;
	}

	public String getCostcenternm() {
		return costcenternm;
	}

	public void setCostcenternm(String costcenternm) {
		this.costcenternm = costcenternm;
	}

	public String getCostcenter() {
		return costcenter;
	}

	public void setCostcenter(String costcenter) {
		this.costcenter = costcenter;
	}

	public String getOracleacct() {
		return oracleacct;
	}

	public void setOracleacct(String oracleacct) {
		this.oracleacct = oracleacct;
	}

	public String getAccountnm() {
		return accountnm;
	}

	public void setAccountnm(String accountnm) {
		this.accountnm = accountnm;
	}

	public String getFutureuse2() {
		return futureuse2;
	}

	public void setFutureuse2(String futureuse2) {
		this.futureuse2 = futureuse2;
	}

	public String getFutureuse2nm() {
		return futureuse2nm;
	}

	public void setFutureuse2nm(String futureuse2nm) {
		this.futureuse2nm = futureuse2nm;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public LocalDate getEnddateactive() {
		return enddateactive;
	}

	public void setEnddateactive(LocalDate enddateactive) {
		this.enddateactive = enddateactive;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public int getOrgid() {
		return orgid;
	}

	public void setOrgid(int orgid) {
		this.orgid = orgid;
	}

	@Override
	public String toString() {
		return "HfmOracleAcc [num=" + num + ", orgid=" + orgid + ", companyid=" + companyid + ", hfmcode=" + hfmcode
				+ ", costcenternm=" + costcenternm + ", costcenter=" + costcenter + ", oracleacct=" + oracleacct
				+ ", accountnm=" + accountnm + ", futureuse2=" + futureuse2 + ", futureuse2nm=" + futureuse2nm
				+ ", accounttype=" + accounttype + ", enabled=" + enabled + ", enddateactive=" + enddateactive + "]";
	}

}
