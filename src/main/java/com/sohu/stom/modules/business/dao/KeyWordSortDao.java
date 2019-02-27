package com.sohu.stom.modules.business.dao;

import com.sohu.stom.common.persistence.CrudDao;
import com.sohu.stom.common.persistence.annotation.MyBatisDao;
import com.sohu.stom.modules.business.entity.KeyWordSort;

import java.util.List;

/**
 * Created by yxf on 17/10/24.
 */
@MyBatisDao
public interface KeyWordSortDao extends CrudDao<KeyWordSort> {
    /**
     * 清空keyWordSort
     */
    public void trucateKeyWordSort();

    /**
     * 保存关键词排名
     */
    public void saveKeyWordSort(KeyWordSort keyWordSort);

    /**
     * 查询关键词排名
     * @return
     */
    List<KeyWordSort> findKeyWordSort(KeyWordSort keyWordSort);


    /**
     * 根据appid 和 关键词查询是否有已有排名
     * @return
     */
    KeyWordSort getKeyWordSort(KeyWordSort keyWordSort);


    List<String> findAppNameList();


    List<String> findKeyWordByAppName(String appname);


    List<String> getMoniteTime(String appname,String keyword);
}
