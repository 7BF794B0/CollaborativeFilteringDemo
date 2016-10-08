import java.sql.*;

class DBController
{
	private String mUrl;
	private String mName;
	private String mPassword;
	private Connection mConnection;

	/**
	 * @param url      URL database consists of a protocol:protocol://[host]:[port database]/[DB], and other information
	 * @param name     User
	 * @param password Password
	 */
	DBController (String url, String name, String password)
	{
		mUrl = url;
		mName = name;
		mPassword = password;
		mConnection = null;
	}

	void Connect ()
	{
		try
		{
			// Load driver
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver connected");

			// Create connection
			mConnection = DriverManager.getConnection(mUrl, mName, mPassword);
			System.out.println("Connection established");
		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param data The data in json format to be added to the database
	 */
	void Insert (String data)
	{
		try
		{
			PreparedStatement statement = mConnection.prepareStatement("INSERT INTO person(data) values(cast(? as json))");
			statement.setString(1, data);
			statement.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
