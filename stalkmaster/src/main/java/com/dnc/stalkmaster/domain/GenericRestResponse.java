package com.dnc.stalkmaster.domain;

import com.dnc.stalkmaster.util.JsonUtil;

import java.io.Serializable;

public class GenericRestResponse implements Serializable {

    private String code;
    private String message;
    private Object data;

    public GenericRestResponse(){}

    public GenericRestResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public GenericRestResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public GenericRestResponse withCode(String code) {
        this.code = code;
        return this;
    }

    public GenericRestResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public GenericRestResponse withData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JsonUtil.objectToJson(this);
    }

}
