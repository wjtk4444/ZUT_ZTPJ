package jaxws;
import javax.xml.ws.Endpoint;


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
            Endpoint.publish("http://localhost:" + port + "/IWorkersWebService", new WorkersWebServiceImpl());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void stopServer()
    {
        // do nothing, endpoints cannot be removed
    }
}
