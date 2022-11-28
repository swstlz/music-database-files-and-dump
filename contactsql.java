package contactsql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.Formatter;  
import java.util.UUID;

public class contactsql {
	public static void main(String[] args) throws SQLException {
			System.out.println("Welcome! please enter your user name and password");
			String jdbcURL="jdbc:postgresql://localhost:5432/cse412project";
			String username;
			String password;
			while(true) {
				System.out.println("1.login as User");
				System.out.println("2.login as SuperUser");
				System.out.println("0.i am new User");
				System.out.println("-1.quite");
				Scanner scanx=new Scanner(System.in);
				int choiceu=scanx.nextInt();
				if(choiceu==-1) {
					break;
				}
				if(choiceu==1) {
					while(true) {
						System.out.println("Enter username");
						Scanner scanu=new Scanner(System.in);  // Create a Scanner object
						String u = scanu.nextLine();
						System.out.println("Enter password");
						String p = scanu.nextLine();
						username=u;
						password=p;
						try {
							Connection check= DriverManager.getConnection(jdbcURL,username,password);
							break;
						}
						catch (SQLException e) {
							System.out.println("User name or Password error!");
						}
					}
					try {
						System.out.println("connected！Welcome to our music database (Normal User)");
						while(true) {
							System.out.println("what do you want?");
							System.out.println("1.Display table");
							System.out.println("2.Search Song by genre");
							System.out.println("3.Search Song and album by artist");
							System.out.println("4.Who are popular recently?");
							System.out.println("5.i want to contribute Song!");
							System.out.println("6.recommend playlist");
							System.out.println("7.i want to create playlist/add song to playlist");
							System.out.println("8.i want to follow playlist");
							System.out.println("0.quite");
							Scanner scan=new Scanner(System.in);  // Create a Scanner object
							int choice = scan.nextInt();  // Read user input
							if(choice==1) {
								System.out.println("what table do you want to check?");
								System.out.println("1.Song table");
								System.out.println("2.Artist table");
								System.out.println("3.Album");
								System.out.println("4.All playlist");
								
								Scanner scan1=new Scanner(System.in);  // Create a Scanner object
								int choice1 = scan.nextInt();  // Read user input for table
								if(choice1==1) {
									String x="Song";
									System.out.println("How many rows do u want to see?");
									Scanner scanmax=new Scanner(System.in); 
									int y=scanmax.nextInt();
									Connection connection= DriverManager.getConnection(jdbcURL,username,password);
									UserFunctions.displaytable(connection,x,y);
				
								}else if(choice1==2) {
									String x="artist";
									System.out.println("How many rows do u want to see?");
									Scanner scanmax=new Scanner(System.in); 
									int y=scanmax.nextInt();
									Connection connection= DriverManager.getConnection(jdbcURL,username,password);
									UserFunctions.displaytable(connection,x,y);
								}else if(choice1==3) {
									String x="album";
									System.out.println("How many rows do u want to see?");
									Scanner scanmax=new Scanner(System.in); 
									int y=scanmax.nextInt();
									Connection connection= DriverManager.getConnection(jdbcURL,username,password);
									UserFunctions.displaytable(connection,x,y);
								}else if(choice1==4) {
									String x="playlist";
									System.out.println("How many rows do u want to see?");
									Scanner scanmax=new Scanner(System.in); 
									int y=scanmax.nextInt();
									Connection connection= DriverManager.getConnection(jdbcURL,username,password);
									UserFunctions.displaytable(connection,x,y);
								}
							}else if(choice==2) {
								Connection connection= DriverManager.getConnection(jdbcURL,username,password);
								System.out.println("Input genre you want to search!");
								Scanner scan2=new Scanner(System.in);
								String x= scan2.nextLine();
								UserFunctions.searchGenre(connection, x);
							}else if(choice==4) {
								Connection connection= DriverManager.getConnection(jdbcURL,username,password);
								UserFunctions.pickTopFive(connection);
							}else if(choice==3) {
								System.out.println("What artist do you want to search");
								Scanner scan2=new Scanner(System.in);
								String x=scan2.nextLine();
								Connection connection= DriverManager.getConnection(jdbcURL,username,password);
								UserFunctions.searchArtist(connection, x);
							}else if(choice==6) {
								System.out.println("What genre do you want to listen today?");
								Scanner scan2=new Scanner(System.in);
								String x= scan2.nextLine();
								Connection connection= DriverManager.getConnection(jdbcURL,username,password);
								UserFunctions.RecommendPlaylist(connection, x);
							}else if(choice ==5) {
								System.out.println("Input Song Name");
								Scanner scan3=new Scanner(System.in);
								String x1=scan3.nextLine();
								System.out.println("Input Song genre");
								Scanner scan4=new Scanner(System.in);
								String x2=scan4.nextLine();
								System.out.println("Input Song length");
								Scanner scan5=new Scanner(System.in);
								String x3=scan5.nextLine();
								System.out.println("Input Song language");
								Scanner scan6=new Scanner(System.in);
								String x4=scan6.nextLine();
								System.out.println("Input Song number of stream");
								Scanner scan7=new Scanner(System.in);
								int x5=scan7.nextInt();
								System.out.println("Input Song album Name");
								Scanner scan8=new Scanner(System.in);
								String x6=scan8.nextLine();
								System.out.println("Input Song album artist name");
								Scanner scan9=new Scanner(System.in);
								String x7=scan8.nextLine();
								Connection connection= DriverManager.getConnection(jdbcURL,username,password);
								UserFunctions.insertSong(connection, x1, x2, x3, x4, x5, x6,x7);
							}else if(choice==7) {
								System.out.println("1.To create playlist, 2.To add song into playlist");
								Scanner scan3=new Scanner(System.in);
								int x1=scan3.nextInt();
								if(x1==1) {
									System.out.println("What is the Playlist Name?");
									Scanner scanpl=new Scanner(System.in);
									String plname=scanpl.nextLine();
									Connection connection= DriverManager.getConnection(jdbcURL,username,password);
									UserFunctions.UserCreatePlaylist(connection, plname, username);
								}else if(x1==2) {
									System.out.println("What is the Song Name?");
									Scanner scanSong=new Scanner(System.in);
									String SongName=scanSong.nextLine();
									System.out.println("What is the Playlist Name?");
									Scanner scanpl=new Scanner(System.in);
									String plname=scanpl.nextLine();
									Connection connection= DriverManager.getConnection(jdbcURL,username,password);
									UserFunctions.AddSongToPlaylist(connection, plname, SongName);
								}
							}else if(choice==8) {
								System.out.println("What playlist do you want to follow?");
								Scanner scan3=new Scanner(System.in);
								String x1=scan3.nextLine();
								Connection connection= DriverManager.getConnection(jdbcURL,username,password);
								UserFunctions.UserFollowPlaylist(connection, x1, username);
							}else if(choice==0) {
								break;
							}else {
								System.out.println("ERROR");
							}
						}
						}catch(SQLException e) {
							System.out.println("Error");
							e.printStackTrace();
						}
	
				}else if(choiceu==0) {
					while(true) {
						System.out.println("Create username");
						Scanner scanu=new Scanner(System.in);  // Create a Scanner object
						String u = scanu.nextLine();
						System.out.println("Create password");
						String p = scanu.nextLine();
						username="postgres";
						password="19991230";
						try {
							Connection check= DriverManager.getConnection(jdbcURL,username,password);
							NewUserFunctions.addNewUser(check,u, p);
							break;
						}
						catch (SQLException e) {
							System.out.println("Error");
							 e.printStackTrace(); //Not sure if this is the correct method name
						}
					}
					
					
				}else {
					while(true) {
						System.out.println("Enter username");
						Scanner scanu=new Scanner(System.in);  // Create a Scanner object
						String u = scanu.nextLine();
						System.out.println("Enter password");
						String p = scanu.nextLine();
						username=u;//"postgres";
						password=p;//"19991230";
						try {
							Connection check= DriverManager.getConnection(jdbcURL,username,password);
							break;
						}
						catch (SQLException e) {
							System.out.println("User name or Password error!");
							// e.printStackTrace(); //Not sure if this is the correct method name
						}
					}
					
					try {
					System.out.println("connected！Welcome to our music database(superUser)");
					while(true) {
						System.out.println("what do you want?");
						System.out.println("1.Display table");
						System.out.println("2.Search Song by genre");
						System.out.println("3.Delete playlist");
						System.out.println("4.Delete User");
						System.out.println("5.Delete Song");
						System.out.println("6.Delete album");
						System.out.println("7.Update Song Information");//todo
						System.out.println("8.Give user privaliage");//todo
						System.out.println("0.quite");//todo
						Scanner scan=new Scanner(System.in);  // Create a Scanner object
						int choice = scan.nextInt();  // Read user input
						if(choice==1) {
							System.out.println("what table do you want to check?");
							System.out.println("1.Song table");
							System.out.println("2.Artist table");
	
							Scanner scan1=new Scanner(System.in);  // Create a Scanner object
							int choice1 = scan.nextInt();  // Read user input for table
							if(choice1==1) {
								String x="Song";
								System.out.println("How many rows do u want to see?");
								Scanner scanmax=new Scanner(System.in); 
								int y=scanmax.nextInt();
								Connection connection= DriverManager.getConnection(jdbcURL,username,password);
								functions.displaytable(connection,x,y);
			
							}else if(choice1==2) {
								String x="artist";
								System.out.println("How many rows do u want to see?");
								Scanner scanmax=new Scanner(System.in); 
								int y=scanmax.nextInt();
								Connection connection= DriverManager.getConnection(jdbcURL,username,password);
								functions.displaytable(connection,x,y);
							}
						}else if(choice==2) {
							Connection connection= DriverManager.getConnection(jdbcURL,username,password);
							System.out.println("Input genre you want to search!");
							Scanner scan2=new Scanner(System.in);
							String x= scan2.nextLine();
							functions.searchGenre(connection, x);		
						}else if(choice==3) {
							Connection connection= DriverManager.getConnection(jdbcURL,username,password);
							System.out.println("Input playlistID you want to delete!");
							Scanner scan2=new Scanner(System.in);
							int x=scan2.nextInt();
							functions.deletePlaylist(connection, x);
						}else if(choice==4) {
							Connection connection= DriverManager.getConnection(jdbcURL,username,password);
							System.out.println("Input UserID you want to delete!");
							Scanner scan2=new Scanner(System.in);
							int x=scan2.nextInt();
							functions.deleteUser(connection, x);
						}else if(choice==5) {
							Connection connection= DriverManager.getConnection(jdbcURL,username,password);
							System.out.println("Input SingID you want to delete!");
							Scanner scan2=new Scanner(System.in);
							int x=scan2.nextInt();
							functions.deleteSong(connection, x);
						}else if(choice==6) {
							Connection connection= DriverManager.getConnection(jdbcURL,username,password);
							System.out.println("Input AlbumID you want to delete!");
							Scanner scan2=new Scanner(System.in);
							int x=scan2.nextInt();
							functions.deleteAlbum(connection, x);
						}else if(choice==7) {
							System.out.println("Input the song ID you want to update!");
							Scanner scan2=new Scanner(System.in);
							int x= scan2.nextInt();
							System.out.println("do you want to change 1.song name or 2.genre?");
							Scanner scan3=new Scanner(System.in);
							int y=scan2.nextInt();
							if(y==1) {
								System.out.println("input the new song name");
								Scanner scannewname=new Scanner(System.in);
								String y1=scannewname.nextLine();
								Connection connection= DriverManager.getConnection(jdbcURL,username,password);
								functions.updateSongName(connection, x, y1);
							}else if(y==2) {
								System.out.println("input the new song genre");
								Scanner scannewgenre=new Scanner(System.in);
								String y1=scannewgenre.nextLine();
								Connection connection= DriverManager.getConnection(jdbcURL,username,password);
								functions.updateSongGenre(connection, x, y1);
							}
						}else if(choice==8) {
							
						}else if(choice==0) {
							break;
						}else {
							System.out.println("ERROR");
						}
					}
					}catch(SQLException e) {
						System.out.println("Error");
						e.printStackTrace();
					}
				}
			}
	}
}
