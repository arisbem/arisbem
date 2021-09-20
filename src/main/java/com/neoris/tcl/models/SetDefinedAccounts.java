package com.neoris.tcl.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name = "set_defined_accounts")
public class SetDefinedAccounts implements Serializable {
	private static final long serialVersionUID = 6102853690474970823L;

//	@EmbeddedId
//	private SetDefinedAccountsPK id;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
	@SequenceGenerator(sequenceName = "SEQDEFACC", allocationSize = 1, name = "id")
	private Long id;
	private String icpcode;
	private String userid;
	private String cperiod;
	
	private String accountid;
	private String accountidfin;
	private int companyid;
	private String costcenter;
	private String source;
    
	@Column(name = "modified",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false)
	@Temporal(TemporalType.DATE)
	private Date modified;

	@Transient
	private String uuid;

	public SetDefinedAccounts() {
		//this.setId(new SetDefinedAccountsPK());
		this.uuid = UUID.randomUUID().toString();
	}



	public SetDefinedAccounts(Long id, String icpcode, String userid, String cperiod, String accountid,
			String accountidfin, int companyid, String costcenter, String source, Date modified, String uuid) {
		
		this.id = id;
		this.icpcode = icpcode;
		this.userid = userid;
		this.cperiod = cperiod;
		this.accountid = accountid;
		this.accountidfin = accountidfin;
		this.companyid = companyid;
		this.costcenter = costcenter;
		this.source = source;
		this.modified = modified;
		this.uuid = uuid;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIcpcode() {
		return icpcode;
	}

	public void setIcpcode(String icpcode) {
		this.icpcode = icpcode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCperiod() {
		return cperiod;
	}

	public void setCperiod(String cperiod) {
		this.cperiod = cperiod;
	}

	public String getUuid() {
		return uuid;
	}
	


	public Date getModified() {

		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
	

	

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getAccountidfin() {
		return accountidfin;
	}

	public void setAccountidfin(String accountidfin) {
		this.accountidfin = accountidfin;
	}

	public int getCompanyid() {
		return companyid;
	}

	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}

	public String getCostcenter() {
		return costcenter;
	}

	public void setCostcenter(String costcenter) {
		this.costcenter = costcenter;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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
		SetDefinedAccounts other = (SetDefinedAccounts) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "SetDefinedAccounts [id=" + id + ", icpcode=" + icpcode + ", userid=" + userid + ", cperiod=" + cperiod
				+ ", accountid=" + accountid + ", accountidfin=" + accountidfin + ", companyid=" + companyid
				+ ", costcenter=" + costcenter + ", source=" + source + ", modified=" + modified + ", uuid=" + uuid
				+ "]";
	}

	
	

}
