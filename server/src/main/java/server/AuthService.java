package server;

import network.ClientProfile;

import java.sql.Connection;
import java.sql.SQLException;

public interface AuthService {

    public void start();
    public void stop();
    public boolean auth(ClientProfile clientProfile);
    public boolean reg(ClientProfile clientProfile);
    public byte[] getAvatar(String name) throws SQLException;
    public Connection getConnection() throws Exception;
}
