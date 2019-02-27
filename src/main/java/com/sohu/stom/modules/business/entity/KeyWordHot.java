package com.sohu.stom.modules.business.entity;

import com.sohu.stom.common.persistence.DataEntity;

public class KeyWordHot  extends DataEntity<KeyWordHot> {
    private String appid;
    private String keyword;
    private int keywordSort;
    private int keywordHot;


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getKeywordSort() {
        return keywordSort;
    }

    public void setKeywordSort(int keywordSort) {
        this.keywordSort = keywordSort;
    }

    public int getKeywordHot() {
        return keywordHot;
    }

    public void setKeywordHot(int keywordHot) {
        this.keywordHot = keywordHot;
    }
}
