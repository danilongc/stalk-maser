package com.dnc.stalkmaster.domain.instagram;

import com.dnc.stalkmaster.domain.BaseDomain;

public class GenericInstagramDomain<T> extends BaseDomain {
    
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
