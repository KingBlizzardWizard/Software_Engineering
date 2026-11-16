
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FriendManager {
    private static final String DB_URL = "jdbc:sqlite:userdata.db"; 
    public static String query; 
    
    


        //this cant check for duplicate friends, might be easier
        //to implement in the main method with some other checks

        // takes in a user1 and user2
        //adds a connection from user1 to user2 in friends with status PENDING
        //also adds a connection from user2 to user1 with status PENDING
    public static void sendFriendRequest(int user1, int user2){
        // adds a connection between user1 and user2 with status PENDING
        try (var con = DriverManager.getConnection(DB_URL)){
            System.out.println("Connected to friend database, preparing query");
            query =  "INSERT INTO friends (user1, user2, status) VALUES (?, ?, ?)";
            try (PreparedStatement stmt =
                    con.prepareStatement(query)) {
                        stmt.setInt(1, user1);
                        stmt.setInt(2, user2);
                        // not sure if this needs to be a ? or can just be PENDING, but doing
                        // it anyways to be safe
                        stmt.setString(3,"PENDING");
                        stmt.executeUpdate();

                        //adds a corresponding connection between user2 and user1
                        stmt.setInt(1,user2);
                        stmt.setInt(2,user1);
                        stmt.setString(3,"PENDING");
                        stmt.executeUpdate();
                    } catch (SQLException e){System.out.println(e.getMessage());}
            } catch(SQLException e){System.out.println(e.getMessage());}
    }


    // changes the status between user1 to user2 and user2 to user1 to ACCEPTED
    // denying should possibly be handled in the main method, 
    // might need a new method here to handle deleting the PENDING status from
    // the database however

    // potential privacy option needed to block a user from sending friend requests.

    public static void acceptFriendRequest(int user1,int user2){

        query = "UPDATE friends SET status = ? WHERE user1 = ? AND user2 = ?";
        try (var con = DriverManager.getConnection(DB_URL)){
            try(PreparedStatement stmt = con.prepareStatement(query)){
                stmt.setString(1,"ACCEPTED");
                // sets user1 to user2 status as ACCEPTED
                stmt.setInt(2,user1);
                stmt.setInt(3,user2);
                stmt.executeUpdate();
                // sets user2 to user1 status as ACCEPTED
                stmt.setInt(2,user2);
                stmt.setInt(3,user1);
                stmt.executeUpdate();
                } catch (SQLException e){System.out.println(e.getMessage());}
        } catch(SQLException e){System.out.println(e.getMessage());}






    }
}
