package soft.mahmod.yourreceipt.model;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cash implements Serializable {
    @Ignore
    @SerializedName("message")
    private String message;
    @Ignore
    @SerializedName("error")
    private boolean error;
    @Ignore
    @SerializedName("code")
    private int code;
    @SerializedName("id")
    private int id;

    public Cash() {
    }

    public Cash(String message, boolean error, Integer code) {
        this.message = message;
        this.error = error;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public boolean getError() {
        return error;
    }

    public Integer getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

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
