package dataBase;

import java.sql.Connection;

public interface DataBase {

    public void start();
    public void stop();
    public Connection getConnection() throws Exception;
}
