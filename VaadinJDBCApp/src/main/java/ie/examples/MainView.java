package ie.examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;

/**
 * The main view contains a button and a click listener.
 */
@Route
@PWA(name = "My Application", shortName = "My Application")
public class MainView extends VerticalLayout {


    public MainView() {

        Connection connection = null;

        String connectionString = "jdbc:sqlserver://donaldbserver01.database.windows.net:1433;database=donaldb;user=donal@donaldbserver01;password={PasswordGoesHere};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        VerticalLayout layout  = new VerticalLayout();
        
                        
        try {
            
            // Connect with JDBC driver to a database
            connection = DriverManager.getConnection(connectionString);
            // Add a label to the web app with the message and name of the database we connected to 
           
            ResultSet rs2 = connection.createStatement().executeQuery("SELECT * FROM customerTable;");
            // Convert the resultset that comes back into a List - we need a Java class to represent the data (Customer.java in this case)
            List<Customer> customers = new ArrayList<Customer>();
            // While there are more records in the resultset
            while(rs2.next())
            {   
                // Add a new Customer instantiated with the fields from the record (that we want, we might not want all the fields, note how I skip the id)
                customers.add(new Customer(rs2.getString("first_name"), rs2.getString("last_name"), rs2.getBoolean("paid"), rs2.getDouble("amount")));
            }

            // Add my component, grid is templated with Customer
            Grid<Customer> myGrid = new Grid<>();
            // Set the items (List)
            myGrid.setItems(customers);
            // Configure the order and the caption of the grid
            myGrid.addColumn(Customer::getFirst_name).setHeader("Name");
            myGrid.addColumn(Customer::getLast_name).setHeader("Surname");
            myGrid.addColumn(Customer::getAmount).setHeader("Total Amount");
            myGrid.addColumn(Customer::isPaid).setHeader("Paid");

            
            // Add the grid to the list
            layout.add(myGrid);

            Label status = new Label("Starting Value");

            Button myButton = new Button("Click Me!");

            myButton.addClickListener(event ->
                {
                    Set<Customer> selected = myGrid.getSelectedItems();
                    

                    if (selected.size()==0){
                        status.setText("Please select at least one room!");
                        return;
                    } 
                    
                    for(Customer c : selected){
                        if (c.isPaid()==true){
                            status.setText(c.getFirst_name()+ " " + c.getLast_name() + " owes money still.");
                            return;
                         }
                      }
                }
            );

            layout.add(status);
            layout.add(myButton);

            layout.add(new Label("Connected to database: " + connection.getCatalog()));
            
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM customerTable WHERE paid = 'false' ORDER BY AMOUNT DESC;");
        
            while(rs.next())
            {
                layout.add(new Label(rs.getString("first_name") + " " + rs.getString("last_name") + " has not paid " + rs.getDouble("amount")));  
            }


            
        } catch (Exception e) {
            // This will show an error message if something went wrong
            
            layout.add(e.getMessage());
            
        }

        add(layout);
    }
}
