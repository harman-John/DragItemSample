package sample.activity.channel;


public class MyBean {

    String src;
    int typeView;
    int titleType;

    public MyBean(String src, int typeView, int titleType) {
        this.src = src;
        this.typeView = typeView;
        this.titleType = titleType;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getTypeView() {
        return typeView;
    }

    public void setTypeView(int typeView) {
        this.typeView = typeView;
    }

    public void setTitleType(int type) {
        this.titleType = type;
    }

    public int getTitleType() {
        return titleType;
    }
}
