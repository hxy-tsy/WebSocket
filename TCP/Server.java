package TCP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


class Server extends JFrame implements ActionListener,Runnable{
    private JTextArea taMsg=new JTextArea("以下是你的聊天记录\n");
    private JTextField tfMsg=new JTextField("请输入你的信息");
    private JButton btSend=new JButton("发送");
    private JButton btClear=new JButton("清空");
    private Socket s=null;
    public Server(){
        this.setTitle("服务器端");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(taMsg,BorderLayout.CENTER);
        tfMsg.setBackground(Color.yellow);
        this.add(tfMsg,BorderLayout.NORTH);
        this.add(btClear,BorderLayout.EAST);
        btClear.addActionListener(this);
        this.add(btSend,BorderLayout.SOUTH);
        btSend.addActionListener((ActionListener) this);
        this.setSize(400,500);
        this.setVisible(true);
        try{
            ServerSocket ss=new ServerSocket(9999);
            s=ss.accept();
            new Thread(this).start();
        }catch(Exception e){

        }
    }
    public void run(){
        try{
            while (true) {
                InputStream is=s.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                String str=br.readLine();
                taMsg.append(str+"\n");
            }
        }catch(Exception e){}
    }
    public void actionPerformed(ActionEvent e){
        try{
            if(e.getSource()==btSend){
            OutputStream os=s.getOutputStream();
            PrintStream ps=new PrintStream(os);
            ps.println("服务器说："+tfMsg.getText());
            tfMsg.setText("");
            }else{
                tfMsg.setText("");
            }
        }catch(Exception ex){}
    }
    public static void main(String []args){
        Server server=new Server();
    }
    
}