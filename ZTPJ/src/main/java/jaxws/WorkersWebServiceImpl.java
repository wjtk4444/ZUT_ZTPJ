package jaxws;

import dao.WorkerDaoFactory;
import rmi.Validator;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(endpointInterface = "jaxws.IWorkersWebService", targetNamespace="http://localhost/")
public class WorkersWebServiceImpl implements IWorkersWebService
{
    @WebMethod
    @WebResult
    public WorkerListWrapper getAllWorkersXML(String token)
    {
        Validator validator = new Validator();
        if(validator.validateToken(token))
        {
            WorkerListWrapper workerListWrapper = new WorkerListWrapper();
            workerListWrapper.setWorkers(WorkerDaoFactory.getWorkerDao().getAll());
            return workerListWrapper;
        }

        return null;
    }
}
