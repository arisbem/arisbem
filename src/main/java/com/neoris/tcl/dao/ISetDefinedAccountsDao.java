package com.neoris.tcl.dao;

import java.util.List;

import javax.persistence.Id;

import org.apache.commons.codec.binary.BinaryCodec;
import org.springframework.data.repository.CrudRepository;


import com.neoris.tcl.models.SetDefinedAccounts;
import com.neoris.tcl.models.SetDefinedAccountsPK;

public interface ISetDefinedAccountsDao  extends CrudRepository<SetDefinedAccounts, Long>{
		List<SetDefinedAccounts> findByCompanyid(int companyid);
		
		//List<SetDefinedAccounts> findByCompanyId(String entity);

}
