package networking;

import model.Worker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

public class Client
{
    public List<Worker> getWorkersFromServer(String address, int port, String secret)
    {
        List<Worker> workers = null;
        try
        {
            Socket socket = new Socket(address, port);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream.writeUTF(secret);

            String response = dataInputStream.readUTF();

            if(response.contains("200 OK"))
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(dataInputStream);
                workers = (List<Worker>)objectInputStream.readObject();
                objectInputStream.close();
            }

            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return workers;
    }
}
