package backup;

import dao.WorkerDao;
import dao.WorkerDaoFactory;
import model.Position;
import model.Worker;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Backup
{
    public static boolean backupDatabaseToFile(String filePath)
    {
        List<Worker> workers = WorkerDaoFactory.getWorkerDao().getAll();

        String extension = getFileExtension(filePath);
        if(extension.contentEquals(".zip"))
            return writeZipFile(filePath, workers);
        else if(extension.contentEquals(".gzip"))
            throw new NotImplementedException(); //TODO
        else
            return false;
    }

    public static boolean restoreDatabaseFromFile(String filePath)
    {
        List<Worker> workers = null;

        String extension = getFileExtension(filePath);
        if(extension.contentEquals(".zip"))
            workers = readZipFile(filePath);
        else if(extension.contentEquals("gz"))
            throw new NotImplementedException(); //TODO
        else
            return false;

        if(workers == null)
            return false;

        // delete the entire database
        WorkerDao workerDao = WorkerDaoFactory.getWorkerDao();
        List<Worker> currentWorkers = WorkerDaoFactory.getWorkerDao().getAll();
        for(Worker worker : currentWorkers)
            workerDao.delete(worker);

        // populate database with new entries
        for(Worker worker : workers)
            workerDao.save(worker);

        return true;
    }

    static boolean writeZipFile(String filePath, List<Worker> workers)
    {
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            //ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for(Worker worker : workers)
                objectOutputStream.writeObject(worker);

            objectOutputStream.close();
            //zipOutputStream.close();
            fileOutputStream.close();

            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    static List<Worker> readZipFile(String filePath)
    {
        List<Worker> workers = new ArrayList<>();
        try
        {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            //ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            while(fileInputStream.available() > 0)
                workers.add((Worker)objectInputStream.readObject());

            objectInputStream.close();
            //zipInputStream.close();
            fileInputStream.close();

            return workers;
        }
        catch (IOException ex)
        {
            return null;
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    static String getFileExtension(String filePath)
    {
        int lastDotPosition = filePath.lastIndexOf(".");
        return filePath.substring(lastDotPosition);
    }
}
