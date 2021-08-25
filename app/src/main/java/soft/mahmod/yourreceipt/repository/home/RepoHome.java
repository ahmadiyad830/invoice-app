package soft.mahmod.yourreceipt.repository.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;

public class RepoHome implements DatabaseUrl {
    private static final String TAG = "RepoHome";
    private DatabaseReference reference;
    private FirebaseUser fUser;
    private List<Receipt> receipts;
    private MutableLiveData<List<Receipt>> data;
    private MutableLiveData<Cash> errorData;

    public RepoHome() {
        data = new MutableLiveData<>();
        errorData = new MutableLiveData<>();
        receipts = new ArrayList<>();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public void getAllReceipt() {
        reference.child(ALL_RECEIPT + fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    receipts.add(dataSnapshot.getValue(Receipt.class));
                    data.setValue(receipts);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Cash cash = new Cash();
                cash.setMessage(error.getMessage());
                cash.setCode(error.getCode());
                errorData.setValue(cash);
            }
        });
    }

    public MutableLiveData<Cash> getErrorData() {
        return errorData;
    }

    public MutableLiveData<List<Receipt>> getData() {
        return data;
    }
}
