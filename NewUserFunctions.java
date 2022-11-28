package contactsql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class NewUserFunctions {
	static void addNewUser(Connection connection, String Username,String password) throws SQLException {
		
		Statement st=connection.createStatement();
		ResultSet check = st.executeQuery("SELECT listener_id as ID FROM listener WHERE u_name="+" "+"'"+Username+"'");
		if(check.next()) {
			System.out.println("Username already exist, try other name!");
		}else {
			String command="create user"+" "+Username+" "+"password"+" "+"'"+password+"'";
			st.executeUpdate(command);
			ResultSet res3 = st.executeQuery("SELECT COUNT(*) AS COUNT FROM listener");
			int UserIDnew=-1;
			if(res3.next()) {
				int min = 1 ;
				int max = 999_999 ;
				UserIDnew = ThreadLocalRandom.current().nextInt( min , max + 1 );
			}
			String command2="INSERT INTO listener (listener_id,u_name)VALUES(?,?)";
			PreparedStatement st2=connection.prepareStatement(command2);
			st2.setInt(1,UserIDnew); 
			st2.setString(2,Username);
		
			int rows2=st2.executeUpdate();
			if(rows2>0) {
				System.out.println("new user created!");
			}
		}
	}
}
