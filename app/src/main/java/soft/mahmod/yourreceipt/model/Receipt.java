package soft.mahmod.yourreceipt.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Receipt extends User implements Serializable {
    @SerializedName("receipt_id")
    private String receiptId;
    @SerializedName("subject")
    private String subject;
    @SerializedName("receipt_date")
    private String receiptDate;
    @SerializedName("client_name")
    private String clientName;
    @SerializedName("0")
    private List<Receipt> receiptList;
    @SerializedName("total_all")
    private String totalAll;
    @SerializedName("client_phone")
    private String clientPhone;

    public Receipt(String userId, String receiptId, String subject,
                   String receiptDate, String clientName, String totalAll,
                   String clientPhone) {
        super(userId);
        this.receiptId = receiptId;
        this.subject = subject;
        this.receiptDate = receiptDate;
        this.clientName = clientName;
        this.totalAll = totalAll;
        this.clientPhone = clientPhone;
    }

    public Receipt(String subject, String receiptDate, String clientName, List<Receipt> receiptList) {
        this.subject = subject;
        this.receiptDate = receiptDate;
        this.clientName = clientName;
        this.receiptList = receiptList;
    }

    public Receipt(String userId, String subject, String receiptDate,
                   String clientName, String totalAll, String clientPhone) {
        super(userId);
        this.subject = subject;
        this.receiptDate = receiptDate;
        this.clientName = clientName;
        this.totalAll = totalAll;
        this.clientPhone = clientPhone;
    }

    public List<Receipt> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public String getTotalAll() {
        return totalAll;
    }

    public void setTotalAll(String totalAll) {
        this.totalAll = totalAll;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
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

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId='" + receiptId + '\'' +
                ", subject='" + subject + '\'' +
                ", receiptDate='" + receiptDate + '\'' +
                ", clientName='" + clientName + '\'' +
                ", receiptList=" + receiptList +
                ", totalAll='" + totalAll + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                '}';
    }
}
