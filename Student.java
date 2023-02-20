package Model;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class Student extends JFrame implements ActionListener
{
	
	// �������	
	JPanel panel;                   
	JLabel label,label2,label3,label4;                  
	JButton button1,button2,button3,button4;// ������ť
	//JTable jtable_1, jtable_2 = null;                                      // ���
	//DefaultTableModel model_1, model_2 = null;                            
	//JScrollPane jsp_1, jsp_2 = null;                                    // ������
	
	
	// �洢ѧ����ѧ�ź͸��Ƶĳɼ�
	String Sno;//ѧ��
	String Name;//����	
	String course;//�γ�
	String Sex;//�Ա�
	String grade;//�ɼ�

	
	static String uri = "jdbc:mysql://localhost:3306/user?"+
    "user=root&password=123456&useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";
	
	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
		
	// ����������������ʱ�����ã����ڲ��ԣ�	
	public static void main(String[] args)
	{		
		Student stu = new Student("������","2021001");
		//Student stu = new Student("Сľͬѧ","123456789");
	}
	
	
	// ���캯��������Ϊ��¼ѧ����������ѧ�ţ�	
	Student(String name,String sno)
	{		
		Sno = sno;//�õ���¼ѧ��ѧ�ţ����ں����ѯ�ɼ���
		Name=name;//�õ���¼ѧ������
		
		//�������ݿ⣨�����������������ӣ�
		try 
		{
		   Class.forName("com.mysql.cj.jdbc.Driver");//����JDBC-MySQL��������
		}
		catch(Exception e) {}
		try 
		{
			ct = DriverManager.getConnection(uri);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
			
		// �������		
		// ���
		panel = new JPanel();
		panel.setBounds(125, 45, 650, 350);
		panel.setBackground(Color.white);
		
		
		// ��ǩ		
		label = new JLabel("WELCOME");
		label.setBounds(100, 10, 500, 200);
		label2 = new JLabel(name);
		label2.setBounds(35, 5, 125, 50);
		label3 = new JLabel("��ӭ��½���������γ�ϵͳ");
		label3.setBounds(100, 60, 500, 200);
		label4 = new JLabel(sno);
		label4.setBounds(150, 5, 125, 50);

		label.setFont(new Font("Times New Roman", 1, 25));
		label2.setFont(new Font("����", 1, 25));
		label3.setFont(new Font("����", 1, 25));
		label4.setFont(new Font("����", 1, 25));
		
		// ������ť		
		button1 = new JButton("�ҵ���Ϣ");
		button2 = new JButton("�ҵĿγ�");
		button3 = new JButton("�޸�����");
		button4 = new JButton("�˳�ϵͳ");
		button1.setFont(new Font("����", 1, 20));//protected Font(string name, int style, int size)  int BOLD == 1,�Ӵ�
		button2.setFont(new Font("����", 1, 20));
		button3.setFont(new Font("����", 1, 20));
		button4.setFont(new Font("����", 1, 20));
		
		// ���ö�������		
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		
		this.add(button1);
		button1.setBounds(150, 400, 150, 50);
		this.add(button2);
		button2.setBounds(150, 500, 150, 50);
		this.add(button3);
		button3.setBounds(550, 400, 150, 50);
		this.add(button4);
		button4.setBounds(550, 500, 150, 50);
		
		// �������		
		this.add(label2);		
		this.add(label4);
		panel.add(label);
		panel.add(label3);
		
		// �������뵽����		
		this.add(panel);
		
		// ���ò��ֹ��������ղ��֣�
		this.setLayout(null);
		
		// ���ô��ڱ���
		this.setTitle("ѧ��ҳ��");
		
		// ���ô��ڴ�С
		this.setSize(900, 700);
				
		// ���ô��ڵ���ʼλ��
		this.setLocationRelativeTo(null);//����Ļ�м���ʾ(������ʾ)
		
		// ���õ��رմ���ʱ������Ҳ����
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// ��ʾ����
		this.setVisible(true);
		this.setResizable(true);//��ʾ���ɵĴ���������ɸı��С		
	}
	
	
	// �����¼���������
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "�ҵ���Ϣ")
		{
			studentInfo stuInfo = new studentInfo(Name,Sno,Sex);
		}
		else if(e.getActionCommand() == "�ҵĿγ�")
		{		
			showData sd = new showData(Sno);
		}
		else if(e.getActionCommand() == "�޸�����")
		{
			changepassword_1 cp = new changepassword_1();  // ѧ��ΪSno��ѧ���޸����룬�����޸��������
		}
		else if(e.getActionCommand() == "�˳�ϵͳ")
		{
			System.exit(0);        // �����������д��ڹر�
		}	
	}
}