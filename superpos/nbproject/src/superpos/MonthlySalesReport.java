package superpos;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MonthlySalesReport {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void establishConnection() throws ClassNotFoundException, SQLException {
       try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/ashprod", "root", "");
        connection.setAutoCommit(false); // Set auto-commit to false
    } catch (ClassNotFoundException | SQLException e) {
        throw e;
    }
    }

    public void generateMonthlySalesReport() throws ClassNotFoundException {
        StringBuilder report = new StringBuilder();

        try {
            if (connection == null) {
                establishConnection();
            }

            // Get the current date
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            String currentMonth = dateFormat.format(calendar.getTime());

            String yourSQLQuery = "SELECT Product_name, SUM(Total) AS Monthly_Sales "
                    + "FROM pos "
                    + "WHERE DATE_FORMAT(Date, '%Y-%m') = ? "
                    + "GROUP BY Product_name";

            preparedStatement = connection.prepareStatement(yourSQLQuery ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, currentMonth);
            resultSet = preparedStatement.executeQuery();

            int resultSetSize = 0;
            if (resultSet.last()) {
                resultSetSize = resultSet.getRow();
                resultSet.beforeFirst();
            }

            if (resultSetSize == 0) {
                report.append("No data found for the current month.");
            } else {
                report.append("Monthly Sales Report for ").append(currentMonth).append("\n");
                report.append("-----------------------------------------------------\n");
                report.append(String.format("%-20s %-15s%n", "Product Name", "Total Sales"));
                report.append("-----------------------------------------------------\n");

                while (resultSet.next()) {
                    String productName = resultSet.getString("Product_name");
                    double totalSales = resultSet.getDouble("Monthly_Sales");

                    report.append(String.format("%-20s %-15.2f%n", productName, totalSales));
                }
            }

            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, report.toString(), "Monthly Sales Report", JOptionPane.INFORMATION_MESSAGE);
            });

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } 
        catch (ClassNotFoundException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         }
try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            MonthlySalesReport monthlySalesReport = new MonthlySalesReport();
            monthlySalesReport.establishConnection();
            monthlySalesReport.generateMonthlySalesReport();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
        e.printStackTrace();
        // Handle any other exceptions if necessary
    }
    }


