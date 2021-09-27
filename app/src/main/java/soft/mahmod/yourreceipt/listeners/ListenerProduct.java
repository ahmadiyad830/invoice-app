package soft.mahmod.yourreceipt.listeners;

import soft.mahmod.yourreceipt.model.Products;

public interface ListenerProduct extends MainListener<Products>{
    @Override
    void onClick(Products model);

    @Override
    void onEdit(Products model, int position);

    void onDelete(Products product,int position);
}
