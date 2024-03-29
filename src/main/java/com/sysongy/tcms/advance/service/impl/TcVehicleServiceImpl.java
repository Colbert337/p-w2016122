package com.sysongy.tcms.advance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.card.dao.GasCardMapper;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.tcms.advance.dao.TcVehicleCardMapper;
import com.sysongy.tcms.advance.dao.TcVehicleMapper;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcVehicle;
import com.sysongy.tcms.advance.model.TcVehicleCard;
import com.sysongy.tcms.advance.service.TcFleetService;
import com.sysongy.tcms.advance.service.TcVehicleService;
import com.sysongy.util.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @FileName: TcFleetServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月22日, 12:10
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class TcVehicleServiceImpl implements TcVehicleService{
    @Autowired
    TcVehicleMapper tcVehicleMapper;
    @Autowired
    TcVehicleCardMapper tcVehicleCardMapper;
    @Autowired
    GasCardMapper gasCardMapper;

    @Override
    public TcVehicle queryVehicle(TcVehicle tcVehicle) {
        if(tcVehicle != null){
            return tcVehicleMapper.queryVehicle(tcVehicle);
        }else{
            return null;
        }

    }

    @Override
    public List<TcVehicle> queryVehicleByNumber(TcVehicle tcVehicle) {
        if(tcVehicle != null){
            return tcVehicleMapper.queryVehicleByNumber(tcVehicle);
        }else{
            return null;
        }
    }

    @Override
    public PageInfo<TcVehicle> queryVehicleList(TcVehicle tcVehicle) {
        if(tcVehicle != null){
            PageHelper.startPage(tcVehicle.getPageNum(),tcVehicle.getPageSize(), tcVehicle.getOrderby());
            List<TcVehicle> vehicleList = tcVehicleMapper.queryVehicleList(tcVehicle);
            PageInfo<TcVehicle> vehiclePageInfo = new PageInfo<TcVehicle>(vehicleList);
            return vehiclePageInfo;
        }else{
            return null;
        }
    }

    @Override
    public PageInfo<Map<String, Object>> queryVehicleMapList(TcVehicle tcVehicle) {
        if(tcVehicle != null){
            PageHelper.startPage(tcVehicle.getPageNum(),tcVehicle.getPageSize());

            List<Map<String, Object>> vehicleList = tcVehicleMapper.queryVehicleMapList(tcVehicle);
            PageInfo<Map<String, Object>> vehiclePageInfo = new PageInfo<>(vehicleList);
            return vehiclePageInfo;
        }else{
            return null;
        }
    }

    @Override
    public int addVehicle(TcVehicle tcVehicle) {
        if(tcVehicle != null){
            return tcVehicleMapper.addVehicle(tcVehicle);
        }else{
            return 0;
        }
    }

    @Override
    public int addVehicleList(List<TcVehicle> tcVehicleList) {
        if(tcVehicleList != null && tcVehicleList.size() > 0){
            return tcVehicleMapper.addVehicleList(tcVehicleList);
        }else{
            return 0;
        }
    }

    @Override
    public int deleteVehicle(TcVehicle tcVehicle) {
        if(tcVehicle != null){
            return tcVehicleMapper.deleteVehicle(tcVehicle);
        }else{
            return 0;
        }
    }

    @Override
    public int updateVehicle(TcVehicle tcVehicle) {
        if(tcVehicle != null){
            return tcVehicleMapper.updateVehicle(tcVehicle);
        }else{
            return 0;
        }
    }

    @Override
    public int addVehicleCard(TcVehicleCard tcVehicleCard) {
        return tcVehicleCardMapper.addVehicleCard(tcVehicleCard);
    }

    /**
     * 根据卡号查询车辆信息
     * @param cardNo
     * @return
     */
    @Override
    public List<TcVehicle> queryVehicleByCardNo(String cardNo) {
        return tcVehicleMapper.queryVehicleByCardNo(cardNo);
    }

    /**
     * 根据运输公司编号查询车辆信息
     * @param stationId
     * @return
     */
    @Override
    public List<TcVehicle> queryVehicleByStationId(String stationId) {
        return tcVehicleMapper.queryVehicleByStationId(stationId);
    }

    @Override
    public TcVehicle queryMaxIndex(String provinceId) {
        provinceId = "%"+provinceId+"%";
        return tcVehicleMapper.queryMaxIndex(provinceId);
    }

	@Override
	public Integer updateAndchangeCard(String tcVehicleId, String newcardno) throws Exception{
		GasCard card = gasCardMapper.selectByPrimaryKey(newcardno);

		if(card == null || (!GlobalConstant.CardStatus.PROVIDE.equals(card.getCard_status()) && GlobalConstant.CARD_PROPERTY.CARD_PROPERTY_TRANSPORTION.equals(card.getCard_property()))){
			throw new Exception("该卡不存在或卡状态异常");
		}
		
		card.setCard_status(GlobalConstant.CardStatus.USED);
		gasCardMapper.updateByPrimaryKey(card);

		TcVehicle vehicle = new TcVehicle();

		vehicle.setCardNo(newcardno);
		vehicle.setTcVehicleId(tcVehicleId);

		return tcVehicleMapper.updateVehicle(vehicle);
	}

    /**
     * 查询当前运输公司系统中的车牌号是否重复
     * @param stationId
     * @param platesNumber
     * @return
     */
    @Override
    public int queryVehicleCount(String stationId, String platesNumber) {
        int resultCount = 0;
        List<TcVehicle> tcVehicleList = tcVehicleMapper.queryVehicleCount(stationId, platesNumber);
        if(tcVehicleList != null && tcVehicleList.size() > 0){
            resultCount = tcVehicleList.size();
        }

        return resultCount;
    }
}
