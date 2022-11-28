package contactsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class BackendFunctions {
	static void AlbumCheck(Connection connection,int SongID,String album ) throws SQLException {//chcek if album exist, if not add it
		Statement stmt = connection.createStatement();
		ResultSet res2 = stmt.executeQuery("SELECT al_id as ID FROM album WHERE al_name="+" "+"'"+album+"'");
		int alID=-1;
		if(res2.next()) {//if album already exist
			alID=res2.getInt("ID");
			String command2="INSERT INTO album_song (al_id,song_id)VALUES(?,?)";
			PreparedStatement st2=connection.prepareStatement(command2);
			st2.setInt(1,alID); 
			st2.setInt(2,SongID); 
			int rows2=st2.executeUpdate();
			if(rows2>0) {
				System.out.println("new album and song relation has been inserted");

			}
		}else {//if album not exist then create a new album
			System.out.println("looks like your album is new input the name again to confirm");
			Scanner scan=new Scanner(System.in);
			String x1=scan.nextLine();
			System.out.println("what is the release date?");
			Scanner scan1=new Scanner(System.in);
			String x2=scan1.nextLine();
			System.out.println("who is the artist?");
			Scanner scan2=new Scanner(System.in);
			String artist=scan1.nextLine();//artist name x3
			Statement stmt3 = connection.createStatement();
			ResultSet res3 = stmt3.executeQuery("SELECT COUNT(*) AS COUNT FROM album");
			int alIDnew=-1;
			if(res3.next()) {
				int min = 1 ;
				int max = 999_999 ;
				alIDnew = ThreadLocalRandom.current().nextInt( min , max + 1 );
			}
			String command2="INSERT INTO album (al_id,al_name,al_releasedate)VALUES(?,?,?)";
			PreparedStatement st2=connection.prepareStatement(command2);
			st2.setInt(1,alIDnew); 
			st2.setString(2,x1);
			st2.setString(3,x2);
			int rows2=st2.executeUpdate();
			if(rows2>0) {
				System.out.println("new album has been inserted");

			}
			String command3="INSERT INTO album_song (al_id,song_id)VALUES(?,?)";
			PreparedStatement st3=connection.prepareStatement(command3);
			st3.setInt(1,alIDnew); 
			st3.setInt(2,SongID); 
			int rows3=st3.executeUpdate();
			if(rows3>0) {
				System.out.println("new album and song relation has been inserted");

			}
			BackendFunctions.ArtistCheck(connection, artist,alIDnew);
		}
	}
	static void ArtistCheck(Connection connection,String artist,int alID) throws SQLException {//chcek if artist exist, if not add it

		Statement stmt = connection.createStatement();
		ResultSet res2 = stmt.executeQuery("SELECT art_id as ID FROM artist WHERE art_name="+" "+"'"+artist+"'");
		int artID=-1;
		if(res2.next()) {//if artist already exist
			artID=res2.getInt("ID");
			String command2="INSERT INTO album_artist (al_id,art_id)VALUES(?,?)";
			PreparedStatement st2=connection.prepareStatement(command2);
			st2.setInt(1,alID); 
			st2.setInt(2,artID); 
			int rows2=st2.executeUpdate();
			if(rows2>0) {
				System.out.println("new album and artist relation has been inserted");

			}
		}else {//if artist not exist then create a new album
			System.out.println("looks like artist is new input name again to confirm");
			Scanner scan=new Scanner(System.in);
			String x1=scan.nextLine();
			System.out.println("what is the number of follower");
			Scanner scan1=new Scanner(System.in);
			int x2=scan1.nextInt();
			Statement stmt3 = connection.createStatement();
			ResultSet res3 = stmt3.executeQuery("SELECT COUNT(*) AS COUNT FROM artist");
			int artIDnew=-1;
			if(res3.next()) {
				int min = 1 ;
				int max = 999_999 ;
				artIDnew = ThreadLocalRandom.current().nextInt( min , max + 1 );
			}
			String command2="INSERT INTO artist (art_id,art_name,art_numfollower)VALUES(?,?,?)";
			PreparedStatement st2=connection.prepareStatement(command2);
			st2.setInt(1,artIDnew); 
			st2.setString(2,x1);
			st2.setInt(3,x2);
			int rows2=st2.executeUpdate();
			if(rows2>0) {
				System.out.println("new artist has been inserted");

			}
			String command3="INSERT INTO album_artist (al_id,art_id)VALUES(?,?)";
			PreparedStatement st3=connection.prepareStatement(command3);
			st3.setInt(1,alID); 
			st3.setInt(2,artIDnew); 
			int rows3=st3.executeUpdate();
			if(rows3>0) {
				System.out.println("new album and artist relation has been inserted");

			}
		}
	}
	static void addnewUser(Connection connection,String UserName,String playlist) throws SQLException {
	
	}


}
