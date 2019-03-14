package dao;

import model.Position;
import model.Worker;

import java.util.List;

public class WorkerDaoWrapper extends WorkerDao
{
   WorkerDaoWrapper() { }

   private WorkerDao getDaoById(int id)
   {
      // get regular user dao first in order to determine his position
      WorkerDao workerDao = WorkerDaoFactory.getWorkerDaoByPosition(Position.WORKER);
      Position position = workerDao.getPositionById(id);

      // return the appropriate dao
      return WorkerDaoFactory.getWorkerDaoByPosition(position);
   }

   public List<Worker> getAll()
   {
      // use regular worker dao as it handles getAll internally
      return WorkerDaoFactory.getWorkerDaoByPosition(Position.WORKER).getAll();
   }

   public Worker get(int id)
   {
      WorkerDao workerDao = getDaoById(id);
      return workerDao.get(id);
   }

   public int save(Worker worker)
   {
      return WorkerDaoFactory.getWorkerDaoByPosition(worker.getPosition()).save(worker);
   }

   public boolean delete(Worker worker)
   {
      return WorkerDaoFactory.getWorkerDaoByPosition(worker.getPosition()).delete(worker);
   }
}
