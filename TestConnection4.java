import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection4 {
    public static void main(String[] args) {
        String password = "M@sjidD!&App";
        String projectRef = "ufzdpvqlvmfnpuldndhv";

        // Try various pooler hostname formats
        String[][] tests = {
            // Project-ref as subdomain
            {"jdbc:postgresql://ufzdpvqlvmfnpuldndhv.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true", "postgres"},
            {"jdbc:postgresql://ufzdpvqlvmfnpuldndhv.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true", "postgres.ufzdpvqlvmfnpuldndhv"},
            // Region-specific
            {"jdbc:postgresql://ap-south-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true", "postgres.ufzdpvqlvmfnpuldndhv"},
            // Just postgres user on pooler with project-ref host
            {"jdbc:postgresql://ufzdpvqlvmfnpuldndhv.pooler.supabase.com:6543/postgres?sslmode=require", "postgres"},

            // Transaction mode
            {"jdbc:postgresql://ufzdpvqlvmfnpuldndhv.pooler.supabase.com:6579/postgres?sslmode=require&pgbouncer=true", "postgres.ufzdpvqlvmfnpuldndhv"},
        };

        for (String[] test : tests) {
            try {
                System.out.print("Testing: " + test[0] + " user=" + test[1] + " ... ");
                Connection conn = DriverManager.getConnection(test[0], test[1], password);
                System.out.println("CONNECTED!");
                conn.close();
            } catch (Exception e) {
                String msg = e.getMessage();
                if (msg.length() > 100) msg = msg.substring(0, 100);
                System.out.println("FAILED: " + msg);
            }
        }
    }
}
