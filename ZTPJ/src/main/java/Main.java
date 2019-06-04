import ui.Menu;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main
{
    public static void main(String[] args) throws UnknownHostException {
        networking.Server serverNetworking = new networking.Server(1234); // password: "secret"
        serverNetworking.runServer();

        rmi.Server serverRMI = new rmi.Server(12345); // username: "admin", password: "admin1" || "user", "qwerty"
        serverRMI.runServer();

        System.out.println(InetAddress.getLocalHost().getHostName());

        Menu.displayMenu();
    }
}
