package common;

import java.io.Serializable;

public class ClientProfile implements Serializable {

    private String name;
    private String password;
    private byte[] avatar;
    private boolean online;

    public ClientProfile(String name, String password, byte[] avatar) {
        this.name = name;
        this.password = password;
        this.avatar = avatar;
        this.online = false;
    }

    public ClientProfile(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }
}
