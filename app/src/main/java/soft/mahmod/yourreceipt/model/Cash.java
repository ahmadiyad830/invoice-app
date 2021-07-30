package soft.mahmod.yourreceipt.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cash implements Serializable {
    @SerializedName("message")
    private String message;
    @SerializedName("error")
    private Boolean error;
    @SerializedName("code")
    private Integer code;

    public Cash() {
    }

    public Cash(String message, Boolean error, Integer code) {
        this.message = message;
        this.error = error;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getError() {
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
