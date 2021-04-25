package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * model for type of reimbursements
 * */
public class Type {
    private int id;
    private String value;

    public Type() {
    }


    public Type(int id) {
        String[] types = {"LODGING", "TRAVEL", "FOOD", "OTHER"};
        this.id = id;
        this.value = types[id - 1];


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
        return "Type{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
