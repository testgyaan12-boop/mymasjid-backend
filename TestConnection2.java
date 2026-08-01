import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection2 {
    public static void main(String[] args) {
        String[][] tests = {
            // pooler with just postgres user
            {"jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true", "postgres"},
            // pooler with project ref user (no pgbouncer flag)
            {"jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:6543/postgres?sslmode=require", "postgres.ufzdpvqlvmfnpuldndhv"},
            // direct IPv4 attempt
            {"jdbc:postgresql://db.ufzdpvqlvmfnpuldndhv.supabase.co:5432/postgres?sslmode=disable", "postgres"},
            // try without ssl
            {"jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:6543/postgres?sslmode=disable", "postgres.ufzdpvqlvmfnpuldndhv"},
        };
        String password = "M@sjidD!&App";

        for (String[] test : tests) {
            String url = test[0];
            String user = test[1];
            try {
                System.out.print("Testing: " + url + " user=" + user + " ... ");
                Connection conn = DriverManager.getConnection(url, user, password);
                System.out.println("CONNECTED!");
                conn.close();
            } catch (Exception e) {
                String msg = e.getMessage();
                if (msg.length() > 120) msg = msg.substring(0, 120);
                System.out.println("FAILED: " + msg);
            }
        }
    }
}
