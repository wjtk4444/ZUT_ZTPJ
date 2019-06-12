package jaxws;

import model.Worker;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

public class Client
{
    IWorkersWebService workersWebService;

    public Client(String address, int port)
    {
        try
        {
            URL url = new URL("http://" + address + ":" + port + "/IWorkersWebService?wsdl");
            QName qName = new QName("http://localhost/", "WorkersWebServiceImplService");
            Service service = Service.create(url, qName);
            workersWebService = service.getPort(IWorkersWebService.class);

            int a =2;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public List<Worker> getWorkersFromServer(String token)
    {
        try
        {
            WorkerListWrapper workerListWrapper = workersWebService.getAllWorkersXML(token);
            return workerListWrapper.getWorkers();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }
}
