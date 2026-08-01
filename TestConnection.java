import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class TestConnection {
    public static void main(String[] args) {
        String[] hosts = {
            "jdbc:postgresql://db.ufzdpvqlvmfnpuldndhv.supabase.co:5432/postgres?sslmode=require",
            "jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true",
            "jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:6579/postgres?sslmode=require&pgbouncer=true"
        };
        String[] users = {"postgres", "postgres.ufzdpvqlvmfnpuldndhv"};
        String password = "M@sjidD!&App";

        for (String url : hosts) {
            for (String user : users) {
                boolean isPooler = url.contains("pooler");
                boolean hasRef = user.contains(".");
                if (isPooler && !hasRef) continue;
                if (!isPooler && hasRef) continue;
                try {
                    System.out.print("Testing: " + url + " user=" + user + " ... ");
                    Connection conn = DriverManager.getConnection(url, user, password);
                    ResultSet rs = conn.createStatement().executeQuery("SELECT 1");
                    rs.next();
                    System.out.println("CONNECTED! " + rs.getInt(1));
                    conn.close();
                } catch (Exception e) {
                    System.out.println("FAILED: " + e.getMessage().substring(0, Math.min(80, e.getMessage().length())));
                }
            }
        }
    }
}
