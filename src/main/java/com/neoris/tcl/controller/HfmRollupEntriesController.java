package com.neoris.tcl.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.neoris.tcl.models.HfmRollupEntries;
import com.neoris.tcl.models.ViewOrclCompany;
import com.neoris.tcl.models.ViewSegmentCompany;
import com.neoris.tcl.services.IHfmRollupEntriesService;
import com.neoris.tcl.services.IViewOrclCompanyService;
import com.neoris.tcl.services.IViewSegmentCompanyService;
import com.neoris.tcl.utils.Functions;
import com.neoris.tcl.utils.ViewScope;

@Controller(value = "rollupentriesControllerBean")
@Scope(ViewScope.VIEW)
public class HfmRollupEntriesController {

    private final static Logger LOG = LoggerFactory.getLogger(HfmRollupEntriesController.class);

    @Autowired
    private IHfmRollupEntriesService service;

    private List<HfmRollupEntries> lstEntries;
    private List<HfmRollupEntries> lstSelectedEnt;
    private HfmRollupEntries currEntries;
    private boolean newCode;
    
    private List<ViewSegmentCompany> lstsegment;
    @Autowired
    private IViewSegmentCompanyService servseg;
    
    //companies
    private List<ViewOrclCompany> lstcomp;
    @Autowired
    private IViewOrclCompanyService  servcomp;

    @PostConstruct
    public void init() {
        LOG.info("Initializing Rollup Entries...");
        this.lstEntries = service.findAll();
        
        
		try{
			LOG.info("Initializing lstcomp...");
		
			  this.lstcomp = servcomp.findAll();

			LOG.info(" lstcomp "+this.lstcomp.size());
		}catch (Exception e) {
			LOG.error("init lstcomp ERRor -> {}", e.getMessage());
		}
		
		this.newCode =false;
		
    }

    public void openNew() {
        this.currEntries = new HfmRollupEntries();
        this.newCode =true;
       
    }

    public void save() {
        LOG.info("Entering to save Entries => {}", currEntries);

        if (this.newCode == true) {
         try {	
        	LOG.info("newcode {}",currEntries.getCompanyid().intValue());
            Optional<HfmRollupEntries> code = service.findById(currEntries.getCompanyid());
            List<HfmRollupEntries> codeentity = service.findByEntity(currEntries.getEntity());
            LOG.info("code found  {},codeentity {}",code,codeentity);
            
            if (code.isPresent() || !codeentity.isEmpty()) {
            	LOG.info("isPresent");
                String errorMessage = String.format(
                        "The record with code = %s already exist with value= %s. Can't create new record.",
                        currEntries.getCompanyid(), currEntries.getEntity());
                Functions.addErrorMessage("Error adding new Company", errorMessage);
                PrimeFaces.current().ajax().update("form:messages", "form:" + getDataTableName());
                return;
            }
            
         
        }catch (Exception e) {
			LOG.error("save-newcode ERRor -> {}", e.getMessage());
		}
      }

        currEntries = service.save(currEntries);
        this.lstEntries = service.findAll();
        Functions.addInfoMessage("Succes", "Company saved");
        PrimeFaces.current().executeScript("PF('" + getDialogName() + "').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:" + getDataTableName());
        PrimeFaces.current().executeScript("PF('dtCodes').clearFilters()");
    }

    public void delete() {
        LOG.info("Entering to delete Company => {}", this.currEntries);
        service.delete(this.currEntries);
        this.currEntries = null;
        this.lstEntries = service.findAll();
        Functions.addInfoMessage("Succes", "CompanyRemoved");
        PrimeFaces.current().ajax().update("form:messages", "form:" + getDataTableName());
        PrimeFaces.current().executeScript("PF('dtCodes').clearFilters()");
    }

    public void deleteSelected(ActionEvent event) {
        LOG.info("[deleteSelected] = > Entering to delete entires: {}", this.lstSelectedEnt);
        //service.deleteAll(this.lstSelectdHfmcodes);
        service.deleteAll(this.lstSelectedEnt);
        this.lstSelectedEnt = null;
        this.lstEntries = service.findAll();
        Functions.addInfoMessage("Succes", "Company Removed");
        PrimeFaces.current().ajax().update("form:messages", "form:" + getDataTableName());
        PrimeFaces.current().executeScript("PF('dtCodes').clearFilters()");
    }

    public void update() {
        LOG.info("Entering to update Company=> {}", currEntries);
        save();
    }

    public boolean hasSelectedCodes() {
        return this.lstSelectedEnt != null && !this.lstSelectedEnt.isEmpty();
    }

    public String getDeleteButtonMessage() {
        String message = "Delete %s entr%s selected";
        String retval = "Delete";
        if (hasSelectedCodes()) {
            int size = this.lstSelectedEnt.size();
            if (size > 1) {
                retval = String.format(message, size, "ies");
            } else {
                retval = String.format(message, size, "y");
            }
        }
        return retval;
    }

	    
	  public String getTitle() {
	        return "HFM Input Data by Company";
	    }

    public String getDialogName() {
        return "manageCodeDialog";
    }

    public String getDataTableName() {
        return "dt-codes";
    }

    public String getDeleteCodesButton() {
        return "delete-codes-button-id";
    }

    public List<HfmRollupEntries> getLstEntries() {
        return lstEntries;
    }

    public void setLstEntries(List<HfmRollupEntries> lstEntries) {
        this.lstEntries = lstEntries;
    }

    public List<HfmRollupEntries> getLstSelectedEnt() {
        return lstSelectedEnt;
    }

    public void setLstSelectedEnt(List<HfmRollupEntries> lstSelectedEnt) {
        this.lstSelectedEnt = lstSelectedEnt;
    }

    public HfmRollupEntries getCurrEntries() {
        return currEntries;
    }

    public void setCurrEntries(HfmRollupEntries currEntries) {
        this.currEntries = currEntries;
        this.newCode =false;
    }

	public List<ViewOrclCompany> getLstcomp() {
		return lstcomp;
	}

	public void setLstcomp(List<ViewOrclCompany> lstcomp) {
		this.lstcomp = lstcomp;
	}

	public List<ViewSegmentCompany> getLstSegment() {
		return lstsegment;
	}

	public void setLstSegment(List<ViewSegmentCompany> lstSegment) {
		this.lstsegment = lstSegment;
	}

    
    
}
