package jaxws;

import dao.WorkerDaoFactory;
import rmi.Validator;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@WebService(endpointInterface = "jaxws.IWorkersWebService", targetNamespace="http://localhost/")
public class WorkersWebServiceImpl implements IWorkersWebService
{
    @WebMethod
    public String getAllWorkersXML(String token)
    {
        Validator validator = new Validator();
        if(validator.validateToken(token))
        {
            WorkerListWrapper workerListWrapper = new WorkerListWrapper();
            workerListWrapper.setWorkers(WorkerDaoFactory.getWorkerDao().getAll());
            try
            {
                JAXBContext jaxbContext = JAXBContext.newInstance(WorkerListWrapper.class);
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                StringWriter sw = new StringWriter();
                marshaller.marshal(workerListWrapper, sw);

                return sw.toString();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return null;
    }
}
