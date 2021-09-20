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
 * The persistent class for the set_match_accounts database table.
 * 
 */
@Entity
@Table(name = "set_match_accounts")
public class SetMatchAccounts implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2750715160416744318L;


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
	@SequenceGenerator(sequenceName = "SEQMATCHACC", allocationSize = 1, name = "id")
	private Long id;
	private String sign;
	private String userid;
	@Column(name = "modified",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false)
	@Temporal(TemporalType.DATE)
	private Date modified;
	
	private String hfmchild;

	private String hfmparent;

	public SetMatchAccounts() {
	   
	}

	


	public SetMatchAccounts(Long id, String sign, String userid, Date modified, String hfmchild, String hfmparent) {
	
		this.id = id;
		this.sign = sign;
		this.userid = userid;
		this.modified = modified;
		this.hfmchild = hfmchild;
		this.hfmparent = hfmparent;
	}






	public String getHfmchild() {
		return hfmchild;
	}






	public void setHfmchild(String hfmchild) {
		this.hfmchild = hfmchild;
	}






	public String getHfmparent() {
		return hfmparent;
	}






	public void setHfmparent(String hfmparent) {
		this.hfmparent = hfmparent;
	}






	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getSign() {
		return this.sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
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



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetMatchAccounts other = (SetMatchAccounts) obj;
		return Objects.equals(id, other.id);
	}






	@Override
	public String toString() {
		return "SetMatchAccounts [id=" + id + ", sign=" + sign + ", userid=" + userid + ", modified=" + modified
				+ ", hfmchild=" + hfmchild + ", hfmparent=" + hfmparent + "]";
	}




}