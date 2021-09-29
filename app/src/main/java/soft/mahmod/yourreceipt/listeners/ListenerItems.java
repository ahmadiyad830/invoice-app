package soft.mahmod.yourreceipt.listeners;

import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Products;

public interface ListenerItems  {

    void onClick(Products products, Items model,int position);

    void onDelete(Items mode, int position);
}
