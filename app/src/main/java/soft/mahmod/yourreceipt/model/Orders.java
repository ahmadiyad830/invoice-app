package soft.mahmod.yourreceipt.model;

import java.util.List;

public class Orders {
    private List<String> listIdItems;
    private String clientId;
    private String receiptId;
    private String userId;

    public Orders() {

    }

    public Orders(List<String> listIdItems, String clientId, String receiptId, String userId) {
        this.listIdItems = listIdItems;
        this.clientId = clientId;
        this.receiptId = receiptId;
        this.userId = userId;
    }

    public List<String> getListIdItems() {
        return listIdItems;
    }

    public void setListIdItems(List<String> listIdItems) {
        this.listIdItems = listIdItems;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
