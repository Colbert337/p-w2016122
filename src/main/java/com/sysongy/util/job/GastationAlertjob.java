package com.sysongy.util.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sysongy.poms.gastation.model.ProductPrice;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.gastation.service.ProductPriceService;
import com.sysongy.poms.transportion.service.TransportionService;

public class GastationAlertjob {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GastationService service;
	@Autowired
	private TransportionService transportionService;
	@Autowired
	private ProductPriceService productPriceService;

	public void alertPrepayBalance() {
		try {
			logger.info("GastationAlertjob.alertPrepayBalance调度进行中...");

			service.alertPrepayBalance();
			transportionService.alertPrepayBalance();

			logger.info("GastationAlertjob.alertPrepayBalance调度完成...");

		} catch (Exception e) {
			logger.error("GastationAlertjob.alertPrepayBalance调度失败...");
			logger.error("", e);

			e.printStackTrace();
		}
	}

	public synchronized void updateGasUnitPrice() {
		try {
			logger.info("GastationAlertjob.updateGasUnitPrice调度进行中...");

			ProductPrice record = new ProductPrice();
			record.setStart_time_after(new Date());
			record.setProductPriceStatus("2");

			List<ProductPrice> list = productPriceService.queryProductPrice(record).getList();

			for (int i = 0; i < list.size(); i++) {
				record = list.get(i);

				Calendar recordTime = Calendar.getInstance();
				recordTime.setTime(record.getStartTime());

				Integer recordYear = recordTime.get(Calendar.YEAR);
				Integer recordMonth = recordTime.get(Calendar.MONTH) + 1;
				Integer recordDay = recordTime.get(Calendar.DAY_OF_MONTH);
				Integer recordHour = recordTime.get(Calendar.HOUR_OF_DAY);

				Calendar now = Calendar.getInstance();

				if (recordYear.equals(now.get(Calendar.YEAR)) && recordMonth.equals(now.get(Calendar.MONTH) + 1) && recordDay.equals(now.get(Calendar.DAY_OF_MONTH))&& recordHour.equals(now.get(Calendar.HOUR_OF_DAY) + 1)) {
					ProductPrice newrecord = record;

					productPriceService.updatePriceStatus(record);

					newrecord.setProductPriceStatus("1");
					productPriceService.saveProductPrice(newrecord, "update");
				}
			}

			logger.info("GastationAlertjob.updateGasUnitPrice调度完成...");
		} catch (Exception e) {
			logger.error("GastationAlertjob.updateGasUnitPrice调度失败...");
			logger.error("", e);
			
			e.printStackTrace();
		}
	}
}
