package com.zy.web.dao;

/*
 * @author zy
 * @date Feb 3, 2016
 */

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;

public class SqlManager
{
	private static SqlManager manager = null; // 静�?成员变量，支持单态模�?
	private PropertyResourceBundle bundle; // 配置资源文件
	private static String jdbcDrive = null; // JDBC驱动类型
	private String DBhost = ""; // 数据库主机地�?
	private String DBname = ""; // 数据库名
	private String DBprot = ""; // 数据库端�?
	private String DBuser = ""; // 数据库用户名
	private String DBpasswd = ""; // 数据库密�?
	private String strcon = null; // 连接字符�?

	private Connection conn = null; // 连接对象
	private PreparedStatement pstm = null;
	private CallableStatement cstm = null;

	/**
	 * 私有构�?函数,不可实例�?
	 * 
	 * @throws IOException
	 */
	private SqlManager() throws IOException
	{
		// 读取配置文件
		bundle = new PropertyResourceBundle(SqlManager.class
				.getResourceAsStream("Config.properties"));
		this.DBhost = getString("DBhost"); // 读取主机�?
		this.DBname = getString("DBname"); // 读取用户�?
		this.DBprot = getString("DBport"); // 读取端口
		this.DBuser = getString("DBuser"); // 读取用户
		this.DBpasswd = getString("DBpassword"); // 读取密码
		// 设置mysql数据库的驱动程序和连接字�?
		jdbcDrive = "com.mysql.jdbc.Driver";
		strcon = "jdbc:mysql://" + DBhost + ":" + DBprot + "/" + DBname;
	}

	/**
	 * 读取配置文件中的�?
	 * 
	 * @param key
	 *            配置文件的key
	 * @return key对应的�?
	 */
	private String getString(String key)
	{
		return this.bundle.getString(key);
	}

	/**
	 * 单�?模式获取实例
	 * 
	 * @return SqlManager对象
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public static SqlManager createInstance() throws IOException, ClassNotFoundException
	{
		if (manager == null)
		{
			manager = new SqlManager();
			manager.initDB();
		}
		return manager;
	}

	/**
	 * 初始化连接参数，由指定的DBType生成
	 * 
	 * @throws ClassNotFoundException
	 */
	public void initDB() throws ClassNotFoundException
	{
		Class.forName(jdbcDrive);
	}

	/**
	 * 连接数据�?
	 * 
	 * @throws SQLException
	 */
	public void connectDB() throws SQLException
	{
		conn = DriverManager.getConnection(strcon, DBuser, DBpasswd); // 获取连接
		conn.setAutoCommit(false); // 设置自动提交为false
	}

	/**
	 * 断开数据�?
	 * 
	 * @throws SQLException
	 */
	public void closeDB() throws SQLException
	{
		if (pstm != null)
		{
			pstm.close();
		}
		if (cstm != null)
		{
			cstm.close();
		}
		if (conn != null)
		{
			conn.close();
		}
	}

	/**
	 * 设置PrepareStatement对象中Sql语句中的参数
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数列表
	 * @throws SQLException
	 */
	private void setPrepareStatementParams(String sql, Object[] params)
			throws SQLException
	{
		pstm = conn.prepareStatement(sql); // 获取对象
		if (params != null)
		{	
			for (int i = 0; i < params.length; i++) // 遍历参数列表填充参数
			{
				pstm.setObject(i + 1, params[i]);
			}
		}
	}
	
	private void setPrepareStatement(String sql) throws SQLException{
		pstm = conn.prepareStatement(sql);
	}

	/**
	 * 执行查询
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数列表
	 * @return 返回ResultSet类型的查询结�?
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql, Object[] params)
			throws SQLException
	{ // 执行查询数据库接�?
		ResultSet rs = null;
		manager.setPrepareStatement(sql); // 填充参数
		rs = pstm.executeQuery(); // 执行查询操作
		return rs;
	}
	
	public ResultSet executeQueryHaveParameter(String sql, Object[] params)
			throws SQLException
	{ // 执行查询数据库接�?
		ResultSet rs = null;
		manager.setPrepareStatementParams(sql, params); // 填充参数
		rs = pstm.executeQuery(); // 执行查询操作
		return rs;
	}

	/**
	 * 更新数据库操�?
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数列表
	 * @return 执行操作的结�?
	 * @throws SQLException
	 */
	public boolean executeUpdate(String sql, Object[] params)
			throws SQLException // 执行无返回数据的数据查询，返回�?是被改变的书库的数据库项�?
	{
		boolean result = false;
		manager.setPrepareStatementParams(sql, params); // 填充参数
		pstm.executeUpdate(); // 执行更新
		manager.commitChange();
		result = true;
		return result;
	}

	/**
	 * 提交信息到数据库
	 * 
	 * @throws SQLException
	 */
	private void commitChange() throws SQLException
	{
		conn.commit();
	}
}
