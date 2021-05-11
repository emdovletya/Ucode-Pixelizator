package world.ucode;

import java.sql.*;

public class StoredImageDB {
    private final String URL = "jdbc:mysql://localhost:3306/pxlztr?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
    private final String USER = "root";
    private final String PASSWORD = "1234";

    public static Connection connection;
    public static Driver driver;

    public StoredImageDB() {
        this.connect();
    }

    private void connect() {
        try {
            driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);

            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public StoredImage selectById(int id) {
        StoredImage image = null;
        String sqlQuery = "SELECT * FROM images WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                int imgId = resultSet.getInt(1);
                String imgName = resultSet.getString(2);
                long imgSize = resultSet.getLong(3);
                String imgType = resultSet.getString(4);
                String filePath = resultSet.getString(5);
                image = new StoredImage(imgId, imgName, imgType, imgSize, filePath);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return image;
    }

    public void insert(StoredImage image) throws SQLException {
        String sqlQuery = "INSERT INTO images (original_file_name, size, type, file_path) Values (?, ?, ?, ?)";
        String generatedColumns[] = { "id" };
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, generatedColumns)) {
            preparedStatement.setString(1, image.getOriginalName());
            preparedStatement.setLong(2, image.getSize());
            preparedStatement.setString(3, image.getType());
            preparedStatement.setString(4, image.getFilePath());

            if (preparedStatement.executeUpdate() > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int primkey = generatedKeys.getInt(1);
                    image.setId(primkey);
                }
            }
        }
    }
}
