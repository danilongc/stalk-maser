package com.dnc.stalkmaster.domain;

import com.dnc.stalkmaster.util.JsonUtil;

import java.io.Serializable;

public class BaseDomain implements Serializable{

    @Override
    public String toString() {
        return JsonUtil.objectToJson(this);
    }
}
