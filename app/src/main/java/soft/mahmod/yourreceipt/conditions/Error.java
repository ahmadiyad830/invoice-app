package soft.mahmod.yourreceipt.conditions;

public abstract class Error implements OnError{
    @Override
    public boolean isNull(String input) {
        return input != null;
    }

    @Override
    public boolean isEmpty(String input) {
        return !input.isEmpty();
    }


}
