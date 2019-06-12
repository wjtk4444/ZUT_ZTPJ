package jaxws;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface IWorkersWebService
{
    @WebMethod
    @WebResult
    WorkerListWrapper getAllWorkersXML(String token);
}
