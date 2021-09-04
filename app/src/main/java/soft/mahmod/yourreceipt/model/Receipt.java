package soft.mahmod.yourreceipt.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Receipt extends Cash  {

    private String receiptId;
    private String subject;
    private String receiptDate;
    private String date, time;
    private String clientName;
    private List<Products> products;
    private String clientId;
    private double totalAll;
    private int clientPhone;
    private String type;
    private String invoice;
    private String note;

    public Receipt() {

    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public double getTotalAll() {
        return totalAll;
    }

    public void setTotalAll(double totalAll) {
        this.totalAll = totalAll;
    }

    public int getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(int clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @NonNull
    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId='" + receiptId + '\'' +
                ", subject='" + subject + '\'' +
                ", note='" + note + '\'' +
                ", receiptDate='" + receiptDate + '\'' +
                ", clientName='" + clientName + '\'' +
                "\n" + ", itemId=" + products + "\n" +
                ", clientId='" + clientId + '\'' +
                ", totalAll=" + totalAll +
                ", clientPhone=" + clientPhone +
                ", type='" + type + '\'' +
                '}';
    }
}
