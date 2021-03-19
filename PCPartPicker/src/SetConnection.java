import java.sql.*;

public class SetConnection {
	Connection connect;
	public Connection connectDb()throws Exception{
		try {
			connect=(Connection) DriverManager.getConnection(Configuration.dbname, Configuration.uname , Configuration.psw);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return connect;
	}
}
