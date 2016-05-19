package com.sysongy.poms.doc.dao;

import java.util.List;

import com.sysongy.poms.doc.model.BossDoc;

/**
 * 
 * @FileName     :  BossDocMapper.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.doc.dao
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月16日, 下午3:50:06
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public interface BossDocMapper {
	
	/**
	 * 删除文档
	 * @param bossDocId
	 * @return
	 */
    int deleteByBossDocId(String bossDocId);

    int add(BossDoc bossDoc);

    /**
     * 添加系统文档
     * @param record
     * @return
     */
    int addBossDoc(BossDoc bossDoc);

    /**
     * 查询文档
     * @param bossDocId
     * @return
     */
    BossDoc queryByBossDocId(String bossDocId);

    /**
     * 修改文档
     * @param record
     * @return
     */
    int updateBossDoc(BossDoc bossDoc);

    int update(BossDoc bossDoc);
    
    /**
     * 查询所有 文档
     * @return
     */
    public List<BossDoc> queryAllBossDoc();
    
    /**
     * 根据 parentId  查询 文档信息
     * @param parentId
     * @return
     */
    public List<BossDoc> queryBossDocByParentId(String parentId);
    
    /**
     * 根据文件名称  查询是否有相同的文件夹名称
     * @param documentName
     * @return
     */
    public List<BossDoc> checkBossDocBydocumentName(String documentName);
    
    
}