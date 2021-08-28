package soft.mahmod.yourreceipt.repository.Database;

public interface OnRepoRead<T> {
    void readObject();

    void readValue(T t);
}
