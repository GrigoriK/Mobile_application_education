package Data_classes;

import Interfaces.Data;

/**
 * Created by Григорий Кисляков on 20.05.2018.
 */

public class Video implements Data {

    private String name;
    private int id;
    private final String URL="https://youtu.be/";

    public Video(String name, int id, String url) {
        this.name = name;
        this.id = id;
        Url = URL+url;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    private String Url;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public int getId() {
        return id;
    }

}
