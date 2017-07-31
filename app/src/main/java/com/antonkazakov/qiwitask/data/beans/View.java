package com.antonkazakov.qiwitask.data.beans;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public class View {

    private String title;
    private String prompt;
    private Widget widget;
    /**
     * To save state of widget
     */
    private String text = "";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

}
