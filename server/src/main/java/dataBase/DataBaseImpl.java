package dataBase;

import common.ClientProfile;
import org.intellij.lang.annotations.Language;
import ava.Avatar;

import java.sql.*;

public class DataBaseImpl implements AuthService, DataBase {

    private Connection connection;

    @Override
    public Connection getConnection() throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:server/usersDB");
        System.out.println("База данных подключена");
        return connection;
    }

    @Override
    public void start() {
        try {
            connection = getConnection();
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при подключении базы данных");
        }
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при закрытии соединения с базой данных");
        }
        System.out.println("Сервис аутентификации остановлен");
    }

    @Override
    public boolean reg(ClientProfile clientProfile) {
        if (repeatUser(clientProfile.getName())) return false;
        String query = "INSERT INTO users (name, password, avatar) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, clientProfile.getName());
            statement.setString(2, clientProfile.getPassword());
            statement.setBytes(3, Avatar.createAvatar(clientProfile.getName()));
            statement.executeUpdate();
            System.out.println("Пользователь успешно добавлен!");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении");
        }
    }

    @Override
    public boolean auth(ClientProfile clientProfile) {
        if (repeatUser(clientProfile.getName())) return false;
        String query = "SELECT * FROM users";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("name").equals(clientProfile.getName()) && rs.getString("password").
                        equals(clientProfile.getPassword())) {
                    System.out.println("Пользователь опознан");
                    return true;
                }
            }
            System.out.println("Пользователь не найден");
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка базы данных при аутентификации пользователя");
        }
    }

    public boolean repeatUser(String name) {
        String query = "SELECT * FROM users";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("name").equals(name)) {
                    System.out.println("Учетная запись уже используется");
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при поиске повторов в базе данных");
        }
    }

    public byte[] getBaseAvatar(String name) {
        String query = "SELECT avatar FROM users WHERE name = ? ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            return rs.getBytes("avatar");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void createTable() throws Exception {
        {
            String query = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT, password TEXT, avatar BLOB)";
            try (Statement statement = connection.createStatement()) {
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Ошибка при создании таблицы");
            }
        }
    }
}
