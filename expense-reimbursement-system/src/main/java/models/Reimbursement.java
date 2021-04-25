package models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.File;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Date;

public class Reimbursement {
    private int id;
    private double amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm")
    private Date dateSubmitted;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm")
    private Date dateResolved;
    private String description;
    private byte[] receipt;
    private User author;
    private User resolver;
    private Status status;
    private Type type;

    public Reimbursement() {
    }

    public Reimbursement(double amount, User author, Type type) {
        this.amount = amount;
        this.author = author;
        this.status = new Status(1);
        this.type = type;
    }
    public Reimbursement(double amount, String description, User author, Type type) {
        this.amount = amount;
        this.description = description;
        this.author = author;
        this.status = new Status(1);
        this.type = type;
    }

    public Reimbursement(double amount, User author, Status status, Type type) {
        this.amount = amount;
        this.author = author;
        this.status = status;
        this.type = type;
    }

    public Reimbursement(double amount, String description, User author, Status status, Type type) {
        this.amount = amount;
        this.description = description;
        this.author = author;
        this.status = status;
        this.type = type;
    }

    public Reimbursement(int id, double amount, Date dateSubmitted, Date dateResolved, String description, byte[] receipt, User author, User resolver, Status status, Type type) {
        this.id = id;
        this.amount = amount;
        this.dateSubmitted = dateSubmitted;
        this.dateResolved = dateResolved;
        this.description = description;
        this.receipt = receipt;
        this.author = author;
        this.resolver = resolver;
        this.status = status;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public Date getDateResolved() {
        return dateResolved;
    }

    public void setDateResolved(Date dateResolved) {
        this.dateResolved = dateResolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getResolver() {
        return resolver;
    }

    public void setResolver(User resolver) {
        this.resolver = resolver;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", amount=" + amount +
                ", dateSubmitted=" + dateSubmitted +
                ", dateResolved=" + dateResolved +
                ", description='" + description + '\'' +
                ", receipt=" + receipt +
                ", author='" + author + '\'' +
                ", resolver='" + resolver + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
