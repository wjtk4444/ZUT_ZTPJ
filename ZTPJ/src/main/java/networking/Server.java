package networking;

import dao.WorkerDaoFactory;
import rmi.Validator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server
{
    private int port;
    private Thread serverThread;
    private ServerSocket serverSocket;

    public Server(int port)
    {
        this.port = port;
    }

    public void runServer()
    {
        if(serverThread != null)
            return;

        serverThread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    serverSocket = new ServerSocket(port);
                    System.out.println("server running ");

                    while(!Thread.currentThread().isInterrupted())
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
                catch (SocketException ex)
                {
                    if(!Thread.currentThread().isInterrupted())
                        ex.printStackTrace();
                    // else
                        // ignore if thread was interrupted and socket force-closed
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        serverThread.start();
    }

    public void stopServer()
    {
        if(serverThread == null)
            return;

        // singnal the thread to quit
        serverThread.interrupt();

        try
        {
            // force close because reasons
            // https://stackoverflow.com/questions/4425350
            serverSocket.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        try
        {
            serverThread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        serverThread = null;
    }
}
