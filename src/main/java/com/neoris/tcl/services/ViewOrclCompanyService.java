package com.neoris.tcl.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.neoris.tcl.dao.ViewOrclCompanyDao;
import com.neoris.tcl.models.ViewOrclCompany;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Service()
public class ViewOrclCompanyService implements IViewOrclCompanyService{
	
	@Autowired
	private ViewOrclCompanyDao data;

	@Override
	public List<ViewOrclCompany> findAll() {
		return (List<ViewOrclCompany>) data.findAll();
	}
	
	


}
