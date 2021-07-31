package soft.mahmod.yourreceipt.adapter;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.listeners.OnSendData;
import soft.mahmod.yourreceipt.model.Receipt;


public class ViewPager2Adapter extends FragmentStateAdapter {
    private List<Fragment> listFragment = new ArrayList<>();


    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return listFragment.get(position);
    }

    public void addFragment(Fragment fragment) {
        listFragment.add(fragment);
    }


    @Override
    public int getItemCount() {
        return listFragment.size();
    }



}
