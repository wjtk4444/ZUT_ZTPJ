import ui.Menu;

public class Main
{
    public static void main(String[] args)
    {
        networking.Server serverNetworking = new networking.Server(1234); // password: "secret"
        serverNetworking.runServer();

        rmi.Server serverRMI = new rmi.Server(12345); // username: "admin", password: "admin1" || "user", "qwerty"
        serverRMI.runServer();

        jaxws.Server serverJAXWS = new jaxws.Server(8080);
        serverJAXWS.runServer();

        Menu.displayMenu();

        serverRMI.stopServer();
        serverNetworking.stopServer();
        serverJAXWS.stopServer();
    }
}
