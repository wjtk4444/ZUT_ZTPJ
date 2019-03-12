package dao;

import model.Position;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

public class WorkerDaoFactory
{
    // cache
    private static final Map<Position, WorkerDao> cache = new HashMap<>();
    static
    {
        for(Position position : Position.values())
            switch (position)
            {
                case DIRECTOR:
                    cache.put(position, new DirectorDao());
                case TRADESMAN:
                    cache.put(position, new TradesmanDao());
                case WORKER:
                    cache.put(position, new WorkerDao());
            }
    }

    public static WorkerDao getWorkerDao(Position position)
    {
        //if(cache.containsKey(position))
            return new WorkerDao();
        //else
          //  throw new NotImplementedException();
    }
}
