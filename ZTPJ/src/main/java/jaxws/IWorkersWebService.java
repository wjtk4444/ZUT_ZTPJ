package jaxws;

import model.Worker;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IWorkersWebService
{
    @WebMethod
    public String getAllWorkersXML(String token);
}
