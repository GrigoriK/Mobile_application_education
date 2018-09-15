package Data_classes;

import Interfaces.Data;

/**
 * Created by Григорий Кисляков on 22.05.2018.
 */

public class Comments implements Data {
    private String text;
    private String url;
    private int id;
    private Boolean type;

    public Comments(int id,String text, String url, Boolean type) {
        this.text = text;
        this.url = url;
        this.id = id;
        this.type = type;
    }

    public Boolean getType() {

        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

        public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
