package soft.mahmod.yourreceipt.conditions;


public interface OnError {
    boolean isNull(String input);

    boolean lengthInput(String input);

   boolean isEmpty(String input);
}
