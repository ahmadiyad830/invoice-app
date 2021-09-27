package soft.mahmod.yourreceipt.listeners;

import soft.mahmod.yourreceipt.model.Client;

public interface ListenerClient extends MainListener<Client> {

    @Override
    void onClick(Client model);

    @Override
    void onEdit(Client model, int position);
}

