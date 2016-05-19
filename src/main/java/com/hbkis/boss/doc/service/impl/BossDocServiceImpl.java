package com.hbkis.boss.doc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbkis.boss.doc.dao.BossDocMapper;
import com.hbkis.boss.doc.model.BossDoc;
import com.hbkis.boss.doc.service.BossDocService;

/**
 * @FileName     :  BossDocServiceImpl.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.doc.service.impl
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月16日, 下午3:52:46
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@Service
public class BossDocServiceImpl implements BossDocService{

	@Autowired
	private BossDocMapper bossDocMapper;
	
	/**
	 * 删除文档
	 * @param bossDocId
	 * @return
	 */
	@Override
	public int deleteByBossDocId(String bossDocId) {
		int flag = 0;
		if(bossDocId != null && !"".equals(bossDocId)){
			flag = bossDocMapper.deleteByBossDocId(bossDocId);
		}
		return flag;
	}

	@Override
	public int add(BossDoc bossDoc) {
		return 0;
	}

	/**
     * 添加系统文档
     * @param record
     * @return
     */
	@Override
	public int addBossDoc(BossDoc bossDoc) {
		int flag = 0;
		if(bossDoc != null && !"".equals(bossDoc)){
			flag = bossDocMapper.addBossDoc(bossDoc);
		}
		return flag;
	}

	 /**
     * 查询文档
     * @param bossDocId
     * @return
     */
	@Override
	public BossDoc queryByBossDocId(String bossDocId) {
		BossDoc bossDoc = null;
		if(bossDocId != null && !"".equals(bossDocId)){
			bossDoc = bossDocMapper.queryByBossDocId(bossDocId);
		}
		return bossDoc;
	}

	 /**
     * 修改文档
     * @param record
     * @return
     */
	@Override
	public int updateBossDoc(BossDoc bossDoc) {
		int flag = 0;
		if(bossDoc != null && !"".equals(bossDoc)){
			flag = bossDocMapper.updateBossDoc(bossDoc);
		}
		return flag;
	}

	@Override
	public int update(BossDoc bossDoc) {
		return 0;
	}

	 /**
     * 查询所有 文档
     * @return
     */
	@Override
	public List<BossDoc> queryAllBossDoc() {
		List<BossDoc> list = new ArrayList<BossDoc>();
		list = bossDocMapper.queryAllBossDoc();
		return list;
	}

	/**
     * 根据 parentId  查询 文档信息
     * @param parentId
     * @return
     */
	@Override
	public List<BossDoc> queryBossDocByParentId(String parentId) {
		List<BossDoc> list = new ArrayList<BossDoc>();
		if(parentId != null && !"".equals(parentId)){
			list = bossDocMapper.queryBossDocByParentId(parentId);
		}
		return list;
	}

	 /**
     * 根据文件名称  查询是否有相同的文件夹名称
     * @param documentName
     * @return
     */
	@Override
	public List<BossDoc> checkBossDocBydocumentName(String documentName) {
		List<BossDoc> list = new ArrayList<BossDoc>();
		if(documentName != null && !"".equals(documentName)){
			list = bossDocMapper.checkBossDocBydocumentName(documentName);
		}
		return list;
	}

}
