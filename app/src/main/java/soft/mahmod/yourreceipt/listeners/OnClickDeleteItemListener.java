package soft.mahmod.yourreceipt.listeners;

import soft.mahmod.yourreceipt.model.Items;

public interface OnClickDeleteItemListener<T> extends OnClickItemListener<T> {

    void onClickDeleteItem(T model, int position);


}
