package jaxws;
import com.sun.xml.internal.ws.server.EndpointFactory;

import javax.xml.ws.Endpoint;


public class Server
{
    private int port;
    private Endpoint endpoint;

    public Server(int port)
    {
        this.port = port;
    }

    public void runServer()
    {
        if(endpoint != null)
            return;

        try
        {
            endpoint = Endpoint.publish("http://localhost:" + port + "/IWorkersWebService", new WorkersWebServiceImpl());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void stopServer()
    {
        if(endpoint == null)
            return;

        endpoint.stop();
    }
}
