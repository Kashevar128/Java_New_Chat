package common;

import java.io.Serializable;

public class ClientProfile implements Serializable {

    private String name;
    private String password;
    private byte[] avatar;

    public ClientProfile(String name, String password, byte[] avatar) {
        this.name = name;
        this.password = password;
        this.avatar = avatar;
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
}
