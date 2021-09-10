package com.neoris.tcl.dao;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.neoris.tcl.models.HfmAccEntries;

@Repository
public interface IHfmAccEntriesDao extends CrudRepository<HfmAccEntries, Id> {

	public List<HfmAccEntries> findByCompanyid(int companyid);

	@Procedure("rollup_applyentries")
	void rollUpApplyEntries(int p_orgid,  String p_userid, int p_itemid, int p_applied);
	
	//void rollUpApplyEntries(int p_orgid, String p_periodnm, String p_userid, int p_itemid, int p_applied);

}
