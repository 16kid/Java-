package Model;

import java.sql.*;
public class startQuery//可删除
{
	String Sno,Course,Grade,Credit;
	String colnames[] = {"学号","课程名","成绩","学分"};	
	String record[][];//查询到的记录
	
	// 数据库驱动
	static String uri = "jdbc:mysql://localhost:3306/user?"+
    "user=root&password=123456&useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";
	
	
	// 设置与数据库连接的对象、sql语句、查询的结果集
	static Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public String []getColumnName()
	{
		if(colnames==null)
		{
			System.out.println("先查询记录");
			return null;
		}
		return colnames;
	}
	public String [][]getRecord()
	{
		startQuery();
		return record;
	}
	public void startQuery()
	{
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
		
		try
		{		
			ps = ct.prepareStatement("select * from sc where Sno =? ",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);	// 将编译好的SQL语句赋值给ps		
			ps.setString(1, Sno);			// 给?赋值，用setString将设置SQL语句按照学号Sno查询			
			rs = ps.executeQuery();	        // ResultSet结果集,将对应学号的每一行信息放在ResultSet中
			
			ResultSetMetaData metaData = rs.getMetaData();//rs调用getMetaData()方法返回一个ResultSetMetaData对象
			int columnCount = metaData.getColumnCount();//字段数目
			colnames = new String[columnCount];
			for(int i=1;i<=columnCount;i++)
			{
				colnames[i-1] = metaData.getColumnName(i);
			}
			rs.last();//将游标移动到ResultSet对象rs的最后一行
			int recordAmount = rs.getRow();//结果集中的记录数目
			record = new String[recordAmount][columnCount];
			int i=0;
			rs.beforeFirst();//将游标移动到ResultSet的初始位置，即在第一行之前
			
			while(rs.next())
			{
				for(int j=1;j<=columnCount;j++)
				{
					record[i][j-1] = rs.getString(j);//将第i条记录放入二维数组的第i行
				}
				i++;
			}
		}
		catch (Exception e1) 
		{					
			e1.printStackTrace();
		}
	}
}