package com.antonkazakov.qiwitask.data.beans;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public class Element {

    private String type;
    private String name;
    private String value;
    private Condition condition;
    private Content content;
    private Validator validator;
    private View view;

    /**
     * Parent node identifier used in {@link com.antonkazakov.qiwitask.ui.screen.WidgetGeneratorAdapter}
     * when inserting into tree.
     */
    private String parent;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        if (type != null ? !type.equals(element.type) : element.type != null) return false;
        if (name != null ? !name.equals(element.name) : element.name != null) return false;
        if (value != null ? !value.equals(element.value) : element.value != null) return false;
        if (parent != null ? !parent.equals(element.parent) : element.parent != null) return false;
        if (condition != null ? !condition.equals(element.condition) : element.condition != null)
            return false;
        if (content != null ? !content.equals(element.content) : element.content != null)
            return false;
        if (validator != null ? !validator.equals(element.validator) : element.validator != null)
            return false;
        return view != null ? view.equals(element.view) : element.view == null;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (validator != null ? validator.hashCode() : 0);
        result = 31 * result + (view != null ? view.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Element{");
        sb.append("type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", parent='").append(parent).append('\'');
        sb.append(", condition=").append(condition);
        sb.append(", content=").append(content);
        sb.append(", validator=").append(validator);
        sb.append(", view=").append(view);
        sb.append('}');
        return sb.toString();
    }

}
