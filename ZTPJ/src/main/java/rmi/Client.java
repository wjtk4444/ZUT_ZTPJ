package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client
{
    public String getToken(String address, int port, String username, String password)
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry(address, port);
            Validator validator = (Validator) registry.lookup("validator");

            return validator.getNewToken(username, password);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }
}
