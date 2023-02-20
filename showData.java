package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class showData//有参考
{
	// 设置与数据库连接的对象、sql语句、查询的结果集
	static Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	static String Sno;
	
	showData(String Sno)
	{
		String uri ="jdbc:mysql://localhost:3306/user?"+"user=root&password=123456&useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";
    	//连接数据库（加载驱动、建立连接）
		try 
		{
		   Class.forName("com.mysql.cj.jdbc.Driver");//加载JDBC-MySQL驱动程序
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
        
		//返回的数据List
        LinkedList<Stu_info> list =new LinkedList<>();
        try 
        {
        	PreparedStatement ps = ct.prepareStatement("select * from sc where Sno =? ");
    		ps.setString(1, Sno);
    		ResultSet rs =ps.executeQuery();
            
            while (rs.next())
            {
                Stu_info stu_info =new Stu_info();
                stu_info.setSno(rs.getString("Sno"));
                stu_info.setCourse(rs.getString("Cname"));
                stu_info.setGrade(rs.getString("Grade"));
                stu_info.setCredit(rs.getString("Credit"));
                list.add(stu_info);
                System.out.println("id:"+rs.getObject("Sno")+" "+"Cname:"+rs.getObject("Cname")+" "
                        +"Grade:"+rs.getString("Grade")+" "+"Credit:"+rs.getString("Credit"));
            }
            ct.close(); 
        }
        catch(Exception e1) 
        {
        	e1.printStackTrace();
        }
        
      //1，设定窗口
        JFrame frame =new JFrame("从mysql中获取数据并展示~");
        frame.setLocation(700,400);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


        //2,添加table
        JTable table =null;
        String [] index={"Sno","Course","Grade","Credit"};
        Object [][] data=new Object[list.size()][index.length];
        //3,向data中添加数据
        for (int i = 0; i < list.size(); i++) 
        {
            Stu_info stu_info =list.get(i);
            data[i][0]=stu_info.getSno();
            data[i][1]=stu_info.getCourse();
            data[i][2]=stu_info.getGrade();
            data[i][3]=stu_info.getCredit();

        }
        //4,创建一个默认的表格模型
        DefaultTableModel defaultModel = new DefaultTableModel(data, index);
        table=new JTable(defaultModel);
        table.setBackground(Color.cyan);
        table.setPreferredScrollableViewportSize(new Dimension(100, 80));//JTable的高度和宽度按照设定
        table.setFillsViewportHeight(true);

        //5，给表格设置滚动条
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(table);

        Font font = new Font("宋体", Font.BOLD, 13);

        //添加button
        JButton button =new JButton("查询");
        button.setBounds(50,10,50,30);

        //添加label
        JLabel label =new JLabel("点击按钮，查询MySQL数据库中的数据：");
        label.setFont(font);
        label.setBounds(1,10,240,30);

        //通过panel组合button，label
        JPanel panel =new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setSize(200,100);
        panel.add(label);
        panel.add(button);

        //6，添加表格、滚动条到容器中
        frame.add(panel, BorderLayout.NORTH);
        frame.setVisible(true);


        button.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                frame.add(jScrollPane,BorderLayout.CENTER);
                frame.setVisible(true);

            }
        });
	}

    //创建窗口，以列表展示从数据库中获取的数据
    public static void showFrame(List<Stu_info> list)
    {

        //1，设定窗口
        JFrame frame =new JFrame("从mysql中获取数据并展示~");
        frame.setLocation(700,400);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


        //2,添加table
        JTable table =null;
        String [] index={"Sno","Course","Grade","Credit"};
        Object [][] data=new Object[list.size()][index.length];
        //3,向data中添加数据
        for (int i = 0; i < list.size(); i++) 
        {
            Stu_info stu_info =list.get(i);
            data[i][0]=stu_info.getSno();
            data[i][1]=stu_info.getCourse();
            data[i][2]=stu_info.getGrade();
            data[i][3]=stu_info.getCredit();

        }
        //4,创建一个默认的表格模型
        DefaultTableModel defaultModel = new DefaultTableModel(data, index);
        table=new JTable(defaultModel);
        table.setBackground(Color.cyan);
        table.setPreferredScrollableViewportSize(new Dimension(100, 80));//JTable的高度和宽度按照设定
        table.setFillsViewportHeight(true);

        //5，给表格设置滚动条
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(table);

        Font font = new Font("宋体", Font.BOLD, 13);

        //添加button
        JButton button =new JButton("查询");
        button.setBounds(50,10,50,30);

        //添加label
        JLabel label =new JLabel("点击按钮，查询MySQL数据库中的数据：");
        label.setFont(font);
        label.setBounds(1,10,240,30);

        //通过panel组合button，label
        JPanel panel =new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setSize(200,100);
        panel.add(label);
        panel.add(button);

        //6，添加表格、滚动条到容器中
        frame.add(panel, BorderLayout.NORTH);
        frame.setVisible(true);


        button.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                frame.add(jScrollPane,BorderLayout.CENTER);
                frame.setVisible(true);

            }
        });

    }
    
 
   /* public static void main(String[] args) throws SQLException, ClassNotFoundException 
    {
        getDbData();
        showFrame(getDbData( ));
    }*/
}


