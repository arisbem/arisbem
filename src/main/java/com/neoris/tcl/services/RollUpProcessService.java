package com.neoris.tcl.services;

/*
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_ASSET;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_ASSETA;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_ASSETB;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_OTHER;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_PAYABLES;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_PAYABLESA;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_PAYABLESB;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_PAYABLES1;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_PAYABLES2;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_PAYABLES3;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_PAYABLES4;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_PAYROLL;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_PAYROLLA;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_PAYROLLB;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_RECEIVABLES;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_RECEIVABLESA;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_RECEIVABLESB;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_RECEIVABLES1;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_RECEIVABLES2;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_RECEIVABLES3;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_CONCEPT_RECEIVABLES4;
import static com.neoris.tcl.services.IHfmRollupEntriesService.P_COSTMANAGER;*/
import static com.neoris.tcl.services.IHfmRollupEntriesService.*;

import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.neoris.tcl.models.HfmRollupEntries;
import com.neoris.tcl.security.models.User;
import com.neoris.tcl.utils.ProcessRollUps;
import com.neoris.tcl.websocket.IWebSocketService;

@Service
public class RollUpProcessService implements IRollUpProcessService {

	private final static Logger LOG = LoggerFactory.getLogger(RollUpProcessService.class);
	private IWebSocketService webSocketService;
	private IHfmRollupEntriesService service;
	private User user;
	private boolean lastProcess;
	private int noproc;


	/**
	 * 
	 * @param rollUp
	 */
	@Async("threadPoolRollUpExecutor")
	public void processRollUp(HfmRollupEntries rollUp) {
		// LOG.info("store list rollup = {}", rollUp);
		// service.saveAll(this.lstRollUps);

		LOG.info("Processing Rollup Delete Data by company ");
		service.rollDelData(rollUp.getCompanyid().intValue(), rollUp.getSegment1(), rollUp.getRperiod(),
				rollUp.getRyear(), user.getUsername());

		//rollUp.setAttribute1(HfmRollupEntries.STATUS_PROCESSING);
		//rollUp.setValidations(HfmRollupEntries.STATUS_PROCESSING);
		//webSocketService.sendPushNotification(rollUp);
		webSocketService.sendPushNotification(rollUp.getCompanyid());

		// 1.- Process Drill Details
		//LOG.info("Processing Rollup Start ");
		//service.rollUpStart(rollUp.getCompanyid().intValue(), rollUp.getRperiod(), rollUp.getRyear(),rollUp.getSegment1(), user.getUsername());
		//processRollupStart(rollUp);
		//webSocketService.sendPushNotification(rollUp);

		// 1.- Process Drill Details Headers
		processDrillDetailsHd(rollUp);

		LOG.info("*******************Processing Drill Details*********************** ");
		// 2- Start balance and  Process Drill Details
		processDrillDetails(rollUp);
		
		LOG.info("*********************Processing Reclassifications*********************");
		//3
		processReclassification(rollUp);

		LOG.info("*********************Processing Validations*********************");
		// 4.- Run the validations..
		processValidations(rollUp);

		// 5.- Run Match account...
		LOG.info("*********************Processing Match ACccounts*********************");
		processMatchAccount(rollUp);

		LOG.info("**********************Finish processing rollups!!********************************");

		webSocketService.sendPushNotification(rollUp.getCompanyid());
		webSocketService.sendPushNotification("Finished company " + rollUp.getEntity(), "Sucess", "info");

		if(this.lastProcess) {
			webSocketService.sendProcessFinished();
		}

	}

	/**
	 * 
	 * @param rollUp
	 */
	private void processDrillDetailsHd(HfmRollupEntries rollUp) {
		LOG.info("Preparing Headers rollups...");

		Thread payablesThreadA = null;
		Thread receivablesThreadA = null;
		Thread costmanagerThread =null;
		Thread payrollThreadA =null;
		Thread assetsThreadA =null;

		ProcessRollUps rollUpPayablesA = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES, 0, false, false);
		ProcessRollUps rollUpReceivablesA = getProcessRollUpsInstance(rollUp, P_CONCEPT_RECEIVABLES, 0, false, false);
		ProcessRollUps rollUpCostManager = getProcessRollUpsInstance(rollUp, P_COSTMANAGER, 0, false, false);
		ProcessRollUps rollUpPayrollA = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYROLL, 0, false, false);
		ProcessRollUps rollUpAssetsA = getProcessRollUpsInstance(rollUp, P_CONCEPT_ASSET, 0, false, false);
		
			
		// 2.- Process the drills details...
		LOG.info("Preparing threads for header-rollUp process...");
		try {
			payablesThreadA = createRollUpTread(rollUpPayablesA);
			receivablesThreadA = createRollUpTread(rollUpReceivablesA);
			costmanagerThread = createRollUpTread(rollUpCostManager);
			payrollThreadA = createRollUpTread(rollUpPayrollA);
			assetsThreadA = createRollUpTread(rollUpAssetsA);
			

		} catch (Exception e) {
			LOG.error("Error creating threads: {}", e.getMessage(), e);
			webSocketService.sendPushNotification(e.getMessage(), "Error creating threads", "error", rollUp);
			return;
		}

		LOG.info("Starting Thread for header-rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES,
				rollUp.getCompanyid());
		payablesThreadA.start();
		
		
		LOG.info("Starting Thread for header-rollUp process: {}, companyId:{}", P_CONCEPT_RECEIVABLES,
				rollUp.getCompanyid());
		receivablesThreadA.start();
		LOG.info("Starting Thread for header-rollUp process: {}, companyId:{}", P_CONCEPT_PAYROLL,
				rollUp.getCompanyid());
		payrollThreadA.start();
		LOG.info("Starting Thread for header-rollUp process: {}, companyId:{}", P_CONCEPT_ASSET,
				rollUp.getCompanyid());
		assetsThreadA.start();
		
		LOG.info("** Starting  CostMngr for headers **");
		costmanagerThread.run();



		try {

			payablesThreadA.join();
			receivablesThreadA.join();
			costmanagerThread.join();
			payrollThreadA.join();
			assetsThreadA.join();
			webSocketService.sendPushNotification(rollUp.getCompanyid());

			// rollUp.setAttribute2(HfmRollupEntries.STATUS_OK);
		} catch (InterruptedException e) {
			LOG.error("Error running header process: {}", e.getMessage(), e);
			rollUp.setAttribute2(HfmRollupEntries.STATUS_ERROR);
			webSocketService.sendPushNotification(e.getMessage(), "Error Running header process", "error", rollUp);
		}
		// PrimeFaces.current().ajax().update(DT_ROLLUP);
		LOG.info("Thread for HEADER-rollUp Finish!");
	}
	

	

	/**
	 * 
	 * @param rollUp
	 */
	private void processDrillDetails(HfmRollupEntries rollUp) {
		LOG.info("Preparing concept rollups...");
		//rollUp.setAttribute2(HfmRollupEntries.STATUS_PROCESSING);
		webSocketService.sendPushNotification(rollUp);

		Thread payablesThread1 = null;
		Thread payablesThread2 = null;
		Thread payablesThread3 = null;
		Thread payablesThread4 = null;
		Thread payablesThread5 = null;
		Thread payablesThread6 = null;
		Thread payablesThread7 = null;
		Thread payablesThread8 = null;
		Thread payablesThread9 = null;
		Thread payablesThread10 = null;
		Thread payablesThread11 = null;
		Thread payablesThread12 = null;
		Thread payablesThread13 = null;
		Thread payablesThread14 = null;
		Thread receivablesThread1 = null;
		Thread receivablesThread2 = null;
		Thread receivablesThread3 = null;
		Thread receivablesThread4 = null;
		Thread receivablesThread5 = null;
		Thread receivablesThread6 = null;
		Thread receivablesThread7 = null;
		Thread receivablesThread8 = null;
		Thread payrollThread = null;
		Thread assetsThread = null;
		Thread otherThread = null;
		
		Thread drillRollUp1Tread ;
		Thread drillRollUp2Tread ;
		Thread drillRollUp3Tread ;
		Thread drillRollUp4Tread ;
		Thread drillRollUp5Tread ;
		Thread drillRollUp6Tread ;
		Thread drillRollUp7Tread ;
		Thread drillRollUp8Tread ;
		Thread drillRollUp9Tread ;
		
		
		Thread balanceThread ;
		

		ProcessRollUps rollUpBalance = getProcessRollUpsInstance(rollUp, "", -2, false, false);
		
		ProcessRollUps rollUpPayables1 = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES1, 0, false, false);
		ProcessRollUps rollUpPayables2 = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES2, 0, false, false);
		ProcessRollUps rollUpPayables3 = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES3, 0, false, false);
		ProcessRollUps rollUpPayables4 = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES4, 0, false, false);
		ProcessRollUps rollUpPayables5 = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES5, 0, false, false);
		ProcessRollUps rollUpPayables6 = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES6, 0, false, false);
		ProcessRollUps rollUpPayables7 = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES7, 0, false, false);
		ProcessRollUps rollUpPayables8 = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES8, 0, false, false);
		ProcessRollUps rollUpPayables9 = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES9, 0, false, false);
		ProcessRollUps rollUpPayables10 = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES10, 0, false, false);
		ProcessRollUps rollUpPayables11 = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES11, 0, false, false);
		ProcessRollUps rollUpPayables12 = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES12, 0, false, false);
		ProcessRollUps rollUpPayables13 = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES13, 0, false, false);
		ProcessRollUps rollUpPayables14 = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYABLES14, 0, false, false);
		ProcessRollUps rollUpReceivables1 = getProcessRollUpsInstance(rollUp, P_CONCEPT_RECEIVABLES1, 0, false, false);
		ProcessRollUps rollUpReceivables2 = getProcessRollUpsInstance(rollUp, P_CONCEPT_RECEIVABLES2, 0, false, false);
		ProcessRollUps rollUpReceivables3 = getProcessRollUpsInstance(rollUp, P_CONCEPT_RECEIVABLES3, 0, false, false);
		ProcessRollUps rollUpReceivables4 = getProcessRollUpsInstance(rollUp, P_CONCEPT_RECEIVABLES4, 0, false, false);
		ProcessRollUps rollUpReceivables5 = getProcessRollUpsInstance(rollUp, P_CONCEPT_RECEIVABLES5, 0, false, false);
		ProcessRollUps rollUpReceivables6 = getProcessRollUpsInstance(rollUp, P_CONCEPT_RECEIVABLES6, 0, false, false);
		ProcessRollUps rollUpReceivables7 = getProcessRollUpsInstance(rollUp, P_CONCEPT_RECEIVABLES7, 0, false, false);
		ProcessRollUps rollUpReceivables8 = getProcessRollUpsInstance(rollUp, P_CONCEPT_RECEIVABLES8, 0, false, false);
		ProcessRollUps rollUpPayroll = getProcessRollUpsInstance(rollUp, P_CONCEPT_PAYROLL, 0, false, false);
		ProcessRollUps rollUpAssets = getProcessRollUpsInstance(rollUp, P_CONCEPT_ASSET, 0, false, false);
		ProcessRollUps rollUpOther = getProcessRollUpsInstance(rollUp, P_CONCEPT_OTHER, 0, false, false);
		
		ProcessRollUps drillRollUp1 = getProcessRollUpsInstance(rollUp, "", 1, false, false);
		ProcessRollUps drillRollUp2 = getProcessRollUpsInstance(rollUp, "", 2, false, false);
		ProcessRollUps drillRollUp3 = getProcessRollUpsInstance(rollUp, "", 3, false, false);
		ProcessRollUps drillRollUp4 = getProcessRollUpsInstance(rollUp, "", 4, false, false);
		ProcessRollUps drillRollUp5 = getProcessRollUpsInstance(rollUp, "", 5, false, false);
		ProcessRollUps drillRollUp6 = getProcessRollUpsInstance(rollUp, "", 6, false, false);
		ProcessRollUps drillRollUp7 = getProcessRollUpsInstance(rollUp, "", 7, false, false);
		ProcessRollUps drillRollUp8 = getProcessRollUpsInstance(rollUp, "", 8, false, false);
		ProcessRollUps drillRollUp9 = getProcessRollUpsInstance(rollUp, "", 9, false, false);

		
		// 2.- Process the drills details...
		LOG.info("Preparing threads for rollUp process...");
		try {
			balanceThread = createRollUpTread(rollUpBalance);
			
			payablesThread1 = createRollUpTread(rollUpPayables1);
			payablesThread2 = createRollUpTread(rollUpPayables2);
			payablesThread3 = createRollUpTread(rollUpPayables3);
			payablesThread4 = createRollUpTread(rollUpPayables4);
			payablesThread5 = createRollUpTread(rollUpPayables5);
			payablesThread6 = createRollUpTread(rollUpPayables6);
			payablesThread7 = createRollUpTread(rollUpPayables7);
			payablesThread8 = createRollUpTread(rollUpPayables8);
			payablesThread9 = createRollUpTread(rollUpPayables9);
			payablesThread10 = createRollUpTread(rollUpPayables10);
			payablesThread11 = createRollUpTread(rollUpPayables11);
			payablesThread12 = createRollUpTread(rollUpPayables12);
			payablesThread13 = createRollUpTread(rollUpPayables13);
			payablesThread14 = createRollUpTread(rollUpPayables14);
			receivablesThread1 = createRollUpTread(rollUpReceivables1);
			receivablesThread2 = createRollUpTread(rollUpReceivables2);
			receivablesThread3 = createRollUpTread(rollUpReceivables3);
			receivablesThread4 = createRollUpTread(rollUpReceivables4);
			receivablesThread5 = createRollUpTread(rollUpReceivables5);
			receivablesThread6 = createRollUpTread(rollUpReceivables6);
			receivablesThread7 = createRollUpTread(rollUpReceivables7);
			receivablesThread8 = createRollUpTread(rollUpReceivables8);
			
			payrollThread = createRollUpTread(rollUpPayroll);
			assetsThread = createRollUpTread(rollUpAssets);
			otherThread = createRollUpTread(rollUpOther);
			
			drillRollUp1Tread = createRollUpTread(drillRollUp1);
			drillRollUp2Tread = createRollUpTread(drillRollUp2);
			drillRollUp3Tread = createRollUpTread(drillRollUp3);
			drillRollUp4Tread = createRollUpTread(drillRollUp4);
			drillRollUp5Tread = createRollUpTread(drillRollUp5);
			drillRollUp6Tread = createRollUpTread(drillRollUp6);
			drillRollUp7Tread = createRollUpTread(drillRollUp7);
			drillRollUp8Tread = createRollUpTread(drillRollUp8);
			drillRollUp9Tread = createRollUpTread(drillRollUp9);

		} catch (Exception e) {
			LOG.error("Error creating threads: {}", e.getMessage(), e);
			//rollUp.setAttribute2(HfmRollupEntries.STATUS_ERROR);
			webSocketService.sendPushNotification(e.getMessage(), "Error Creating threads", "error", rollUp);
			return;
		}

		LOG.info("Starting Thread for Trial Balance , companyId:{}", rollUp.getCompanyid());
		balanceThread.start();
		
		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES1, rollUp.getCompanyid());
		payablesThread1.start();

		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES2, rollUp.getCompanyid());
		payablesThread2.start();

		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES3, rollUp.getCompanyid());
		payablesThread3.start();

		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES4, rollUp.getCompanyid());
		payablesThread4.start();

		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES5, rollUp.getCompanyid());
		payablesThread5.start();
		
		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES6, rollUp.getCompanyid());
		payablesThread6.start();
		
		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES7, rollUp.getCompanyid());
		payablesThread7.start();
		
		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES8, rollUp.getCompanyid());
		payablesThread8.start();
		
		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES9, rollUp.getCompanyid());
		payablesThread9.start();
		
		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES10, rollUp.getCompanyid());
		payablesThread10.start();
		
		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES11, rollUp.getCompanyid());
		payablesThread11.start();
		
		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES12, rollUp.getCompanyid());
		payablesThread12.start();
		
		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES13, rollUp.getCompanyid());
		payablesThread13.start();
		
		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYABLES14, rollUp.getCompanyid());
		payablesThread14.start();

		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_RECEIVABLES1, rollUp.getCompanyid());
		receivablesThread1.start();

		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_RECEIVABLES2, rollUp.getCompanyid());
		receivablesThread2.start();

		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_RECEIVABLES3, rollUp.getCompanyid());
		receivablesThread3.start();

		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_RECEIVABLES4, rollUp.getCompanyid());
		receivablesThread4.start();
		
		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_RECEIVABLES5, rollUp.getCompanyid());
		receivablesThread5.start();
		
		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_RECEIVABLES6, rollUp.getCompanyid());
		receivablesThread6.start();
		
		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_RECEIVABLES7, rollUp.getCompanyid());
		receivablesThread7.start();
		
		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_RECEIVABLES8, rollUp.getCompanyid());
		receivablesThread8.start();
		

		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_PAYROLL, rollUp.getCompanyid());
		payrollThread.start();

		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_ASSET, rollUp.getCompanyid());
		assetsThread.start();

		LOG.info("Starting Thread for rollUp process: {}, companyId:{}", P_CONCEPT_OTHER, rollUp.getCompanyid());
		otherThread.start();

		// **Cost Manager **
		LOG.info("Starting Thread for rollUp drill process 1");
		drillRollUp1Tread.start();

		LOG.info("Starting Thread for rollUp drill process 2");
		drillRollUp2Tread.start();

		LOG.info("Starting Thread for rollUp drill process 3");
		drillRollUp3Tread.start();

		LOG.info("Starting Thread for rollUp drill process 4");
		drillRollUp4Tread.start();

		LOG.info("Starting Thread for rollUp drill process 5");
		drillRollUp5Tread.start();

		LOG.info("Starting Thread for rollUp drill process 6");
		drillRollUp6Tread.start();

		LOG.info("Starting Thread for rollUp drill process 7");
		drillRollUp7Tread.start();

		LOG.info("Starting Thread for rollUp drill process 8");
		drillRollUp8Tread.start();

		LOG.info("Starting Thread for rollUp drill process 9");
		drillRollUp9Tread.start();
		
		// 3.- wait for finish these process and start Costmanager
		try {
			balanceThread.join();
			
			payablesThread1.join();
			payablesThread2.join();
			payablesThread3.join();
			payablesThread4.join();
			payablesThread5.join();
			payablesThread6.join();
			payablesThread7.join();
			payablesThread8.join();
			payablesThread9.join();
			payablesThread10.join();
			payablesThread11.join();
			payablesThread12.join();
			payablesThread13.join();
			payablesThread14.join();
			receivablesThread1.join();
			receivablesThread2.join();
			receivablesThread3.join();
			receivablesThread4.join();
			receivablesThread5.join();
			receivablesThread6.join();
			receivablesThread7.join();
			receivablesThread8.join();
			payrollThread.join();
			assetsThread.join();
			otherThread.join();
			
			drillRollUp1Tread.join();
			drillRollUp2Tread.join();
			drillRollUp3Tread.join();
			drillRollUp4Tread.join();
			drillRollUp5Tread.join();
			drillRollUp6Tread.join();
			drillRollUp7Tread.join();
			drillRollUp8Tread.join();
			drillRollUp9Tread.join();
			
			
			
			webSocketService.sendPushNotification(rollUp.getCompanyid());
		} catch (InterruptedException e) {
			LOG.error("Error running process: {}", e.getMessage(), e);
			//rollUp.setAttribute2(HfmRollupEntries.STATUS_ERROR);
			webSocketService.sendPushNotification(e.getMessage(), "Error running Drill process", "error", rollUp);
		}
		LOG.info("****Thread for rollUp Finish!*******");
	}

 
	/**
	 * 
	 * @param rollUp
	 */
	private void processValidations(HfmRollupEntries rollUp) {
		//rollUp.setAttribute5(HfmRollupEntries.STATUS_PROCESSING);
		webSocketService.sendPushNotification(rollUp);

		ProcessRollUps rollUpValidations = getProcessRollUpsInstance(rollUp, "", 0, true, false);
		Thread validationsThread = createRollUpTread(rollUpValidations);
		validationsThread.run();

		try {
			validationsThread.join();
			webSocketService.sendPushNotification(rollUp.getCompanyid());
		} catch (InterruptedException e) {
			LOG.error("Error running Validations rollup: {}", e.getMessage(), e);
			//rollUp.setAttribute5(HfmRollupEntries.STATUS_ERROR);
			webSocketService.sendPushNotification(e.getMessage(), "Error running Validation", "error", rollUp);
		}
	}

	/**
	 * 
	 * @param rollUp
	 */
	private void processMatchAccount(HfmRollupEntries rollUp) {
		ProcessRollUps rollUpMatchAccount = getProcessRollUpsInstance(rollUp, "", 0, false, true);
		Thread matchAccountThread = createRollUpTread(rollUpMatchAccount);
		matchAccountThread.run();
		try {
			matchAccountThread.join();
			webSocketService.sendPushNotification(rollUp.getCompanyid());
		} catch (InterruptedException e) {
			LOG.error("Error running Match Account rollup: {}", e.getMessage(), e);
			//rollUp.setAttribute6(HfmRollupEntries.STATUS_ERROR);
			webSocketService.sendPushNotification(e.getMessage(), "Error running MatchAccount", "error", rollUp);
		}

	}
	
	

	/**
	 * 
	 * @param rollUp
	 */
	private void processReclassification(HfmRollupEntries rollUp) {
		ProcessRollUps rollUpReclassification = getProcessRollUpsInstance(rollUp, "", -1, false, false);
		Thread reclassificationThread = createRollUpTread(rollUpReclassification);
		reclassificationThread.run();
		try {
			reclassificationThread.join();
			webSocketService.sendPushNotification(rollUp.getCompanyid());
		} catch (InterruptedException e) {
			LOG.error("Error running reclassification rollup: {}", e.getMessage(), e);
			rollUp.setReclassifications(HfmRollupEntries.STATUS_ERROR);
			webSocketService.sendPushNotification(e.getMessage(), "Error running reclassification", "error", rollUp);
		}

	}

	/**
	 * 
	 * @param rollUp
	 * @param process
	 * @param numDrill
	 * @param processValidations
	 * @param matchAccounts
	 * @return
	 */
	private ProcessRollUps getProcessRollUpsInstance(HfmRollupEntries rollUp, String process, int numDrill,
			boolean processValidations, boolean matchAccounts) {
		ProcessRollUps rollup = new ProcessRollUps(rollUp, this.service, process, numDrill, processValidations,
				matchAccounts, this.user);
		rollup.setFacesContext(FacesContext.getCurrentInstance());
		//rollup.setPrimefaces(PrimeFaces.current());
		rollup.setWebSocketService(webSocketService);
		return rollup;
	}

	/**
	 * 
	 * @param rollup
	 * @return
	 */
	private Thread createRollUpTread(ProcessRollUps rollup) {
		LOG.info("create thread for ProcessRollUps => {}", rollup);
		Thread thead = new Thread(rollup);
		thead.setName(rollup.getProcessId());
		LOG.info("Return with thead => {}", thead);
		return thead;
	}

	@Override
	public void setWebSocketService(IWebSocketService webSocketService) {
		this.webSocketService = webSocketService;
	}

	@Override
	public void setService(IHfmRollupEntriesService service) {
		this.service = service;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setLastProcess(boolean isLast) {
		this.lastProcess = isLast;		
	}

	@Override
	public void setNoproc(int num) {
		this.noproc =num;
		
	}

	
	
}
