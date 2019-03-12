package dao;

import model.Worker;
import model.worker.Tradesman;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TradesmanDao extends WorkerDao
{
    PreparedStatement getTradesmanCommissionStatement = null;
    PreparedStatement insertTradesmanCommissionStatement = null;
    PreparedStatement deleteTradesmanCommissionStatement = null;

    TradesmanDao()
    {
        try
        {
            connection = ConnectionPool.getConnection();

            String getTradesmanCommissionSQL = "SELECT * FROM tradesmans_commissions WHERE worker_id = ?";
            getTradesmanCommissionStatement = connection.prepareStatement(getTradesmanCommissionSQL);

            String insertTradesmanCommissionSQL = "INSERT INTO "
                    + "tradesmans_commissions(worker_id, commission, monthly_commission_limit) "
                    + "VALUES(?, ?, ?)";
            insertTradesmanCommissionStatement = connection.prepareStatement(insertTradesmanCommissionSQL);

            String deleteTradesmanCommissionSQL = "DELETE FROM tradesmans_commisions WHERE worker_id = ?";
            deleteTradesmanCommissionStatement = connection.prepareStatement(deleteTradesmanCommissionSQL);
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
            getTradesmanCommissionStatement.setInt(1, worker.getId());
            ResultSet rs = getTradesmanCommissionStatement.executeQuery();

            if(rs.next())
            {
                int commission = rs.getInt("commission");
                int monthlyCommissionLimit = rs.getInt("monthly_commission_limit");

                return new Tradesman(worker, commission, monthlyCommissionLimit);
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
            insertTradesmanCommissionStatement.setInt(1, insertedId);
            insertTradesmanCommissionStatement.setInt(2, ((Tradesman) worker).getCommision());
            insertTradesmanCommissionStatement.setInt(3, ((Tradesman) worker).getMonthlyCommissionLimit());

            insertTradesmanCommissionStatement.execute();

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
            deleteTradesmanCommissionStatement.setInt(1, worker.getId());
            deleteTradesmanCommissionStatement.execute();

            return super.delete(worker);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }
}
