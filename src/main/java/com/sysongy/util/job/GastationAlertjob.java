package com.sysongy.util.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.transportion.service.TransportionService;

public class GastationAlertjob {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GastationService service;
	@Autowired
	private TransportionService transportionService;
	
	public void alertPrepayBalance(){
		try {
			logger.info("GastationAlertjob.alertPrepayBalance调度进行中..."); 
			service.alertPrepayBalance();
			transportionService.alertPrepayBalance();
			logger.info("GastationAlertjob.alertPrepayBalance调度完成...");;
		} catch (Exception e) {
			logger.error("GastationAlertjob.alertPrepayBalance调度失败..."); 
			logger.error("", e);
			e.printStackTrace();
		}
	}
}
