package soft.mahmod.yourreceipt.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Receipt   {
    private String receiptId;
    private String subject;
    private String receiptDate;
    private String clientName;
    private List<Receipt> receiptList;
    private String totalAll;
    private String clientPhone;
    private String type;
    public Receipt() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @NonNull
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
