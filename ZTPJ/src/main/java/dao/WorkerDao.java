package dao;

import model.Position;
import model.Worker;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class WorkerDao
{
    Connection connection = null;

    // main
    PreparedStatement getWorkerStatement = null;
    PreparedStatement getAllWorkersIdsAndPositionIdsStatement = null;
    PreparedStatement insertWorkerStatement = null;
    PreparedStatement deleteWorkerStatement = null;


    // auxiliary
    PreparedStatement getPositionNameStatement = null;
    PreparedStatement getPositionIdStatement = null;

    WorkerDao()
    {
        try
        {
            connection = ConnectionPool.getConnection();

            String getWorkerSQL = "SELECT * FROM workers WHERE id = ?";
            getWorkerStatement = connection.prepareStatement(getWorkerSQL);

            String getAllWorkersIdsAndPositionIdsSQL = "SELECT id, position_id FROM workers";
            getAllWorkersIdsAndPositionIdsStatement = connection.prepareStatement(getAllWorkersIdsAndPositionIdsSQL);

            String insertWorkerSQL = "INSERT INTO "
                    + "workers(first_name, last_name, pesel, position_id, phone_number, service_card_number, salary) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?) "
                    + "RETURNING id";
            insertWorkerStatement = connection.prepareStatement(insertWorkerSQL);

            String deleteWorkerSQL = "DELETE FROM workers WHERE id = ?";
            deleteWorkerStatement = connection.prepareStatement(deleteWorkerSQL);


            String getPositionNameSQL = "SELECT name FROM positions WHERE id = ?";
            getPositionNameStatement = connection.prepareStatement(getPositionNameSQL);

            String getPositionIdSQL = "SELECT id FROM positions WHERE name = ?";
            getPositionIdStatement = connection.prepareStatement(getPositionIdSQL);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public Worker get(int id)
    {
        try
        {
            getWorkerStatement.setInt(1, id);
            ResultSet resultSet = getWorkerStatement.executeQuery();

            if (resultSet.next())
            {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String pesel = resultSet.getString("pesel");
                int salary = resultSet.getInt("salary");
                String phoneNumber = resultSet.getString("phone_number");
                String serviceCardNumber = resultSet.getString("service_card_number");

                return new Worker(firstName, lastName, pesel, salary, phoneNumber, serviceCardNumber, id);
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    // doing things this way is probably slow as hell, but I really hate copy-pasting code
    public List<Worker> getAll()
    {
        List<Worker> workers = new ArrayList<>();
        try
        {
            ResultSet resultSet = getAllWorkersIdsAndPositionIdsStatement.executeQuery();

            while (resultSet.next())
            {
                int workerId = resultSet.getInt("id");
                int positionId = resultSet.getInt("position_id");
                WorkerDao workerDao = WorkerDaoFactory.getWorkerDao(Position.fromName(getPositionName(positionId)));
                Worker worker = workerDao.get(workerId);
                workers.add(worker);
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return workers;
    }

    // save updates worker's id if current id == -1
    public int save(Worker worker)
    {
        try
        {
            insertWorkerStatement.setString(1, worker.getFirstName());
            insertWorkerStatement.setString(2, worker.getLastName());
            insertWorkerStatement.setString(3, worker.getPesel());
            insertWorkerStatement.setInt(4, getPositionId(worker.getPosition().getName()));
            insertWorkerStatement.setString(5, worker.getPhoneNumber());
            insertWorkerStatement.setString(6, worker.getServiceCardNumber());
            insertWorkerStatement.setInt(7, worker.getSalary());

            ResultSet resultSet = insertWorkerStatement.executeQuery();

            if (resultSet.next())
            {
                int insertedId = resultSet.getInt(1); // first and only value returned by insert
                return insertedId;
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return -1;
    }

    public boolean delete(Worker worker)
    {
        try
        {
            deleteWorkerStatement.setInt(1, worker.getId());
            deleteWorkerStatement.execute();

            return true;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    /*
     * Auxiliary methods
     */
    private String getPositionName(int id)
    {
        // There are only 3 (three) different position names, so querying database each time would be just dumb.
        Map<Integer, String> cache = new HashMap<>();
        if (!cache.containsKey(id))
        {
            try
            {
                getPositionNameStatement.setInt(1, id);
                ResultSet resultSet = getPositionNameStatement.executeQuery();

                if (resultSet.next())
                {
                    String positionName = resultSet.getString("name");
                    cache.put(id, positionName);
                    return positionName;
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                return null;
            }
        }

        return cache.get(id);
    }

    private int getPositionId(String positionName)
    {
        // There are only 3 (three) different position names, so querying database each time would be just dumb.
        Map<String, Integer> cache = new HashMap<>();
        if (!cache.containsKey(positionName))
        {
            try
            {
                getPositionIdStatement.setString(1, positionName);
                ResultSet resultSet = getPositionIdStatement.executeQuery();

                if (resultSet.next())
                {
                    int positionId = resultSet.getInt("id");
                    cache.put(positionName, positionId);
                    return positionId;
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                return -1;
            }
        }

        return cache.get(positionName);
    }
}
