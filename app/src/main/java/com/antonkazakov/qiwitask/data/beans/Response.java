package com.antonkazakov.qiwitask.data.beans;

import com.google.gson.annotations.SerializedName;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public class Response {

    @SerializedName("content")
    private Content content;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
