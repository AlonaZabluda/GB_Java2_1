package lesson8_ChatFX;

public interface AuthService {
    void start();
    void stop();
    String getNickByLoginPass(String login, String pass);
}
