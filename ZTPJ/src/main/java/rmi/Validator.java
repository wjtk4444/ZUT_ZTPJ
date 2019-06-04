package rmi;

import java.io.Serializable;
import java.net.Authenticator;
import java.rmi.Remote;
import java.util.*;

public class Validator extends Authenticator implements Remote, Serializable
{

    // singleton would be better, but whatever
    static private Map<String, String> users = new HashMap<>();
    static
    {
        users.put("admin", "admin1");
        users.put("user", "qwerty");
    }

    static private Map<String, Date> tokens = new HashMap<>();

    public String getNewToken(String username, String password)
    {
        if(users.containsKey(username) && users.get(username).contentEquals(password))
        {
            String token = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
            tokens.put(token, new Date());
            return token;
        }

        return null;
    }

    public boolean validateToken(String token)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -2);

        if(tokens.containsKey(token))
        {
            // check if token is less than 2 minutes old
            boolean retVal = tokens.get(token).after(calendar.getTime());

            // remove old token
            tokens.remove(token);

            return retVal;
        }

        return false;
    }
}
