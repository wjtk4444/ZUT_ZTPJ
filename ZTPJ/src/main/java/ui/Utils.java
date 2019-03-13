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
                // do nothing
            }
        }
    }

    static String promptForString(String message)
    {
        while(true)
        {
            System.out.print(message);
            String response = scanner.nextLine();
            if(response.trim().length() > 0)
                return response;
            System.out.println("Wartosc nie moze byc pusta");
        }
    }

    static String promptForNullableString()
    {
        String response = scanner.nextLine();
        if (response.trim().length() == 0)
            return null;

        return response;
    }
}
