package com.it.dnc.inspy.services.representations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.it.dnc.inspy.util.JsonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dnc on 30/08/17.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "message", "messages", "details", "result"})
public class GenericResponseRepresentation<T> implements Serializable {
    private static final long serialVersionUID = 29815893532162486L;

    private String status;
    private String code;
    private String message;
    private String item;
    private transient Object details;
    private List<GenericResponseRepresentation<?>> messages;

    private transient T result;

    public GenericResponseRepresentation() {

    }

    public GenericResponseRepresentation(String code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public GenericResponseRepresentation<T> withCode(String code){
        this.setCode(code);
        return this;
    }

    public GenericResponseRepresentation<T> withMessage(String message){
        this.setMessage(message);
        return this;
    }

    public GenericResponseRepresentation<T> withItem(String item){
        this.setItem(item);
        return this;
    }

    public GenericResponseRepresentation<T> withDetails(Object details){
        this.setDetails(details);
        return this;
    }

    public GenericResponseRepresentation<T> addMessage(GenericResponseRepresentation<?> message) {
        if(this.getMessages() == null) {
            this.setMessages(new ArrayList());
        }
        this.getMessages().add(message);
        return this;
    }


    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return JsonUtil.objectToJson(this);
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<GenericResponseRepresentation<?>> getMessages() {
        return messages;
    }

    public void setMessages(List<GenericResponseRepresentation<?>> messages) {
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}


