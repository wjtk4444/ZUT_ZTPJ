package ui;

import dao.WorkerDao;
import dao.WorkerDaoFactory;
import model.Position;
import model.Worker;

import java.util.Scanner;

import static ui.Utils.*;

public class Menu
{
    static Scanner scanner = new Scanner(System.in);

    public static void displayMenu()
    {
        while_loop:
        while (true)
        {
            clearScreen();

            System.out.println("MENU:");
            System.out.println("    1. Lista pracownikow:");
            System.out.println("    2. Dodaj pracownika:");
            System.out.println("    3. Usun pracownika:");
            System.out.println("    4. Kopia zapasowa:");
            System.out.println("    0. Wyjscie:");

            switch (prompForInteger("Wybor>"))
            {
                case 0:
                    break while_loop;

                case 1:
                    printWorkers();
                    break;

                case 2:
                    addWorker();
                    break;

                case 3:
                    deleteWorker();
                    break;

                case 4:
                    backup();
                    break;

                default:
                    System.out.println("Niepoprwana opcja");
                    break;
            }
        }
    }

    static void printWorkers()
    {
        for(Worker worker : WorkerDaoFactory.getWorkerDao(Position.WORKER).getAll())
            System.out.println(worker);
    }

    static void addWorker()
    {
        Worker worker = WorkerUI.readFromStdin();
        System.out.println("Czy chcesz zapisac nastepujacego pracownika:");
        System.out.println(worker);
        System.out.print("[Z]apisz / [O]drzuc : ");
        if(prompForSave())
        {
            WorkerDao workerDao = WorkerDaoFactory.getWorkerDao(worker.getPosition());
            workerDao.save(worker);
        }
    }

    static boolean prompForSave()
    {
        while (true)
        {
            switch (scanner.nextLine().charAt(0))
            {
                case 'Z':
                case 'z':
                    return true;
                case 'O':
                case 'o':
                    return false;
            }
            System.out.println("Niepoprawna opcja");
            System.out.print("[Z]apisz / [O]drzuc");
        }
    }

    static char promptForDelete()
    {
        while (true)
        {
            switch (scanner.nextLine().charAt(0))
            {
                case 'U':
                case 'u':
                    return 'u';
                case 'N':
                case 'n':
                    return 'n';
                case 'W':
                case 'w':
                    return 'w';
            }
            System.out.println("Niepoprawna opcja");
            System.out.print("[U]sun / [N]astepny pracownik / [W]yjscie");
        }
    }

    static void deleteWorker()
    {
        for(Worker worker : WorkerDaoFactory.getWorkerDao(Position.WORKER).getAll())
        {
            System.out.println(worker);
            System.out.println("***************************************************************************");

            System.out.println("Czy chcesz usunac tego pracownika?");
            System.out.print("[U]sun / [N]astepny pracownik / [W]yjscie");
            char choice = promptForDelete();
            if(choice == 'u')
            {
                WorkerDaoFactory.getWorkerDao(worker.getPosition()).delete(worker);
                System.out.println("Usunieto pracownika " + worker.getFirstName() + " " + worker.getLastName());
            }
            else if(choice == 'w')
            {
                break;
            }
            // else do nothing for choice == 'n'
        }
    }

    static void backup()
    {
        System.out.println("Not implemented (yet)");
    }
}