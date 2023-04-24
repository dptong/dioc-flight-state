/**
 *
 */
package com.flywin.core.dto;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author chensihong
 */
public class SelectListDto implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 显示的文本
     */
    private String label;
    /**
     * 值1
     */
    private Object value1;
    /**
     * 值2
     */
    private Object value2;
    /**
     * 值3
     */
    private Object value3;
    /**
     * 值4
     */
    private Object value4;

    /**
     * 无参构造
     */
    public SelectListDto() {

    }

    /**
     * @param id    id
     * @param label String
     */
    public SelectListDto(String id, String label) {
        this.id = id;
        this.label = label;
    }

    /**
     * @param id    id
     * @param label Long
     */
    public SelectListDto(String id, Long label) {
        this.id = id;
        this.label = label.toString();
    }

    /**
     * @param id    id
     * @param label Integer
     */
    public SelectListDto(String id, Integer label) {
        this.id = id;
        this.label = label.toString();
    }

    /**
     * @param id    id
     * @param label Date
     */
    public SelectListDto(String id, Date label) {
        this.id = id;
        this.label = label.toString();
    }

    /**
     * @param id    id
     * @param label Character
     */
    public SelectListDto(String id, Character label) {
        this.id = id;
        this.label = label.toString();
    }

    /**
     * @param id     id
     * @param label  String
     * @param value1 Object
     */
    public SelectListDto(String id, String label, Object value1) {
        this.id = id;
        this.label = label;
        this.value1 = value1;
    }

    /**
     * @param id     id
     * @param label  String
     * @param value1 Object
     * @param value2 Object
     */
    public SelectListDto(String id, String label, Object value1, Object value2) {
        this.id = id;
        this.label = label;
        this.value1 = value1;
        this.value2 = value2;
    }

    /**
     * @param id     id
     * @param label  String
     * @param value1 Object
     * @param value2 Object
     * @param value3 Object
     */
    public SelectListDto(String id, String label, Object value1, Object value2, Object value3) {
        this.id = id;
        this.label = label;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    /**
     * @param id     id
     * @param label  String
     * @param value1 Object
     * @param value2 Object
     * @param value3 Object
     * @param value4 Object
     */
    public SelectListDto(String id, String label, Object value1, Object value2, Object value3, Object value4) {
        this.id = id;
        this.label = label;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
    }

    /**
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label 显示的文本
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return value1
     */
    public Object getValue1() {
        return value1;
    }

    /**
     * @param value1 值1
     */
    public void setValue1(Object value1) {
        this.value1 = value1;
    }

    /**
     * @return value2
     */
    public Object getValue2() {
        return value2;
    }

    /**
     * @param value2 值2
     */
    public void setValue2(Object value2) {
        this.value2 = value2;
    }

    /**
     * @return value3
     */
    public Object getValue3() {
        return value3;
    }

    /**
     * @param value3 值3
     */
    public void setValue3(Object value3) {
        this.value3 = value3;
    }

    /**
     * @return value4
     */
    public Object getValue4() {
        return value4;
    }

    /**
     * @param value4 值4
     */
    public void setValue4(Object value4) {
        this.value4 = value4;
    }

    //自己增加的

    /**
     * @param label 显示的文本
     */
    public SelectListDto(String label) {
        this.label = label;
    }


}
