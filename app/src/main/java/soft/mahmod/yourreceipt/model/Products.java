package soft.mahmod.yourreceipt.model;

import java.io.Serializable;
import java.util.List;

public class Products extends Receipt implements Serializable {
    public Products(String subject, String receiptDate, String clientName, List<Receipt> receiptList) {
        super(subject, receiptDate, clientName, receiptList);
    }
}
