package main;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame implements MouseListener {
	public static final int WIDTH = 400; // ���ڵĿ�
	public static final int HEIGHT = 654; // ���ڵĸ�
	
	JLabel userLabel;// �û�����
	JLabel pwdLabel;// �û�����
	JTextField userText;// �û����ı������
	JPasswordField pwdText;// ���������
	JLabel loginLabel, signinLabel;// ��¼ȡ����ť
	JLabel surprise1;//�ʵ�1
	JLabel surprise2;//�ʵ�2
	JLabel titlelabel;//"�ɻ���ս"
	String username;// �ı����������
	String userpwd;// ���������
	
	public LoginFrame(){
		userLabel = new JLabel("�û���");
		userLabel.setBounds(80, 250, 120, 30);
		this.add(userLabel);

		pwdLabel = new JLabel("��   ��");
		pwdLabel.setBounds(80, 300, 120, 30);
		this.add(pwdLabel);
		
		// ����û��������
		userText = new JTextField(10);
		userText.setBounds(140, 250, 160, 25);
		// ��ȡ��꽹��
		userText.setFocusable(true);
		this.add(userText);

		// ������������
		pwdText = new JPasswordField();
		pwdText.setBounds(140, 300, 160, 25);
		pwdText.setFocusable(true);
		this.add(pwdText);

		titlelabel = new JLabel(new ImageIcon("E:\\����\\�ɻ���ս\\�ɻ���ս3.0\\src\\main\\start1.png"));
		titlelabel.setBounds(40, 80, 330, 101);
		titlelabel.setEnabled(false);
		titlelabel.setEnabled(true);
		this.add(titlelabel);
		
		loginLabel = new JLabel(new ImageIcon("E:\\����\\�ɻ���ս\\�ɻ���ս3.0\\src\\main\\login.png"));
		loginLabel.setBounds(70, 375, 130, 35);
		loginLabel.setEnabled(false);
		loginLabel.setEnabled(true);
		//����
		loginLabel.addMouseListener(this);
		this.add(loginLabel);
		
		signinLabel = new JLabel(new ImageIcon("E:\\����\\�ɻ���ս\\�ɻ���ս3.0\\src\\main\\signin.png"));
		signinLabel.setBounds(175, 375, 130, 35);
		signinLabel.setEnabled(false);
		signinLabel.setEnabled(true);
		//����
		signinLabel.addMouseListener(this);
		this.add(signinLabel);
				
		surprise1 = new JLabel(new ImageIcon("E:\\����\\�ɻ���ս\\�ɻ���ս3.0\\src\\main\\airplane.png"));
		surprise1.setBounds(30, 170, 49, 36);
		surprise1.setEnabled(false);
		surprise1.setEnabled(true);
		//����
		surprise1.addMouseListener(this);
		this.add(surprise1);
		
		surprise2 = new JLabel(new ImageIcon("E:\\����\\�ɻ���ս\\�ɻ���ս3.0\\src\\main\\bee.png"));
		surprise2.setBounds(280, 480, 58, 88);
		surprise2.setEnabled(false);
		surprise2.setEnabled(true);
		//����
		surprise2.addMouseListener(this);
		this.add(surprise2);
				
		//��������
		BackImage back = new BackImage();
		back.setBounds(0, 0, WIDTH, HEIGHT);
		this.add(back);
		//�����õĻ����Ű����
		this.setTitle("�ɻ���ս");
		this.setLayout(null);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("E:\\����\\�ɻ���ս\\�ɻ���ս3.0\\src\\main\\hero0.png").getImage());
		this.setVisible(true);
				
	}
	
	public static void main(String[] args) {
		LoginFrame frame = new LoginFrame();
	}
	
	String[] id = new String[999]; 
	String[] pwd = new String[999];
	int i = 0;
	
	public boolean find(String uname,String upwd){
		for(int i = 0;i < id.length;i++)
			if(id[i].equals(uname) && pwd[i].equals(upwd))
				return true;
		return false;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == loginLabel){ // ��¼
			username = userText.getText();
			char[] chPWD = pwdText.getPassword();
			userpwd = new String(chPWD);

			if (username.length() == 0) {
				JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��");
			} 
			if (find(username,userpwd)) {
				 // ��������������
				dispose();// �رյ�ǰ����
				JFrame frame = new JFrame("�ɻ���ս  developed by ��զ�����а�"); // ���ڶ���
				//	ShootGame game = new ShootGame(Integer.parseInt(score)); // ���
				ShootGame game = null;
				try {
					game = new ShootGame(getHighestScore(), username);
					System.out.print(username);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				frame.add(game); // �������ӵ�������
			    	Image image;
					image = new ImageIcon("E:\\����\\�ɻ���ս\\�ɻ���ս3.0\\src\\main\\icon.png").getImage();
					frame.setIconImage(image);

					frame.setSize(WIDTH, HEIGHT); // ���ô��ڵĿ�͸�
					frame.setAlwaysOnTop(true); // ����һֱ��������
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ����Ĭ�ϹرյĲ��������ڹر�ʱ�˳�����
					frame.setLocationRelativeTo(null); // ���ô��ڳ�ʼλ�ã�����)
					frame.setVisible(true); // ���ô���ɼ�

				game.action();
			} else {
				JOptionPane.showMessageDialog(null, "�û������������");
			}
		}
		
		if(arg0.getSource() == signinLabel){ // ע��
			username = userText.getText();
			char[] chPWD = pwdText.getPassword();
			userpwd = new String(chPWD);
			
			if (username.length() == 0 || userpwd.length() == 0) {
				JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��");
			}else{
				id[i] = username;
				pwd[i++] = userpwd;
//				System.out.println(id[i] + pwd[i++]);
				JOptionPane.showMessageDialog(null, "ע��ɹ�");
			}
		}
		
		if(arg0.getSource() == surprise1){ // �ʵ�
			JOptionPane.showMessageDialog(null, "С���ѣ��ҵ��ٶȻ�Խ��Խ��Ŷ��","���Ȼ����ң�", JOptionPane.INFORMATION_MESSAGE);
		}
		if(arg0.getSource() == surprise2){ // �ʵ�
			JOptionPane.showMessageDialog(null, "С���ѣ����Ǹ�����Ŷ��","���Ȼ����ң�", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public int getHighestScore() throws ClassNotFoundException, SQLException {
		String user = "root";
		String password = "zyh20000205";
		Connection con;
		Statement stat;
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://127.0.0.1:3306/planewar?" +
				"useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";
		con = DriverManager.getConnection(url, user, password);
		con = DriverManager.getConnection(url, user, password);
		stat = con.createStatement();//�ҵ����
		String sql = "select max(score) score from score;";//��ѯ���
		ResultSet rs=stat.executeQuery(sql);//��ѯ
//����ѯ���Ľ�����
		String score = "0";
		while (rs.next()) {
			score=rs.getString("score");
		}
		rs.close();
		stat.close();
		con.close();
		return Integer.parseInt(score);
	}

}

