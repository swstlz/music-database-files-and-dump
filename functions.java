package contactsql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.UUID;

public class functions {
	static void displaytable(Connection connection,String TableName,int y) throws SQLException {
		DBTablePrinter.printTable(connection, TableName,y);
		connection.close();
	  }
	static void pickTopFive(Connection connection) throws SQLException {
		Statement st=connection.createStatement();
		String command="SELECT art_name, art_numfollower FROM artist order by art_numfollower DESC limit 5";
		ResultSet res =st.executeQuery(command);
		DBTablePrinter.printResultSet(res);
		st.close();
		connection.close();
	  }
	static void searchGenre(Connection connection,String GenretName) throws SQLException {
		
		Statement st=connection.createStatement();
		
		String command="SELECT s_name, s_genre, s_length, s_numofstreams FROM song WHERE s_genre ="+" "+"'"+GenretName+"'";
		ResultSet res =st.executeQuery(command);
		if(!res.next()) {
			System.out.println("This genre is not found");
		}else {
			DBTablePrinter.printResultSet(res);
		}
		st.close();
		connection.close();		
	}
	//Delete Query: DELETE FROM playlist WHERE pl_numfollow < 150
	static void DeleteBadPlaylist(Connection connection,int input) throws SQLException {
		String command="DELETE FROM playlist WHERE pl_numfollow < ?";
		PreparedStatement st=connection.prepareStatement(command);
		st.setInt(1,input);
		int rows=st.executeUpdate();
		if(rows>0) {
			System.out.println("The Playlist has been deleted");
		}
		connection.close();
	}
	static void deletePlaylist(Connection connection,int plID) throws SQLException {
		String command="DELETE FROM playlist WHERE pl_id = ?";
		PreparedStatement st=connection.prepareStatement(command);
		st.setInt(1,plID);
		int rows=st.executeUpdate();
		if(rows>0) {
			System.out.println("The Playlist has been deleted");
		}
		connection.close();
	}
	static void deleteUser(Connection connection,int uID) throws SQLException {
		String command="DELETE FROM listener WHERE listener_id = ?";
		PreparedStatement st=connection.prepareStatement(command);
		st.setInt(1,uID);
		int rows=st.executeUpdate();
		if(rows>0) {
			System.out.println("The User has been deleted");
		}
		connection.close();
	}
	static void deleteSong(Connection connection,int sID) throws SQLException {
		String command="DELETE FROM song WHERE song_id = ?";
		PreparedStatement st=connection.prepareStatement(command);
		st.setInt(1,sID);
		int rows=st.executeUpdate();
		if(rows>0) {
			System.out.println("The song has been deleted");
		}
		connection.close();
	}
	static void deleteAlbum(Connection connection,int alID) throws SQLException {
		String command="DELETE FROM album WHERE al_id = ?";
		PreparedStatement st=connection.prepareStatement(command);
		st.setInt(1,alID);
		int rows=st.executeUpdate();
		if(rows>0) {
			System.out.println("The album has been deleted");
		}
		connection.close();
	}
	static void updateSongName(Connection connection, int songID, String SongNewName) throws SQLException {
		String command="UPDATE song SET s_name ="+" "+"'"+SongNewName+"'"+" "+"WHERE song_id = ?";
		PreparedStatement st=connection.prepareStatement(command);
		st.setInt(1,songID);
		int rows=st.executeUpdate();
		if(rows>0) {
			System.out.println("The song has been updated");
		}
		connection.close();
	 
	}
	static void updateSongGenre(Connection connection, int songID, String SongNewG) throws SQLException {
		String command="UPDATE song SET s_genre ="+" "+"'"+SongNewG+"'"+" "+"WHERE song_id = ?";
		PreparedStatement st=connection.prepareStatement(command);
		st.setInt(1,songID);
		int rows=st.executeUpdate();
		if(rows>0) {
			System.out.println("The song has been updated");
		}
		connection.close();
	 
	}
	
	
	}

