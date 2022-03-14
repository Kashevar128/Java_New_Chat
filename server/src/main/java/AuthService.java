import java.sql.SQLException;

public interface AuthService {

    public void start();
    public void stop();
    public boolean auth(ClientProfile clientProfile);
}
