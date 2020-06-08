package ChatJavaFX_2;

import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {
    private class Entry{
        private String login;
        private String password;
        private String nick;

        public Entry(String login, String password, String nick) {
            this.login = login;
            this.password = password;
            this.nick = nick;
        }
    }

    private List<Entry> entries;

    public BaseAuthService() {
        entries = new ArrayList<>();
        entries.add(new Entry("log1", "pass1", "nick1"));
        entries.add(new Entry("log2", "pass2", "nick2"));
        entries.add(new Entry("log3", "pass3", "nick3"));
    }

    @Override
    public void start() {
        System.out.println("Authentication Service Launched");

    }

    @Override
    public void stop() {
        System.out.println("Authentication Service Stopped");

    }

    @Override
    public String getNickByLoginPass(String login, String password) {
        for(Entry entry: entries)
            if(entry.login.equals(login) && entry.password.equals(password)) return entry.nick;
        return null;
    }
}
