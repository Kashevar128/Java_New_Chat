package server;

import network.ClientProfile;
import org.intellij.lang.annotations.Language;

import java.sql.*;

public class DataBase implements AuthService {

    private Connection connection;

    public Connection getConnection() throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:users.db");
        System.out.println("База данных подключена");
        return connection;
    }

    public boolean reg (ClientProfile clientProfile) {
        if(repeatUser(clientProfile.getName())) return false;
        @Language("SQL")
        String query = "INSERT INTO users (name, password, avatar) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, clientProfile.getName());
            statement.setString(2, clientProfile.getPassword());
            statement.setBytes(3, Avatar.createAvatar(clientProfile.getName()));
            System.out.println("Пользователь успешно добавлен!");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении");
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
            throw new RuntimeException("Ошибка при поиске повторов в базе данных");
        }
    }

    public byte[] getBaseAvatar(String name) throws SQLException {
        @Language("SQL")
        String query = "SELECT avatar FROM users, WHERE name = ? ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            return rs.getBytes("avatar");
        }
    }

    @Override
    public void start() {
        try {
            connection = getConnection();
        } catch (Exception e) {
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
    public boolean auth(ClientProfile clientProfile) {
        if(repeatUser(clientProfile.getName())) return false;
        @Language("SQL")
        String query = "SELECT * FROM users";
        try(Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                if(rs.getString("name").equals(clientProfile.getName()) && rs.getString("password").
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
}
