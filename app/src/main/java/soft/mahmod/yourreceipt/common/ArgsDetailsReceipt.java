package soft.mahmod.yourreceipt.common;

import soft.mahmod.yourreceipt.model.Receipt;

public class ArgsDetailsReceipt {
    private Receipt model;
    private static ArgsDetailsReceipt instance ;
    private ArgsDetailsReceipt (){

    }
    public static ArgsDetailsReceipt getInstance(){
        if (instance==null){
            instance = new ArgsDetailsReceipt();
        }
        return instance;
    }
}
