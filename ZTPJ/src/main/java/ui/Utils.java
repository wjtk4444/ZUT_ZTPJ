package ui;

import java.util.Scanner;

public class Utils
{
    static Scanner scanner = new Scanner(System.in);

    static int prompForInteger(String message)
    {
        while(true)
        {
            try
            {
                System.out.print(message);
                String response = scanner.nextLine();
                return Integer.parseInt(response);
            }
            catch(NumberFormatException ex)
            {
                // ¯\_(ツ)_/¯
            }
        }
    }

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static String promptForString(String message)
    {
        while(true)
        {
            System.out.println("Wartosc nie moze byc pusta");
            System.out.print(message);
            String response = scanner.nextLine();
            if(response.trim().length() > 0)
                return response;
        }
    }

    static String promptForNullableString(String nullPattern)
    {
        String response = scanner.nextLine();
        if (response.contentEquals(nullPattern) || response.trim().length() == 0)
            return null;

        return response;
    }
}
