package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentCreateProductsBinding;
import soft.mahmod.yourreceipt.model.entity.EntityProducts;
import soft.mahmod.yourreceipt.view_model.room_products.VMInsertProducts;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCreateProducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateProducts extends Fragment {
    private static final String TAG = "FragmentCreateProducts";
    private FragmentCreateProductsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_products, container, false);
        inti();
        cleanEdit();
        binding.btnDown.setOnClickListener(v -> insert());
        return binding.getRoot();
    }

    private void inti() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentCreateProductsArgs args = FragmentCreateProductsArgs.fromBundle(getArguments());
        binding.setModel(args.getItemArgs());
        Log.d(TAG, "onViewCreated: " + args.getItemArgs().toString());
    }

    private void cleanEdit() {
        binding.btnClean.setOnClickListener(v -> {
//            binding.edtName.setText("");
            binding.edtPrice.setText("");
            binding.edtQuantity.setText("");
            binding.edtDiscount.setText("");
            binding.edtTax.setText("");
//            binding.edtNote.setText("");
        });
    }

    private void insert() {
        VMInsertProducts vmInsertProducts = new ViewModelProvider(
                getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        ).get(VMInsertProducts.class);

        vmInsertProducts.insertProduct(getProducts())
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: save");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                });
        Log.d(TAG, "insert: " + getProducts().toString());
    }


    private EntityProducts getProducts() {
        String price = binding.edtPrice.getText().toString().trim();
        String quantity = binding.edtQuantity.getText().toString().trim();
        String name = binding.edtName.getText().toString().trim();
        String note = binding.edtNote.getText().toString().trim();
        String discount = binding.edtDiscount.getText().toString().trim();
        String tax = binding.edtTax.getText().toString().trim();
        EntityProducts modelProduct = new EntityProducts();

        modelProduct.setNotes(note);
        modelProduct.setItemName(name);
        if (modelProduct.isEmpty(price)){
            binding.edtPrice.setError("Input price");
        }else if (modelProduct.isEmpty(quantity)){
            binding.edtQuantity.setError("Input quantity");
        }else if (modelProduct.isEmpty(discount)){
            modelProduct.setDiscount("0");
        }else if (modelProduct.isEmpty(tax)){
            modelProduct.setTax("1");
        }
        else {

            modelProduct.setProductsPrice(price);
            modelProduct.setProductsQuantity(quantity);
            modelProduct.setDiscount(Double.parseDouble(price),Double.parseDouble(discount));
            if (Double.parseDouble(modelProduct.getDiscount())>0){
                modelProduct.setTotalProducts(Double.parseDouble(price), Double.parseDouble(quantity));
            }


        }
        return modelProduct;
    }

//        String price = binding.edtPrice.getText().toString().trim().equals("")
//                ? binding.getModel().getItemPrice() : binding.edtPrice.getText().toString().trim();
//        String quantity =binding.edtPrice.getText().toString().trim().equals("")
//                ? binding.getModel().getQuantity() : binding.edtQuantity.getText().toString().trim();
}