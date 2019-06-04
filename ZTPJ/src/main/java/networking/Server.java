package networking;

import dao.WorkerDaoFactory;
import rmi.Validator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    private int port;

    public Server(int port)
    {
        this.port = port;
    }

    public void runServer()
    {
        Runnable serverThread =  new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    ServerSocket serverSocket = new ServerSocket(port);
                    System.out.println("server running ");

                    while(true)
                    {
                        Socket socket = serverSocket.accept();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try
                                {
                                    Validator validator = new Validator();
                                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                                    String secret = dataInputStream.readUTF();
                                    if(!secret.contentEquals("secret") && !validator.validateToken(secret))
                                    {
                                        dataOutputStream.writeUTF("403 Forbidden");
                                    }
                                    else
                                    {
                                        dataOutputStream.writeUTF("200 OK");
                                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(dataOutputStream);
                                        objectOutputStream.writeObject(WorkerDaoFactory.getWorkerDao().getAll());
                                        objectOutputStream.close();
                                    }

                                    dataOutputStream.close();
                                    dataInputStream.close();
                                    socket.close();
                                }
                                catch(Exception ex)
                                {
                                    ex.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        };

        new Thread(serverThread).start();
    }
}
