package soft.mahmod.yourreceipt.model;

public class Cash {
    private String message;
    private Boolean error;
    private Integer code;

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
}
