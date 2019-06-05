package model.worker;

import model.Position;
import model.Worker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tradesman extends Worker
{
    @XmlElement
    private int commission;
    @XmlElement
    private int monthlyCommissionLimit;

    public Tradesman(String firstName, String lastName, String pesel, int salary, String phoneNumber, String serviceCardNumber, int id,
                     int commission, int monthlyCommissionLimit)
    {
        super(firstName, lastName, pesel, salary, phoneNumber, serviceCardNumber, id);

        this.position = Position.TRADESMAN;
        this.commission = commission;
        this.monthlyCommissionLimit = monthlyCommissionLimit;
    }

    public Tradesman(Worker worker, int commission, int monthlyCommissionLimit)
    {
        super(worker.getFirstName(), worker.getLastName(), worker.getPesel(), worker.getSalary(), worker.getPhoneNumber(), worker.getServiceCardNumber(), worker.getId());

        this.position = Position.TRADESMAN;
        this.commission = commission;
        this.monthlyCommissionLimit = monthlyCommissionLimit;
    }

    @Override
    public String toString()
    {
        return super.toString() +
                String.format(""
                        + "Prowizja (%%)        : %s%n" // 1 additional whitespace because %% counts as %
                        + "Limit prowizji/mc   : %s%n"

                        , commission
                        , monthlyCommissionLimit
                );
    }

    /*
     * Getters and Setters
     */
    public int getCommision()
    {
        return commission;
    }

    public void setCommision(int commission)
    {
        this.commission = commission;
    }

    public int getMonthlyCommissionLimit()
    {
        return monthlyCommissionLimit;
    }

    public void setMonthlyCommissionLimit(int monthlyCommissionLimit)
    {
        this.monthlyCommissionLimit = monthlyCommissionLimit;
    }
}
