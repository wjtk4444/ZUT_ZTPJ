package jaxws;

import model.Worker;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WorkerListWrapper
{
    @XmlElement
    private List<Worker> workers;

    public List<Worker> getWorkers()
    {
        return workers;
    }

    public void setWorkers(List<Worker> workers)
    {
        this.workers = workers;
    }
}
