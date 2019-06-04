import networking.Server;
import ui.Menu;

public class Main
{
    public static void main(String[] args)
    {
        Server server = new Server(1234); // password: "secret"
        server.runServer();

        Menu.displayMenu();
    }
}
