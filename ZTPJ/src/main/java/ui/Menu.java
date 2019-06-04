package ui;

import backup.Backup;
import dao.WorkerDao;
import dao.WorkerDaoFactory;
import model.Position;
import model.Worker;
import networking.Client;

import java.util.List;
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
            System.out.println("MENU:");
            System.out.println("    1. Lista pracownikow:");
            System.out.println("    2. Dodaj pracownika:");
            System.out.println("    3. Usun pracownika:");
            System.out.println("    4. Kopia zapasowa:");
            System.out.println("    5. Pobierz dane z sieci (hasło):");
            System.out.println("    6. Pobierz dane z sieci (token rmi):");
            System.out.println("    0. Wyjscie:");

            switch (prompForInteger("Wybor> "))
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

                case 5:
                    getDataFromNetwork(false);
                    break;

                case 6:
                    getDataFromNetwork(true);
                    break;

                default:
                    System.out.println("Niepoprwana opcja");
                    break;
            }
        }
    }

    static void getDataFromNetwork(boolean rmi)
    {
        String address = promptForString("Podaj adres serwera: ");
        int port = prompForInteger("Podaj port (serwer danych): ");

        String secret;
        if(rmi)
        {
            int portRMI = prompForInteger("Podaj port (serwer RMI): ");

            String username = promptForString("Podaj nazwe uzytkownika: ");
            String password = promptForString("Podaj haslo: ");

            rmi.Client client = new rmi.Client();
            secret = client.getToken(address, portRMI, username, password);
            if(secret == null)
            {
                System.out.println("Nieprawidlowe dane, lub blad poalczenia.");
                return;
            }

            System.out.println("Otrzymano token: " + secret);
            System.out.println("Autoryzacja tokenem");
        }
        else
        {
            secret = promptForString("Podaj haslo: ");
        }

        networking.Client client = new networking.Client();
        List<Worker> workers = client.getWorkersFromServer(address, port, secret);
        if (workers == null)
        {
            System.out.println("Nieprawidlowe dane, lub blad poalczenia.");
        }
        else
        {
            System.out.println("Dane pobrane z sieci:");
            for (Worker worker : workers)
                System.out.println(worker);
        }
    }

    static void printWorkers()
    {
        for(Worker worker : WorkerDaoFactory.getWorkerDao().getAll())
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
            WorkerDao workerDao = WorkerDaoFactory.getWorkerDao();
            workerDao.save(worker);
        }
    }

    static boolean prompForSave()
    {
        while (true)
        {
            String line = scanner.nextLine();
            if(line.length() > 0)
                switch (line.charAt(0))
                {
                    case 'Z':
                    case 'z':
                        return true;
                    case 'O':
                    case 'o':
                        return false;
                }

            System.out.println("Niepoprawna opcja");
            System.out.print("[Z]apisz / [O]drzuc : ");
        }
    }

    static char promptForDelete()
    {
        while (true)
        {
            String line = scanner.nextLine();
            if(line.length() > 0)
                switch (line.charAt(0))
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
            System.out.print("[U]sun / [N]astepny pracownik / [W]yjscie : ");
        }
    }

    static void deleteWorker()
    {
        WorkerDao  workerDao = WorkerDaoFactory.getWorkerDao();
        for(Worker worker : workerDao.getAll())
        {
            System.out.println(worker);
            System.out.println("Czy chcesz usunac tego pracownika?");
            System.out.print("[U]sun / [N]astepny pracownik / [W]yjscie : ");
            char choice = promptForDelete();
            if(choice == 'u')
            {
                workerDao.delete(worker);
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
        System.out.println("UWAGA: W przypadku przywracania bazy z pliku, wszystkie obecne dane zostana zastapione.");
        System.out.print("[Z]achowaj / [O]dtworz / [W]yjscie : ");
        char choice = promptForBackup();
        if(choice == 'z')
        {
            String filePath = promptForString("Podaj nazwe pliku do zapisu. Dopuszczalne rozszerzenia: .zip oraz .gzip : ");
            if(Backup.backupDatabaseToFile(filePath))
                System.out.println("Zapisano pomyslnie");
            else
                System.out.println("Nie udalo sie zapisac pliku. Sprawdz nazwe pliku i sprobuj ponownie");
        }
        else if(choice == 'o')
        {
            String filePath = promptForString("Podaj nazwe pliku do odczytu. Dopuszczalne rozszerzenia: .zip oraz .gzip : ");
            if(Backup.restoreDatabaseFromFile(filePath))
                System.out.println("Odczytano pomyslnie");
            else
                System.out.println("Nie udalo sie odczytac pliku. Sprawdz nazwe pliku i sprobuj ponownie");

        }
        // else do nothing for choice == 'w'
    }

    static char promptForBackup()
    {
        while (true)
        {
            String line = scanner.nextLine();
            if(line.length() > 0)
                switch (line.charAt(0))
                {
                    case 'Z':
                    case 'z':
                        return 'z';
                    case 'O':
                    case 'o':
                        return 'o';
                    case 'W':
                    case 'w':
                        return 'w';
                }

            System.out.println("Niepoprawna opcja");
            System.out.println("[Z]achowaj / [O]dtworz / [W]yjscie : ");
        }
    }
}