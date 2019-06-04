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
        Runnable serverThread =  new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    Validator validator = new Validator();
                    Registry registry = LocateRegistry.createRegistry(port);
                    registry.rebind("validator", validator);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        };

        new Thread(serverThread).start();
    }
}
