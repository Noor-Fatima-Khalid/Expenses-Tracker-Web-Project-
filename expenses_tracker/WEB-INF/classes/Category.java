import java.io.*;

public class Category {
    int id;
    String name;
    String limit;

    Category (int id, String name, String limit) {
        this.id = id;
        this.name = name;
        this.limit = limit;
    }

    void setId (int id) {
        this.id = id;
    }
    void setName (String name) {
        this.name = name;
    }
    void setLimit (String limit) {
        this.limit = limit;
    }

    int getId () {
        return id;
    }
    String getName () {
        return name;
    }
    String getLimit () {
        return limit;
    }
}