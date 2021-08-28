package soft.mahmod.yourreceipt.repository.Database;


import soft.mahmod.yourreceipt.model.Client;

public class RepoClient extends Repo<Client> {
    public RepoClient() {
        setPath(CLIENT);
    }

    @Override
    public void postData(Client model) {
        model.setClientId(getId());
        super.postData(model);

    }
}
