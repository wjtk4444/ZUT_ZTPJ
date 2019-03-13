package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum Position implements Serializable
{
    WORKER("Pracownik"),
    TRADESMAN("Handlowiec"),
    DIRECTOR("Dyrektor");

    private String name;

    Position(String name)
    {
        this.name = name;
    }

    // lookup table for name -> enum resolving
    private static final Map<String, Position> cache = new HashMap<>();
    static
    {
        for(Position position : Position.values())
            cache.put(position.name, position);
    }

    static public Position fromName(String name)
    {
        return cache.get(name);
    }

    public String getName()
    {
        return  name;
    }
}
