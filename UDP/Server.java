package UDP;
import java.awt.Color;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.net.*;
import javax.swing.JFrame;

public class Server extends JFrame implements Runnable{
    private DatagramSocket ds=null;
    private ArrayList<SocketAddress>clients=new ArrayList<SocketAddress>();
    public Server()throws Exception{
        this.setTitle("服务器端");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.yellow);
        this.setSize(400,500);
        this.setVisible(true);
        try{
            ds=new DatagramSocket(9999);
            new Thread(this).start();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
        public void run(){
            try{
                while(true){
                    byte[]data=new byte[255];
                    DatagramPacket dp=new DatagramPacket(data,data.length);
                    ds.receive(dp);
                    SocketAddress cAddress=dp.getSocketAddress();
                    if(!clients.contains(cAddress))clients.add(cAddress);
                    this.senToAll(dp);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
    
    public void senToAll(DatagramPacket dp)throws Exception{
                for(SocketAddress sa:clients){
                    DatagramPacket datagram=new DatagramPacket(dp.getData(),dp.getLength(),sa);
                    ds.send(datagram);
                }
            }
    public static void main(String []args)throws Exception{
            //Scanner input=new Scanner(System.in);
            Server Server=new Server();
            //input.close();
        }
}
