package ie.examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Div;

/**
 * The main view contains a button and a click listener.
 */
@Route
@PWA(name = "My Application", shortName = "My Application")
public class MainView extends VerticalLayout {


    public MainView() {

        Connection connection = null;

        String connectionString = "jdbc:sqlserver://donaldbserver01.database.windows.net:1433;database=donaldb;user=donal@donaldbserver01;password=;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

                        
        try {
            
            DriverManager.registerDriver(new SQLServerDriver());

            // Connect with JDBC driver to a database
            connection = DriverManager.getConnection(connectionString);
            // Add a label to the web app with the message and name of the database we connected to 
            Notification notification = Notification.show("Connected to database: " + connection.getCatalog());
            
        } catch (Exception e) {
            // This will show an error message if something went wrong
            Notification notification = Notification.show(e.getMessage());
            
        }

        
    }
}
