package com.neoris.tcl.services;

import java.util.List;

import com.neoris.tcl.models.ViewRollupMatchFFSSHist;

public interface IViewRollupMatchFFSSHistService {

	List<ViewRollupMatchFFSSHist> findByCompanyidAndPeriodid(Long companyId,String periodnm);
}
