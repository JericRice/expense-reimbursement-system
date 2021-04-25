package utilities;

public class Main {
    public static void main(String[] args) {
        String password = "admin";
        String enc = Encryption.encrypt(password);
        System.out.println(enc);
        String dec = Encryption.decrypt(enc);
        System.out.println(dec);
        System.out.println(Encryption.decrypt("zcHk+U2xwWh3/os9AYfk5A=="));
    }
}
