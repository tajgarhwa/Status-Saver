package android.statussaver.com.statussaver.models;

public class Status {

    int id;
    String imagepath;

    public Status(int id, String imagepath) {
        this.id = id;
        this.imagepath = imagepath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
