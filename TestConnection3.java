import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection3 {
    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv6Addresses", "true");
        String url = "jdbc:postgresql://db.ufzdpvqlvmfnpuldndhv.supabase.co:5432/postgres?sslmode=require";
        String user = "postgres";
        String password = "M@sjidD!&App";

        try {
            System.out.print("Direct connection test... ");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("CONNECTED!");
            conn.close();
        } catch (Exception e) {
            System.out.println("FAILED:");
            System.out.println("  Exception: " + e.getClass().getName());
            System.out.println("  Message: " + e.getMessage());
            Throwable cause = e.getCause();
            int depth = 0;
            while (cause != null && depth < 5) {
                System.out.println("  Caused by: " + cause.getClass().getName() + ": " + cause.getMessage());
                cause = cause.getCause();
                depth++;
            }
        }
    }
}
