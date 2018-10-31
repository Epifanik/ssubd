import java.sql.*;

public class DBConnection {
    public Connection connection;
    public Statement statement;

    public void connect() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
            String user = "postgres";
            String pass = "epifanik";
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kursova", user, pass);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }


    public String[][] createTable(String sql) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery(sql);
        int col = resultSet.getMetaData().getColumnCount();
        resultSet.last();
        int row = resultSet.getRow();
        resultSet.first();
        String[][] mass = new String[row + 1][col];
        int j = 0;
        for (int i = 1; i <= col; i++)
            mass[j][i - 1] = resultSet.getMetaData().getColumnName(i);

        resultSet.beforeFirst();
        while (resultSet.next()) {
            j++;
            for (int i = 1; i <= col; i++) {
                mass[j][i - 1] = resultSet.getString(i);
            }
        }
        statement.closeOnCompletion();
        return mass;
    }	}

