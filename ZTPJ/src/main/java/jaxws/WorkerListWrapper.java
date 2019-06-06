package jaxws;

import model.Worker;
import model.worker.Director;
import model.worker.Tradesman;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WorkerListWrapper
{
    @XmlElements({
            @XmlElement(type = Worker.class),
            @XmlElement(type = Director.class),
            @XmlElement(type = Tradesman.class),
    })
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
