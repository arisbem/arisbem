package com.neoris.tcl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neoris.tcl.models.HfmAccEntries;
import com.neoris.tcl.models.HfmFfss;
import com.neoris.tcl.models.HfmFfssPK;

public interface IHfmFfssDao extends JpaRepository<HfmFfss, HfmFfssPK> {
    
    /**
     * Finds a HFM_FFSS Record by Company and Period.
     * Needs to ad "Id<property>" because the field is in the Class Id.
     * find by (Id<CompanyId>) and (Id<Period>)
     * Also could work with:
     * findByHfmFfssPKCompanyIdAndHfmFfssPKPeriod(Long companyId, String period)
     * @param companyId
     * @param perid
     * @return
     */
    public List<HfmFfss> findByCompanyId(Long companyId);
    
    


	    
}