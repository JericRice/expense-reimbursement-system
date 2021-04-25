package models;

/**
 * model for roles of users
 * */
public class UserRole {
    private int id;
    private String value;

    public UserRole() {
    }

    public UserRole(int id) {
        String[] roles = {"EMPLOYEE", "MANAGER"};
        this.id = id;
        this.value = roles[id-1];
    }

    public UserRole(int id, String value) {
        this.id = id;
        this.value = value;
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
        return "UserRole{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
