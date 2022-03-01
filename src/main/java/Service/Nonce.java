package Service;

public class Nonce {
    private static int nonce;
    public static int next(){
        return nonce++;
    }
}
