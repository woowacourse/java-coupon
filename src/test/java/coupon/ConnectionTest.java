package coupon;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionTest {

    public static void main(String[] args) {
        String writerDBUrl = "jdbc:mysql://localhost:33306/coupon";
        String readerDBUrl = "jdbc:mysql://localhost:33307/coupon";
        String user = "root";
        String password = "root";
        try (Connection connection = DriverManager.getConnection(writerDBUrl, user, password)) {
            if (connection != null) {
                System.out.println("Connection successful!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(readerDBUrl, user, password)) {
            if (connection != null) {
                System.out.println("Connection successful!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
