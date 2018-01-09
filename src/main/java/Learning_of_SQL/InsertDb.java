package Learning_of_SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertDb
{
    public static void main(String[] args) {
        InsertDb m = new InsertDb();
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/contactdb";
            String login = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                // Процедура вставки
                int contactId = (int)m.insert(con, "FirstName", "LastName", "phone", "email");
                System.out.println("CONTACT_ID:" + (Integer) contactId);
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long insert(Connection con, String firstName, String lastName, String phone, String email) throws SQLException {
        // Объявили переменную для хранения ИД
        int contactId = -1;

        // Вторым параметром передаем массив полей, значниея которых нам нужны
        PreparedStatement stmt = con.prepareStatement("INSERT INTO jc_contact (first_name, last_name, phone, email) VALUES (?, ?, ?, ?)", new String[] {"contact_id"});
        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        stmt.setString(3, phone);
        stmt.setString(4, email);
        stmt.executeUpdate();


        // Получаем список данных дял сгенерированных ключей
        ResultSet gk = stmt.getGeneratedKeys();
        if(gk.next()) {
            // Получаем поле contact_id
            contactId = gk.getInt("contact_id");
        }
        stmt.close();

        return contactId;
    }
}