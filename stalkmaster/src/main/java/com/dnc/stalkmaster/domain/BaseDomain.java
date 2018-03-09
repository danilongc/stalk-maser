package com.dnc.stalkmaster.domain;

import com.dnc.stalkmaster.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDomain implements Serializable{

    @Override
    public String toString() {
        return JsonUtil.objectToJson(this);
    }
}
