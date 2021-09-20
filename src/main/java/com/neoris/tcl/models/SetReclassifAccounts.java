package com.neoris.tcl.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the set_reclassif_accounts database table.
 * 
 */
@Entity
@Table(name = "set_reclassif_accounts")
public class SetReclassifAccounts implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4212642143691598086L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
	@SequenceGenerator(sequenceName = "SEQRECLASIF", allocationSize = 1, name = "id")
	private Long id;
	private String userid;
	private String segment1;

	@Column(name = "modified",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false)
	@Temporal(TemporalType.DATE)
	private Date modified;

	private String hfmcodeorig;
	private String hfmcodedest;
	private String costcenter;
	private String accountidini;
	private String accountidfin;
	private String partnerid;
	private int companyid;
	private String source;
	
	public SetReclassifAccounts() {
	   
	}

	
	


	public SetReclassifAccounts(Long id, String userid, String segment1, Date modified, String hfmcodeorig,
			String hfmcodedest, String costcenter, String accountidini, String accountidfin, String partnerid,
			int companyid, String source) {
		
		this.id = id;
		this.userid = userid;
		this.segment1 = segment1;
		this.modified = modified;
		this.hfmcodeorig = hfmcodeorig;
		this.hfmcodedest = hfmcodedest;
		this.costcenter = costcenter;
		this.accountidini = accountidini;
		this.accountidfin = accountidfin;
		this.partnerid = partnerid;
		this.companyid = companyid;
		this.source = source;
	}





	public String getSource() {
		return source;
	}




	public void setSource(String source) {
		this.source = source;
	}




	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getHfmcodeorig() {
		return hfmcodeorig;
	}


	public void setHfmcodeorig(String hfmcodeorig) {
		this.hfmcodeorig = hfmcodeorig;
	}


	public String getHfmcodedest() {
		return hfmcodedest;
	}


	public void setHfmcodedest(String hfmcodedest) {
		this.hfmcodedest = hfmcodedest;
	}


	public String getCostcenter() {
		return costcenter;
	}


	public void setCostcenter(String costcenter) {
		this.costcenter = costcenter;
	}


	public String getAccountidini() {
		return accountidini;
	}


	public void setAccountidini(String accountidini) {
		this.accountidini = accountidini;
	}


	public String getAccountidfin() {
		return accountidfin;
	}


	public void setAccountidfin(String accountidfin) {
		this.accountidfin = accountidfin;
	}


	public String getPartnerid() {
		return partnerid;
	}


	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}


	public int getCompanyid() {
		return companyid;
	}


	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	
	public String getSegment1() {
		return segment1;
	}

	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}


	
	
	public Date getModified() {
		return modified;
	}


	public void setModified(Date modified) {
		this.modified = modified;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetReclassifAccounts other = (SetReclassifAccounts) obj;
		return Objects.equals(id, other.id);
	}




	@Override
	public String toString() {
		return "SetReclassifAccounts [id=" + id + ", userid=" + userid + ", segment1=" + segment1 + ", modified="
				+ modified + ", hfmcodeorig=" + hfmcodeorig + ", hfmcodedest=" + hfmcodedest + ", costcenter="
				+ costcenter + ", accountidini=" + accountidini + ", accountidfin=" + accountidfin + ", partnerid="
				+ partnerid + ", companyid=" + companyid + ", source=" + source + "]";
	}




	

	

}
