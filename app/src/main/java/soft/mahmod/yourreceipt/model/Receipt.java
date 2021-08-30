package soft.mahmod.yourreceipt.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Receipt {
    private long receiptId;
    private String subject;
    private String receiptDate;
    private String clientName;
    private List<Long> itemId;
    private long clientId;
    private double totalAll;
    private int clientPhone;
    private String type;

    public Receipt() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getReceiptId() {
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

    public void setReceiptId(long receiptId) {
        this.receiptId = receiptId;
    }

    public List<Long> getItemId() {
        return itemId;
    }

    public void setItemId(List<Long> itemId) {
        this.itemId = itemId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    @NonNull
    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId='" + receiptId + '\'' +
                ", subject='" + subject + '\'' +
                ", receiptDate='" + receiptDate + '\'' +
                ", clientName='" + clientName + '\'' +
                ", totalAll='" + totalAll + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                '}';
    }
}
