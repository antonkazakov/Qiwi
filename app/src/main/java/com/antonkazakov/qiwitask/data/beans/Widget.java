package com.antonkazakov.qiwitask.data.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public class Widget {

    private String type;
    private List<Choice> choices = new ArrayList<>();
    private String keyboard;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public static class Choice {
        private String value;
        private String title;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
