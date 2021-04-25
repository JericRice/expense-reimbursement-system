package models;

/**
 * model for status of reimbursements
 * */
public class Status {
    private int id;
    private String value;

    public Status() {
    }

    public Status(int id) {
        String[] statuses = {"PENDING", "APPROVED", "DENIED"};
        this.id = id;
        this.value = statuses[id-1];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
