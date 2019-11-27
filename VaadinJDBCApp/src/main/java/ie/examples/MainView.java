package ie.examples;

import java.sql.Connection;
import java.sql.DriverManager;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Label;

/**
 * The main view contains a button and a click listener.
 */
@Route
@PWA(name = "My Application", shortName = "My Application")
public class MainView extends VerticalLayout {


    public MainView() {

        Connection connection = null;

        String connectionString = "jdbc:sqlserver://donaldbserver01.database.windows.net:1433;database=donaldb;user=donal@donaldbserver01;password=[PasswordGoesHere];encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        VerticalLayout layout  = new VerticalLayout();
        
                        
        try {
            
            // Connect with JDBC driver to a database
            connection = DriverManager.getConnection(connectionString);
            // Add a label to the web app with the message and name of the database we connected to 
            layout.add(new Label("Connected to database: " + connection.getCatalog()));
            
        } catch (Exception e) {
            // This will show an error message if something went wrong
            
            layout.add(e.getMessage());
            
        }

        add(layout );
    }
}
