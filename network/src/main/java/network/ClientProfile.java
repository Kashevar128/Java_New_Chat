package network;

public class ClientProfile {

    private String name;
    private String password;
    private byte[] avatar;

    public ClientProfile(String name, String password, byte[] avatar) {
        this.name = name;
        this.password = password;
        this.avatar = avatar;
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
}
