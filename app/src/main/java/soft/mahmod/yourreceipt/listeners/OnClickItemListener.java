package soft.mahmod.yourreceipt.listeners;

import soft.mahmod.yourreceipt.model.Items;

public interface OnClickItemListener {
   <T extends Items> void  onClickItem(T model);
}
