package model;

import java.io.Serializable;

public class Worker implements Serializable
{
    private String firstName;
    private String lastName;
    private String pesel;
    protected Position position;

    private String phoneNumber;
    private String serviceCardNumber;
    private int salary;
    private int id; // database id

    public Worker(String firstName, String lastName, String pesel, int salary, String phoneNumber, String serviceCardNumber, int id)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.serviceCardNumber = serviceCardNumber;

        this.id = id;
        this.position = Position.WORKER;
    }

    public Position getPosition()
    {
        return position;
    }

    @Override
    public String toString()
    {
        return String.format(""
                + "Identyfikator PESEL : %s%n"
                + "Imie                : %s%n"
                + "Nazwisko            : %s%n"
                + "Stanowisko          : %s%n"
                + "Wynagrodzenie       : %s%n"
                + "Telefon sluzbowy    : %s%n"
                + "Karta sluzbowa      : %s%n"

                , pesel
                , firstName
                , lastName
                , position.getName()
                , salary
                , (phoneNumber       != null ? phoneNumber       : "-brak-")
                , (serviceCardNumber != null ? serviceCardNumber : "-brak-")
        );
    }

    /*
     * Getters and Setters
     */
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPesel()
    {
        return pesel;
    }

    public void setPesel(String pesel)
    {
        this.pesel = pesel;
    }

    public int getSalary()
    {
        return salary;
    }

    public void setSalary(int salary)
    {
        this.salary = salary;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getServiceCardNumber()
    {
        return serviceCardNumber;
    }

    public void setServiceCardNumber(String serviceServiceCardNumber)
    {
        this.serviceCardNumber = serviceServiceCardNumber;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
