package com.neoris.tcl.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

/**
 * The persistent class for the hfm_ffss_details database table.
 * 
 */
@Entity
@Table(name = "ROLLUP_VFFSSDET")
public class HfmFfssDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5676213260456508605L;

	@EmbeddedId
	private HfmFfssDetailsPK id;

	private String tpname;

	@Column(name = "HFMCODE_OLD")
	private String hfmcodeold;

	@Column(name = "BATCH_NAME")
	private String batchname;
	private String docnumber;

	private String upd;
	private String hfmparent;
	private String userid;

	@Column(name = "ERROR_TEXT")
	private String errortext;

	@Column(name = "JE_HEADER_ID")
	@ColumnDefault(value = "0")
	private int headerid;

	@Column(name = "JE_HEADER_ID_REV")
	@ColumnDefault(value = "0")
	private int headeridrev;

	private BigDecimal debit;
	private BigDecimal credit;

	@Column(name = "TRANS_DEBIT")
	private BigDecimal transdebit;

	@Column(name = "TRANS_CREDIT")
	private BigDecimal transcredit;

	@Column(name = "INVOICE_DATE")
	private String invoicedate;

	private String category;
	private String omit;
	private String areaid;

    @ColumnDefault(value = "0")
    private int num;
    @Column(name = "ICPID")
    private String icpid;
    private String posting;
    private String linetype;
    
	public HfmFfssDetails() {
		this.setId(new HfmFfssDetailsPK());
	}

	
	

	public HfmFfssDetails(HfmFfssDetailsPK id, String tpname, String hfmcodeold, String batchname, String docnumber,
			String upd, String hfmparent, String userid, String errortext, int headerid, int headeridrev,
			BigDecimal debit, BigDecimal credit, BigDecimal transdebit, BigDecimal transcredit, String invoicedate,
			String category, String omit, String areaid, int num, String icpid, String posting, String linetype) {
	
		this.id = id;
		this.tpname = tpname;
		this.hfmcodeold = hfmcodeold;
		this.batchname = batchname;
		this.docnumber = docnumber;
		this.upd = upd;
		this.hfmparent = hfmparent;
		this.userid = userid;
		this.errortext = errortext;
		this.headerid = headerid;
		this.headeridrev = headeridrev;
		this.debit = debit;
		this.credit = credit;
		this.transdebit = transdebit;
		this.transcredit = transcredit;
		this.invoicedate = invoicedate;
		this.category = category;
		this.omit = omit;
		this.areaid = areaid;
		this.num = num;
		this.icpid = icpid;
		this.posting = posting;
		this.linetype = linetype;
	}




	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public String getLinetype() {
		return linetype;
	}


	public void setLinetype(String linetype) {
		this.linetype = linetype;
	}


	public String getHfmcodeold() {
		return hfmcodeold;
	}

	public void setHfmcodeold(String hfmcodeold) {
		this.hfmcodeold = hfmcodeold;
	}

	public String getBatchname() {
		return batchname;
	}

	public void setBatchname(String batchname) {
		this.batchname = batchname;
	}

	public String getUpd() {
		return upd;
	}

	public void setUpd(String upd) {
		this.upd = upd;
	}

	public int getHeaderid() {
		return headerid;
	}

	public void setHeaderid(int headerid) {
		this.headerid = headerid;
	}

	public int getHeaderidrev() {
		return headeridrev;
	}

	public void setHeaderidrev(int headeridrev) {
		this.headeridrev = headeridrev;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public HfmFfssDetailsPK getId() {
		return id;
	}

	public void setId(HfmFfssDetailsPK id) {
		this.id = id;
	}

	public String getDocnumber() {
		return docnumber;
	}

	public void setDocnumber(String docnumber) {
		this.docnumber = docnumber;
	}

	public String getTpname() {
		return tpname;
	}

	public void setTpname(String tpname) {
		this.tpname = tpname;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getHfmparent() {
		return hfmparent;
	}

	public void setHfmparent(String hfmparent) {
		this.hfmparent = hfmparent;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public BigDecimal getTranscredit() {
		return transcredit;
	}

	public void setTranscredit(BigDecimal transcredit) {
		this.transcredit = transcredit;
	}

	public BigDecimal getTransdebit() {
		return transdebit;
	}

	public void setTransdebit(BigDecimal transdebit) {
		this.transdebit = transdebit;
	}

	public String getErrortext() {
		return errortext;
	}

	public void setErrortext(String errortext) {
		this.errortext = errortext;
	}

	public String getInvoicedate() {
		return invoicedate;
	}

	public void setInvoicedate(String invoicedate) {
		this.invoicedate = invoicedate;
	}

	public String getOmit() {
		return omit;
	}

	public void setOmit(String omit) {
		this.omit = omit;
	}

	

	public String getIcpid() {
		return icpid;
	}

	public void setIcpid(String icpid) {
		this.icpid = icpid;
	}

	public String getPosting() {
		return posting;
	}

	public void setPosting(String posting) {
		this.posting = posting;
	}




	@Override
	public String toString() {
		return "HfmFfssDetails [id=" + id + ", tpname=" + tpname + ", hfmcodeold=" + hfmcodeold + ", batchname="
				+ batchname + ", docnumber=" + docnumber + ", upd=" + upd + ", hfmparent=" + hfmparent + ", userid="
				+ userid + ", errortext=" + errortext + ", headerid=" + headerid + ", headeridrev=" + headeridrev
				+ ", debit=" + debit + ", credit=" + credit + ", transdebit=" + transdebit + ", transcredit="
				+ transcredit + ", invoicedate=" + invoicedate + ", category=" + category + ", omit=" + omit
				+ ", areaid=" + areaid + ", num=" + num + ", icpid=" + icpid + ", posting=" + posting + ", linetype="
				+ linetype + "]";
	}

	


}
