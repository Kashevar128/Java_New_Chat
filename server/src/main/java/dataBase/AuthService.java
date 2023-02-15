package dataBase;

import common.ClientProfile;

public interface AuthService {

    public boolean auth(ClientProfile clientProfile);
    public boolean reg(ClientProfile clientProfile);
}
