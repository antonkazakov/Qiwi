package com.antonkazakov.qiwitask.data.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public class Content {

    @SerializedName("elements")
    private List<Element> elements = new ArrayList<>();

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Content{");
        sb.append("elements=").append(elements);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Content content = (Content) o;

        return elements != null ? elements.equals(content.elements) : content.elements == null;

    }

    @Override
    public int hashCode() {
        return elements != null ? elements.hashCode() : 0;
    }
}
