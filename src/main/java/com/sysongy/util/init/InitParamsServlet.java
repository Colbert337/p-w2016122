package com.sysongy.util.init;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import com.sysongy.util.RedisClientInterface;

public class InitParamsServlet{

	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UsysparamService service;
	@Autowired
	private RedisClientInterface redis;
	
	public void initUsysparam() throws Exception{
		
		try {
			List<Usysparam> list = service.query(null, null);
			
			for (Usysparam usysparam : list) {
				String key = usysparam.getGcode() + usysparam.getMcode();
				
				redis.addToCache(key, usysparam.getMname(), -1);
				logger.info("Memocache_Usysparam key = "+ key +" value = " + usysparam.getMname());
			}
		} catch (Exception e) {
			logger.error("加载USYSPARAM缓存出错",e);
			throw e;
		}
		
	}
	
}
