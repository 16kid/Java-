package Model;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class addCourse extends JFrame implements ActionListener
{
	JLabel label,label2,label3;
	JTextField textField1,textField2,textField3;
	JButton button;
	
	Connection con;
	Statement sql;
	
	// ����������������ʱ�����ã����ڲ��ԣ�	
	public static void main(String[] args)
	{		
		addCourse stu = new addCourse();
	}
	
	addCourse()
	{
		String uri ="jdbc:mysql://localhost:3306/user?"+"user=root&password=123456&useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";
    	//�������ݿ⣨�����������������ӣ�
		try 
		{
		   Class.forName("com.mysql.cj.jdbc.Driver");//����JDBC-MySQL��������
		}
		catch(Exception e) {}
		try 
		{
			con = DriverManager.getConnection(uri);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
		
		//��ǩ
		label = new JLabel("��Ҫ��ӵĿγ̺�");
		label.setBounds(100, 100, 200, 50);
		label2 = new JLabel("��Ҫ��ӵĿγ���");
		label2.setBounds(100, 200, 200, 50);
		label3 = new JLabel("��Ҫ��ӵĿγ�ѧ��");
		label3.setBounds(100, 300, 200, 50);
		
		label.setFont(new Font("����", 1, 20));
		label2.setFont(new Font("����", 1, 20));
		label3.setFont(new Font("����", 1, 20));
		
		this.add(label);
		this.add(label2);
		this.add(label3);

		
		//�ı���
		textField1 = new JTextField();
		textField2 = new JTextField();
		textField3 = new JTextField();		
		
		this.add(textField1);
		textField1.setBounds(300, 100, 150, 50);		
		this.add(textField2);
		textField2.setBounds(300, 200, 150, 50);
		this.add(textField3);
		textField3.setBounds(300, 300, 150, 50);
		
		//��ť
		button = new JButton("���");
		button.setBounds(225, 400, 100, 50);	
		button.setFont(new Font("����", 1, 25));
		this.add(button);
		button.addActionListener(this);
		
		// ���ò��ֹ��������ղ��֣�
		this.setLayout(null);
		
		// ���ô��ڱ���
		this.setTitle("��ӿγ�");
		
		// ���ô��ڴ�С
		this.setSize(550, 600);
				
		// ���ô��ڵ���ʼλ��
		this.setLocationRelativeTo(null);//����Ļ�м���ʾ(������ʾ)
		
		// �������ص�ǰ���ڣ����ͷŴ���ռ�е�������Դ
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// ��ʾ����
		this.setVisible(true);
		this.setResizable(true);//��ʾ���ɵĴ���������ɸı��С	
	}	
		
	public void actionPerformed(ActionEvent e) 
	{
		String addCno = textField1.getText();
        String addCname = textField2.getText();
        String addCredit = textField3.getText();	     

        try 
        {
           sql=con.createStatement();            
                     
           String  strSQL="insert into course values('"+addCno+"','"+addCname+"','"+addCredit+"')";
    	   String  strSQL1="(select * from  course where cname='"+addCname+"' )";

    	   if(!addCname.trim().equals("")&&!addCno.trim().equals("")&&!addCredit.trim().equals(""))
    	   {
    		   ResultSet rs1=sql.executeQuery(strSQL1);
    		 
        	   if(rs1.next())	 
        	   {
        		   JOptionPane.showMessageDialog(null,"�ÿγ��Ѵ���");     
        	   }
        	   else 
        	   {
	        	   int rs=sql.executeUpdate(strSQL);
	        		  
	        	   if(rs==1) 
	        	   {
	        		   JOptionPane.showMessageDialog(null,"�γ���ӳɹ�");
	        	   }
	        	   else
	        	   {
	        		   JOptionPane.showMessageDialog(null,"�γ����ʧ��");
	        	   }
        	   }
    	    }
		    else
		    { 
			    JOptionPane.showMessageDialog(null,"�γ���Ϣ�����������������룡");
		    }	  	  
        }
        catch(Exception e1) 
        {
        	e1.printStackTrace();
    	}
     }	
}
