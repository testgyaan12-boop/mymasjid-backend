import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection5 {
    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv6Addresses", "true");
        String password = "M@sjidD!&App";

        // Direct connection with IPv6 address
        String url = "jdbc:postgresql://[2406:da1a:314:7101:c579:ad7:4c2d:cc21]:5432/postgres?sslmode=require";
        String user = "postgres";

        try {
            System.out.print("Direct via IPv6 address... ");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("CONNECTED!");
            conn.close();
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            Throwable cause = e.getCause();
            if (cause != null) System.out.println("  Cause: " + cause.getClass().getSimpleName() + ": " + cause.getMessage());
        }
    }
}
