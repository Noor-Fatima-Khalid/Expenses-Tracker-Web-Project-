public class Expenses {
    int id;
    int user_id;
    int category_id;
    int amount;
    String date;

    Expenses (int id, int user_id, int category_id, int amount, String date) {
        this.id = id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.amount = amount;
        this.date = date;
    }

    int getId () {
        return id;
    }
    
    int getUser_id () {
        return user_id;
    }
    
    int getCategory_id () {
        return category_id;
    }
    
    int getAmount () {
        return amount;
    }

    String getDate () {
        return date;
    }
}