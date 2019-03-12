package ui;

import model.*;
import model.worker.Director;
import model.worker.Tradesman;

import java.util.Scanner;

import static ui.Utils.prompForInteger;
import static ui.Utils.prompForNullableString;

public class WorkerUI
{
    static Scanner scanner = new Scanner(System.in);

    static model.Worker readFromStdin()
    {
        System.out.println("Dodaj pracownika:");

        System.out.print("Imie : ");
        String firstName = scanner.nextLine();

        System.out.print("Nazwisko : ");
        String lastName = scanner.nextLine();

        System.out.print("Pesel : ");
        String pesel = scanner.nextLine();

        System.out.print("[D]yrektor / [H]andlowiec / [P]racownik : ");
        Position position = promptForPosition();

        System.out.print("Numer telefonu / brak : ");
        String phoneNumber = prompForNullableString("brak");

        System.out.print("Numer karty sluzbowej / brak : ");
        String serviceCardNumber = prompForNullableString("brak");

        int salary = prompForInteger("Wynagrodzenie : ");

        switch (position)
        {
            case DIRECTOR:
                int businessAllowance = prompForInteger("Dodatek sluzbowy : ");
                int monthlyCostLimit = prompForInteger("Limit kosztow / miesiac:");

                return new Director(firstName, lastName, pesel, salary, phoneNumber, serviceCardNumber, -1,
                        businessAllowance, monthlyCostLimit);

            case TRADESMAN:
                int commission = prompForInteger("Prowizja % : ");
                int monthlyCommissionLimit = prompForInteger("Limit prowizji / miesiac : ");

                return new Tradesman(firstName, lastName, pesel, salary, phoneNumber, serviceCardNumber, -1,
                        commission, monthlyCommissionLimit);

            case WORKER:
                return new model.Worker(firstName, lastName, pesel, salary, phoneNumber, serviceCardNumber, -1);

            // cannot occur
            default:
                return null;
        }
    }

    static Position promptForPosition()
    {
        while (true)
        {
            switch (scanner.nextLine().charAt(0))
            {
                case 'd':
                case 'D':
                    return Position.DIRECTOR;
                case 'h':
                case 'H':
                    return Position.TRADESMAN;
                case 'p':
                case 'P':
                    return Position.WORKER;
            }
            System.out.println("Niepoprawna opcja");
            System.out.print("[D]yrektor / [H]andlowiec / [P]racownik : ");
        }
    }
}
