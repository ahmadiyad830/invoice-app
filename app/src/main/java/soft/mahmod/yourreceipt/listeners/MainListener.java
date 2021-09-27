package soft.mahmod.yourreceipt.listeners;

public interface MainListener<T> {
    void onClick(T model);

    void onEdit(T model,int position);


}
