package dao;

import model.Position;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

public class WorkerDaoFactory
{
    // cache
    private static WorkerDaoWrapper genericWorkerDao = new WorkerDaoWrapper();

    private static final Map<Position, WorkerDao> cache = new HashMap<>();
    static
    {
        for(Position position : Position.values())
            switch (position)
            {
                case DIRECTOR:
                    cache.put(position, new DirectorDao());
                    break;
                case TRADESMAN:
                    cache.put(position, new TradesmanDao());
                    break;
                case WORKER:
                    cache.put(position, new WorkerDao());
                    break;
            }
    }

    static WorkerDao getWorkerDaoByPosition(Position position)
    {
        if(cache.containsKey(position))
            return cache.get(position);
        else
            throw new NotImplementedException();
    }

    public static WorkerDao getWorkerDao()
    {
        return genericWorkerDao;
    }
}
