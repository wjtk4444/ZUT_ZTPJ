package dao;

import model.Worker;
import model.worker.Director;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DirectorDao extends WorkerDao
{
    PreparedStatement getDirectorAllowanceStatement = null;
    PreparedStatement insertDirectorAllowanceStatement = null;
    PreparedStatement deleteDirectorAllowanceStatement = null;

    DirectorDao()
    {
        try
        {
            connection = ConnectionPool.getConnection();

            String getDirectorAllowanceSQL = "SELECT * FROM directors_allowances WHERE worker_id = ?";
            getDirectorAllowanceStatement = connection.prepareStatement((getDirectorAllowanceSQL));

            String insertDirectorAllowanceSQL = "INSERT INTO "
                    + "directors_allowances(worker_id, business_allowance, monthly_cost_limit) "
                    + "VALUES(?, ?, ?)";
            insertDirectorAllowanceStatement = connection.prepareStatement((insertDirectorAllowanceSQL));

            String deleteDirectorAllowanceSQL = "DELETE FROM directors_allowances WHERE worker_id = ?";
            deleteDirectorAllowanceStatement = connection.prepareStatement(deleteDirectorAllowanceSQL);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public Worker get(int id)
    {
        Worker worker = super.get(id);

        try
        {
            getDirectorAllowanceStatement.setInt(1, worker.getId());
            ResultSet rs = getDirectorAllowanceStatement.executeQuery();

            if (rs.next())
            {
                int businessAllowance = rs.getInt("business_allowance");
                int monthlyLimit = rs.getInt("monthly_cost_limit");

                return new Director(worker, businessAllowance, monthlyLimit);
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public int save(Worker worker)
    {
        int insertedId = super.save(worker);

        try
        {
            insertDirectorAllowanceStatement.setInt(1, insertedId);
            insertDirectorAllowanceStatement.setInt(2, ((Director) worker).getBusinessAllowance());
            insertDirectorAllowanceStatement.setInt(3, ((Director) worker).getMonthlyCostLimit());

            insertDirectorAllowanceStatement.execute();

            return insertedId;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return -1;
    }

    @Override
    public boolean delete(Worker worker)
    {
        try
        {
            deleteDirectorAllowanceStatement.setInt(1, worker.getId());
            deleteDirectorAllowanceStatement.execute();

            return super.delete(worker);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }
}
