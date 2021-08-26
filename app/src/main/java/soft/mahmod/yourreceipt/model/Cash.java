package soft.mahmod.yourreceipt.model;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Cash implements Serializable {
    @Exclude
    @Ignore
    private String message;
    @Exclude
    @Ignore
    private  boolean error;
    @Exclude
    @Ignore
    private  int code;
    @Exclude
    private  int id;

    public Cash() {

    }

    public Cash(String message, boolean error, Integer code) {
        this.message = message;
        this.error = error;
        this.code = code;
    }
    @Exclude
    public int getId() {
        return id;
    }
    @Exclude
    public void setId(int id) {
        this.id = id;
    }
    @Exclude
    public String getMessage() {
        return message;
    }
    @Exclude
    public boolean getError() {
        return error;
    }
    @Exclude
    public Integer getCode() {
        return code;
    }
    @Exclude
    public void setMessage(String message) {
        this.message = message;
    }
    @Exclude
    public void setError(boolean error) {
        this.error = error;
    }
    @Exclude
    public void setCode(int code) {
        this.code = code;
    }

    @NonNull
    @Override
    public String toString() {
        return "Cash{" +
                "message='" + message + '\'' +
                ", error=" + error +
                ", code=" + code +
                ", id=" + id +
                '}';
    }

    public void cleanUser() {
        this.message = null;
        this.code = 0;
    }

}
