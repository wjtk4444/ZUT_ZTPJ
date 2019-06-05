package jaxws;

import model.Worker;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
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
            String workersXML = workersWebService.getAllWorkersXML(token);
            if(workersXML == null)
                return null;

            JAXBContext jaxbContext = JAXBContext.newInstance(WorkerListWrapper.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            StringReader stringReader = new StringReader(workersXML);
            WorkerListWrapper workerListWrapper = (WorkerListWrapper)unmarshaller.unmarshal(stringReader);

            return workerListWrapper.getWorkers();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }
}
