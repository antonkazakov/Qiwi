package com.antonkazakov.qiwitask.data.beans;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public class Validator {

    private String type;
    private Predicate predicate;
    private String message;

    public Validator(Predicate predicate) {
        this.predicate = predicate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
