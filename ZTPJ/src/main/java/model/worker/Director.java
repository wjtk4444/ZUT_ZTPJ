package model.worker;

import model.Position;
import model.Worker;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Director extends Worker
{
    @XmlElement
    private int businessAllowance;
    @XmlElement
    private int monthlyCostLimit;

    Director() { }

    public Director(String firstName, String lastName, String pesel, int salary, String phoneNumber, String serviceCardNumber, int id,
                     int businessAllowance, int monthlyCostLimit)
    {
        super(firstName, lastName, pesel, salary, phoneNumber, serviceCardNumber, id);

        this.position = Position.DIRECTOR;
        this.businessAllowance = businessAllowance;
        this.monthlyCostLimit = monthlyCostLimit;
    }

    public Director(Worker worker, int businessAllowance, int monthlyCostLimit)
    {
        super(worker.getFirstName(), worker.getLastName(), worker.getPesel(), worker.getSalary(), worker.getPhoneNumber(), worker.getServiceCardNumber(), worker.getId());

        this.position = Position.DIRECTOR;
        this.businessAllowance = businessAllowance;
        this.monthlyCostLimit = monthlyCostLimit;
    }

    @Override
    public String toString()
    {
        return super.toString() +
                String.format(""
                        + "Dodatek sluzbowy    : %s%n"
                        + "Limit kosztow/mc    : %s%n"

                        , businessAllowance
                        , monthlyCostLimit
                );
    }

    /*
     * Getters and Setters
     */
    public int getBusinessAllowance()
    {
        return businessAllowance;
    }

    public void setBusinessAllowance(int businessAllowance)
    {
        this.businessAllowance = businessAllowance;
    }

    public int getMonthlyCostLimit()
    {
        return monthlyCostLimit;
    }

    public void setMonthlyCostLimit(int monthlyCostLimit)
    {
        this.monthlyCostLimit = monthlyCostLimit;
    }
}
