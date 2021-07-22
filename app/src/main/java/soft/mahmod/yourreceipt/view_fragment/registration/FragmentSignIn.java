package soft.mahmod.yourreceipt.view_fragment.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSignIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSignIn extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }
}