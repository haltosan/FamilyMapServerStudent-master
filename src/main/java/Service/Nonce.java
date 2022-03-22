package Service;

public class Nonce {
    private static int nonce;
    private static Nonce instance;

    public static Nonce getInstance(){
        if(instance == null){
            instance = new Nonce();
        }
        return instance;
    }
    private Nonce(){
        nonce = (int)System.currentTimeMillis();
    }

    public int next(){
        return nonce++;
    }
}
