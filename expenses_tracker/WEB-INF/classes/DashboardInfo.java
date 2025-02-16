import java.io.*; 

public class DashboardInfo implements Serializable {
    int count;
    double amount;

    public DashboardInfo () {
        count = 0;
        amount = 0;
    }
    public void setCount (int count) {
        this.count = count;
    }
    public void setAmount (double amount) {
        this.amount = amount;
    }

    public int getCount () {
        return count;
    }
    public double getAmount () {
        return amount;
    }
}