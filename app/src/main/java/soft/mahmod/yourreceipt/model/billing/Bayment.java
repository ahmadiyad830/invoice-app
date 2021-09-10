package soft.mahmod.yourreceipt.model.billing;

import java.util.List;

interface Bayment {
    void setBayments(List<String> bayments);

    List<String> getBayments();
}
