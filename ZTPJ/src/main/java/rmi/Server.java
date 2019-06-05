package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server
{
    private int port;

    public Server(int port)
    {
        this.port = port;
    }

    public void runServer()
    {
        try
        {
            Validator validator = new Validator();
            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind("validator", validator);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void stopServer()
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry(port);
            registry.unbind("validator");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
