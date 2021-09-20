package com.neoris.tcl.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "hfm_rollup_exceptions")
public class HfmRollupExceptions implements Serializable {

	private static final long serialVersionUID = 7111822966653268829L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
	@SequenceGenerator(sequenceName = "SEQMEXCEPT", allocationSize = 1, name = "id")
	private Long id;
	private String userid;
	private String segment1;
	@Column(name = "modified",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false)
	@Temporal(TemporalType.DATE)
	private Date modified;

	
	private int partnerid;
	private String costcenter;
	private String accountid;
	private int companyid;
	private String source;

	

	public HfmRollupExceptions() {
		
	}

	


	public HfmRollupExceptions(Long id, String userid, String segment1, Date modified, int partnerid, String costcenter,
			String accountid, int companyid, String source) {
		
		this.id = id;
		this.userid = userid;
		this.segment1 = segment1;
		this.modified = modified;
		this.partnerid = partnerid;
		this.costcenter = costcenter;
		this.accountid = accountid;
		this.companyid = companyid;
		this.source = source;
	}




	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public int getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(int partnerid) {
		this.partnerid = partnerid;
	}

	public String getCostcenter() {
		return costcenter;
	}

	public void setCostcenter(String costcenter) {
		this.costcenter = costcenter;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public int getCompanyid() {
		return companyid;
	}

	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}

	public String getSegment1() {
		return segment1;
	}
	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
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


	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HfmRollupExceptions other = (HfmRollupExceptions) obj;
		return Objects.equals(id, other.id);
	}




	@Override
	public String toString() {
		return "HfmRollupExceptions [id=" + id + ", userid=" + userid + ", segment1=" + segment1 + ", modified="
				+ modified + ", partnerid=" + partnerid + ", costcenter=" + costcenter + ", accountid=" + accountid
				+ ", companyid=" + companyid + ", source=" + source + "]";
	}




	
}
