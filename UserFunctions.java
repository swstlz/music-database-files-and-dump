package contactsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class UserFunctions {
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
	static void insertSong(Connection connection,String SongName,String SongGenre,String SongLen,String SongLan ,int numofstreams,String album,String artist) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet res = stmt.executeQuery("SELECT COUNT(*) AS COUNT FROM Song");
		int SongID=-1;
		if(res.next()) {
			int min = 1 ;
			int max = 999_999 ;
			SongID = ThreadLocalRandom.current().nextInt( min , max + 1 );
		}
		String command="INSERT INTO song (song_id, s_name, s_genre, s_length, s_language, s_numofstreams)VALUES(?,?,?,?,?,?)";
		PreparedStatement st=connection.prepareStatement(command);
				st.setInt(1,SongID); 
				st.setString(2,SongName);
				st.setString(3,SongGenre);
				st.setString(4,SongLen);
				st.setString(5,SongLan);
				st.setInt(6,numofstreams);

				//+ ", "+ SongName+", "+SongGenre + ", " + SongLen + ", "+ SongLan+", "+numofstreams+")";
		int rows=st.executeUpdate();
		if(rows>0) {
			System.out.println("new song has been inserted");
		}
		BackendFunctions.AlbumCheck(connection, SongID, album);
		//add the album of the new song
		//Statement stmt2 = connection.createStatement();
		connection.close();
		
	}
	static void RecommendSong(Connection connection, ArrayList<String>genre) throws SQLException {
		Statement st=connection.createStatement();
		System.out.println("Here is your recommended songs!");
		for(int i=0;i<genre.size();i++) {
			String command="SELECT s_name, s_genre FROM song WHERE s_genre ="+" "+"'"+genre.get(i)+"'"+" "+ "order by s_numofstreams DESC limit 3";
			ResultSet res =st.executeQuery(command);
			while(res.next()) {
				System.out.println(res.getString("s_name")+" "
						+res.getString("s_genre"));
			}
		}
		st.close();
		connection.close();
	  }
	static void updateAlbumArtist(Connection connection,String artist, String album) {
		
	}
	//this function return a table where contain listners who created a playlist that has most number of songs that user loves
	static void recommendedUser(Connection connection) {
		
	}
	//user can create username and add him to the listener table, 
	//user can add some songs to the playlist (this will update the playlist_song table) ask username and playlist name
	static void AddSongToPlaylist(Connection connection,String playlistName,String SongName) throws SQLException {
		Statement st=connection.createStatement();
		int SongID=-1;
		ResultSet res2 = st.executeQuery("SELECT song_id as ID FROM song WHERE s_name="+" "+"'"+SongName+"'");
		if(res2.next()) {
			SongID=res2.getInt("ID");
			
		}else {
			System.out.println("looks like we dont have the Song in the database, please add it first!");
			return;
		}
		int PlayID=-1;
		ResultSet res3 = st.executeQuery("SELECT pl_id as ID FROM playlist WHERE pl_name="+" "+"'"+playlistName+"'");
		if(res3.next()) {
			PlayID=res3.getInt("ID");
		}else {
			System.out.println("looks like we dont have the playlist in the database, please add it first!");
			return;
		}
		String command2="INSERT INTO playlist_song (song_id,pl_id)VALUES(?,?)";
		PreparedStatement st2=connection.prepareStatement(command2);
		st2.setInt(1,SongID); 
		st2.setInt(2,PlayID);
		int rows2=st2.executeUpdate();
		if(rows2>0) {
			System.out.println("new Song "+SongName+" has been inserted into playlist "+playlistName);
		}
		st.close();
		connection.close();	
		
	}
	static void UserCreatePlaylist(Connection connection,String playlistName,String UserName) throws SQLException {
		Statement st=connection.createStatement();
		int plID=-1;
		ResultSet res2 = st.executeQuery("SELECT pl_id as ID FROM playlist WHERE pl_name="+" "+"'"+playlistName+"'");
		if(res2.next()) {
			plID=res2.getInt("ID");
			System.out.println("Lookslike the playlist already existed!, do you want"
					+ "to be co-creator of this playlist? 1.yes, 0.no");
			Scanner scanx=new Scanner(System.in);
			int choice=scanx.nextInt();
			if(choice==0) {
				System.out.println("Try other name, this playlist name is taken");
				return;
			}else if(choice==1){
				ResultSet res4= st.executeQuery("SELECT listener_id as ID FROM listener WHERE u_name="+" "+"'"+UserName+"'");
				int u_id=-1;
				if(res4.next()) {
					u_id=res4.getInt("ID");
					System.out.println(plID);
				}
				String command3="INSERT INTO listener_create_playlist (listener_id,pl_id)VALUES(?,?)";
				PreparedStatement st3=connection.prepareStatement(command3);
				st3.setInt(1,u_id); 
				st3.setInt(2,plID);
				int rows3=st3.executeUpdate();
				if(rows3>0) {
					System.out.println("new playlist and listener relation has been created");

				}
			}
	
			
		}else {
			ResultSet res3 = st.executeQuery("SELECT COUNT(*) AS COUNT FROM playlist");
			int plIDnew=-1;
			if(res3.next()) {
				int min = 1 ;
				int max = 999_999 ;
				plIDnew = ThreadLocalRandom.current().nextInt( min , max + 1 );
			}
			System.out.println(plIDnew);
			String command2="INSERT INTO playlist (pl_id,pl_name,pl_numfollow)VALUES(?,?,?)";
			PreparedStatement st2=connection.prepareStatement(command2);
			st2.setInt(1,plIDnew); 
			st2.setString(2,playlistName);
			st2.setInt(3,0);
			int rows2=st2.executeUpdate();
			if(rows2>0) {
				System.out.println("new playlist has been added");
			}
			ResultSet res4= st.executeQuery("SELECT listener_id as ID FROM listener WHERE u_name="+" "+"'"+UserName+"'");
			int u_id=-1;
			if(res4.next()) {
				u_id=res4.getInt("ID");
			}
			String command3="INSERT INTO listener_create_playlist (listener_id,pl_id)VALUES(?,?)";
			PreparedStatement st3=connection.prepareStatement(command3);
			st3.setInt(1,u_id); 
			st3.setInt(2,plIDnew);
			int rows3=st3.executeUpdate();
			if(rows3>0) {
				System.out.println("new playlist and listener relation has been created");

			}
		}
		st.close();
		connection.close();	
	}
	private static Scanner nextInt() {
		// TODO Auto-generated method stub
		return null;
	}
	static void UserFollowPlaylist(Connection connection,String playlistName,String UserName) throws SQLException {
		Statement st=connection.createStatement();
		int plID=-1;
		ResultSet res2 = st.executeQuery("SELECT pl_id as ID FROM playlist WHERE pl_name="+" "+"'"+playlistName+"'");
		if(!res2.next()) {
			System.out.println("Sorry, no such playlist!");
			return;
			
		}else {
			plID=res2.getInt("ID");
			ResultSet numfollower= st.executeQuery("SELECT pl_numfollow as num FROM playlist WHERE pl_id="+" "+plID);
			int newfollower=0;
			if(numfollower.next()) {
				newfollower=numfollower.getInt("num")+1;
			}
			String command="UPDATE playlist SET pl_numfollow = ? WHERE pl_id = ?";
			PreparedStatement st1=connection.prepareStatement(command);
			st1.setInt(1,newfollower);
			st1.setInt(2,plID);
			int rows=st1.executeUpdate();
			if(rows>0) {
				System.out.println("followed!");
			}
			Statement st2=connection.createStatement();
			ResultSet res4= st2.executeQuery("SELECT listener_id as ID FROM listener WHERE u_name="+" "+"'"+UserName+"'");
			int u_id=-1;
			if(res4.next()) {
				u_id=res4.getInt("ID");
			}
			String command3="INSERT INTO listener_follow_playlist (listener_id,pl_id)VALUES(?,?)";
			PreparedStatement st3=connection.prepareStatement(command3);
			st3.setInt(1,u_id); 
			st3.setInt(2,plID);
			int rows3=st3.executeUpdate();
			if(rows3>0) {
				System.out.println("new playlist and listener relation has been created");

			}
		}
		st.close();
		connection.close();	
	}

	static void RecommendPlaylist(Connection connection,String genre) throws SQLException {
		Statement st=connection.createStatement();
		
		String command="select pl_name from playlist,( select id from (select max(count) from "
				+ "(select pl_id as id,count(*)as COUNT from Song,playlist_song where s_genre="+"'"+genre+"'"
				+ "and playlist_song.Song_id=song.song_id Group by pl_id) as t1) as t2,(select pl_id as id,"
				+ "count(*) as COUNT from Song,playlist_song where s_genre="+"'"+genre+"'"+"and playlist_song.Song_id=song."
				+ "song_id Group by pl_id) as t3 where count=max)as t4 where id=pl_id\r\n"
				+ "";

		ResultSet res =st.executeQuery(command);
		if(res.next()) {
			System.out.println("Your may love this playlist!"+" "+res.getString("pl_name"));
		}else {
			System.out.println("not found");
		}
		st.close();
		connection.close();		
	}
	static void searchArtist(Connection connection,String artist) throws SQLException {
		Statement st=connection.createStatement();
		String command="select distinct song.s_name,album.al_name from artist,album_artist,album,song,album_song where artist.art_id=album_artist.art_id \r\n"
				+ "and album_artist.al_id=album.al_id and song.song_id=album_song.song_id and album.al_id=album_song.al_id and artist.art_name="+"'"+artist+"'";
		ResultSet res=st.executeQuery(command);
		DBTablePrinter.printResultSet(res);
		if(!res.next()) {
			System.out.println("not found");
		}
		st.close();
		connection.close();		
	}
	
}
