package com.sky.readygo.core.cglib;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wurenqing
 * @Description:
 * @Date 2017-11-30 15:27
 */
public class CGModel {
    private Map<String, Object> columnType  = new HashMap<String, Object>();

    public Map<String, Object> getColumnType() {
        return columnType;
    }

    public void setColumnType(Map<String, Object> columnType) {
        this.columnType = columnType;
    }

    private Object model;

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }
}
