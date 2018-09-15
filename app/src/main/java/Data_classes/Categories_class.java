package Data_classes;

import Interfaces.Data;

/**
 * Created by Григорий Кисляков on 05.05.2018.
 */

public class Categories_class implements Data {

    private int id;
    private String name;
    private String image;
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public Categories_class(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Categories_class(int id, String name,String image,String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {

        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private String Str_procesing(String st){
        String stroc="";
        

        return stroc;
    }

}
