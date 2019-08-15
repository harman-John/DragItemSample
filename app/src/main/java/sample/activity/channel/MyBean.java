package sample.activity.channel;


public class MyBean {

    String src;
    int typeView;

    public MyBean(String src, int typeView) {
        this.src = src;
        this.typeView = typeView;
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
}
