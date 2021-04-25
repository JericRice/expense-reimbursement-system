package utilities;

/**
 * Custom Encryption for project 1
 * */
public class Encryption {

    /**
     * encrypts string
     * */
    public static String encrypt(String password){
        char[] chars = password.toCharArray();
        int key = 5;
        String enc = "";
        for(char c : chars){
            enc += (c+=key);
        }

        return enc;
    }

    /**
     * decrypts string
     * */
    public static String decrypt(String encryptedPassword){
        char[] chars = encryptedPassword.toCharArray();
        int key = 5;
        String dec = "";
        for(char c : chars){
            dec += (c-=key);
        }

        return dec;
    }
}
