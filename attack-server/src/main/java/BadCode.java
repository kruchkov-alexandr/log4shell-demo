import java.io.IOException;

public class BadCode {
    public BadCode() throws IOException {
        Process process = Runtime.getRuntime().exec("pwd");
        java.io.BufferedReader reader = new java.io.BufferedReader(
            new java.io.InputStreamReader(process.getInputStream()));

        System.err.println("\n[!] YOU'VE BEEN H4CKED! [!]");
        System.err.println("[*] Current directory: " + reader.readLine());
        System.err.println("[*] Your system belongs to us now >:)");
        System.err.println("[-] Have a nice day! muhahahaha!\n");
    }
}
