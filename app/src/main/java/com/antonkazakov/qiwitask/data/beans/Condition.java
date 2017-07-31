package com.antonkazakov.qiwitask.data.beans;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public class Condition {

    private String type;
    private String field;
    private Predicate predicate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }



}
