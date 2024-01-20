package UDP;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;

public class Client extends JFrame implements ActionListener,Runnable {
    private JTextArea taMsg=new JTextArea("以下是聊天记录\n");
    private JTextField tfMsg=new JTextField();
    private DatagramSocket ds=null;
    private String nickname=null;
    public Client(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(taMsg,BorderLayout.CENTER);
        tfMsg.setBackground(Color.yellow);
        this.add(tfMsg,BorderLayout.SOUTH);
        tfMsg.addActionListener(this);
        this.setSize(280,500);
        this.setVisible(true);
        nickname =JOptionPane.showInputDialog("输入昵称:");
        this.setTitle("客户端:"+nickname);
        try{
            ds=new DatagramSocket();
            InetAddress add=InetAddress.getByName("127.0.0.1");
            ds.connect(add,9999);
            String msg=nickname+"登陆！";
            byte[]data=msg.getBytes();
            DatagramPacket dp=new DatagramPacket(data,data.length);
            ds.send(dp);
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
                    String msg=new String(dp.getData(),0,dp.getLength());
                    taMsg.append(msg+"\n");
                    
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
    
    public void actionPerformed(ActionEvent e){
        try{
            String msg=nickname+"说:"+tfMsg.getText();
            byte[]data=msg.getBytes();
            DatagramPacket dp=new DatagramPacket(data, data.length);
            ds.send(dp);
        }catch(Exception ex){}
        finally{
            tfMsg.setText("");
        }
    }
    public static void main(String []args){
            //Scanner input=new Scanner(System.in);
            Client client=new Client();
            //input.close();
        }
}
