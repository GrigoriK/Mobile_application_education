package Data_classes;

import Interfaces.Data;

/**
 * Created by Григорий Кисляков on 08.05.2018.
 */

public class Document    implements Data {
    private int id;
    private String name;
    private String code;
    private double price;
    private String author;
    private String image;
    private String post;
    private String cont_type;


    public String getDesc_text() {
        return desc_text;
    }

    private String desc_text;
    private int doc_type;

    public String getCont_type() {
        return cont_type;
    }

    public void setCont_type(String cont_type) {
        this.cont_type = cont_type;
    }

    public void setDesc_text(String desc_text) {
        this.desc_text = desc_text;
    }

    public Document(int id, String name, String desc_text, String code, double price,
                    String author, String image, String post, int doc_type, String cont_type) {
        this.id = id;
        this.cont_type=cont_type;
        this.name = name;
        this.code = code;
        this.desc_text = desc_text;
        this.price = price;
        this.author = author;
        this.image = image;
        this.post = post;
        this.doc_type = doc_type;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public void setDoc_type(int doc_type) {
        this.doc_type = doc_type;
    }

    public int getDoc_type() {

        return doc_type;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public double getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }

    public String getPost() {
        return post;
    }


}
