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

    public boolean addUser(String name, String pass) {
        if(repeatUser(name)) return false;
        @Language("SQL")
        String query = "INSERT INTO users (name, password, avatar) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, pass);
            statement.setBytes(3, Avatar.createAvatar(name));
            System.out.println("Пользователь успешно добавлен!");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении");
        }
    }

    public boolean repeatUser(String name) {
        @Language("SQL")
        String query = "SELECT * FROM users";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("name").equals(name)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске повторов в базе данных");
        }
    }

    public byte[] getAvatar(String name) throws SQLException {
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
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка базы данных при аутентификации пользователя");
        }
    }
}
