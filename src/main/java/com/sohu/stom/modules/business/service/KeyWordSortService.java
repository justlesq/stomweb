package com.sohu.stom.modules.business.service;

/**
 * Created by yxf on 17/10/24.
 */

import com.sohu.stom.modules.business.dao.KeyWordSortDao;
import com.sohu.stom.modules.business.entity.KeyWordSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 排名
 * @author yxf
 * @version 2017-10-23
 */
@Service
@Transactional(readOnly = true)
public class KeyWordSortService {

    @Autowired
    private KeyWordSortDao keyWordSortDao;
    /**
     * 清空keywordSort
     */
    @Transactional(readOnly = false)
    public void trucateKeyWordSort(){
        keyWordSortDao.trucateKeyWordSort();
    }

    /**
     * 保存关键词排名
     * @param keyWordSort 关键词排名
     */
    @Transactional(readOnly = false)
    public void saveKeyWordSort(KeyWordSort keyWordSort){
        keyWordSortDao.saveKeyWordSort(keyWordSort);
    }


    /**
     * 查询当前库里排名
     * @return
     */
    public List<KeyWordSort> findKeyWordSort(KeyWordSort keyWordSort) {
        return keyWordSortDao.findKeyWordSort(keyWordSort);
    }



    public KeyWordSort getKeyWordSort(KeyWordSort keyWordSort) {
        return keyWordSortDao.getKeyWordSort(keyWordSort);
    }

    @Transactional(readOnly = false)
    public void update(KeyWordSort keyWordSort){
        keyWordSortDao.update(keyWordSort);
    }

    public List<String> findAppNameList(){return keyWordSortDao.findAppNameList();}


    public List<String> findKeyWordByAppName(String appname){return  keyWordSortDao.findKeyWordByAppName(appname);}

    public List<String> getMoniteTime(String appname, String keyword) {
        return keyWordSortDao.getMoniteTime(appname,keyword);
    }
}
