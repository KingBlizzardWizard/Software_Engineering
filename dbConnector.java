import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnector {
 
    public static void main(String[] args) {
       dbConnector d = new dbConnector();
        d.userConnect();
        // d.friendConnect();
        // d.postConnect();
    }

    public void userConnect(){
        try (var conn =  DriverManager.getConnection("jdbc:sqlite:userdata.db")){ 
            System.out.println("Connected to User Database");
        } catch (SQLException e){ System.out.println(e.getMessage());  
        }
    }
    
    // public void friendConnect(){
    //     try (var conn =  DriverManager.getConnection("jdbc:sqlite:friends.db")){ 
    //         System.out.println("Connected to Friend Database");
    //     } catch (SQLException e){ System.out.println(e.getMessage());  
    //     }
    // }
    
    // public void postConnect(){
    //     try (var conn =  DriverManager.getConnection("jdbc:sqlite:posts.db")){ 
    //         System.out.println("Connected to post Database");
    //     } catch (SQLException e){ System.out.println(e.getMessage());  
    //     }
    // }



}
