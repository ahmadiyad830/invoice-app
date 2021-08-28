package soft.mahmod.yourreceipt.repository.Database;

import java.util.List;

public interface OnRepoInsert<T> {
    void insertValue(T t, String path);

    void insertObject(T t);

    void insertList(List<T> t);
}
