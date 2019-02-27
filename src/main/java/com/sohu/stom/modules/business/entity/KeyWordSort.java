package com.sohu.stom.modules.business.entity;

import com.sohu.stom.common.persistence.DataEntity;

/**
 * Created by yxf on 17/10/24.
 * 关键词排名
 */
public class KeyWordSort extends DataEntity<KeyWordSort> {
    private String appid;
    private String appname;
    private String keyword;
    private String keysort;


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeysort() {
        return keysort;
    }

    public void setKeysort(String keysort) {
        this.keysort = keysort;
    }
}
