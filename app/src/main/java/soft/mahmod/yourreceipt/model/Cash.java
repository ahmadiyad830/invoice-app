package soft.mahmod.yourreceipt.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cash implements Serializable {
    @SerializedName("message")
    private String message;
    @SerializedName("error")
    private boolean error;
    @SerializedName("code")
    private int code;

    public Cash() {
    }

    public Cash(String message, boolean error, Integer code) {
        this.message = message;
        this.error = error;
        this.code = code;
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

    @Override
    public String toString() {
        return "Cash{" +
                "message='" + message + '\'' +
                ", error=" + error +
                ", code=" + code +
                '}';
    }
}
