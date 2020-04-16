package tian.pusen.entity;

import javax.websocket.Session;
import java.io.Serializable;

public class Client implements Serializable {
    private String username;
    private Session session;

    public Client() { }
    public Client(String username, Session session) {
        this.username = username;
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "Client{" +
                "username='" + username + '\'' +
                ", session=" + session +
                '}';
    }
}
