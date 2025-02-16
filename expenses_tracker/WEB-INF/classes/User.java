public class User {
    int id;
    String name;
    String type;

    User(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getType() {
        return type;
    }
}
