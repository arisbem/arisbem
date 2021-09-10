package com.neoris.tcl.services;

import java.util.List;
import java.util.Map;

import javax.swing.text.ViewFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;


import com.neoris.tcl.dao.IViewRollupFFSSGconsDao;
import com.neoris.tcl.models.ViewFFSSGrouped;


@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Service()
public class ViewRollipFFSSGconsService implements IViewRollupFFSSGconsService{


	@Autowired
	private IViewRollupFFSSGconsDao data;
	
	@Override
	public List<ViewFFSSGrouped> findAll() {
		return (List<ViewFFSSGrouped>) data.findAll();
	}

	

	
	@Override
	public List<ViewFFSSGrouped> findByCompanyidAndHfmcode(String companyid, String hfmcode) {
		  return data.findByCompanyidAndHfmcode(companyid, hfmcode);
	}




	@Override
	public List<ViewFFSSGrouped> findByCompanyid(Long companyid) {
		  return data.findByCompanyid(companyid);
	}
	
	

}
