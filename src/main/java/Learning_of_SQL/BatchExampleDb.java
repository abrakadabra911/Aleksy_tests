package Learning_of_SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BatchExampleDb {

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";

            try (Connection con = DriverManager.getConnection(url, "postgres", "postgres")) {
                addBatch(con);
                select(con);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    private static void addBatch(Connection con) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO jc_contact (first_name, last_name, phone, email) "
                        + "VALUES (?, ?, ?, ?)", new String[] {"contact_id"})) {

            for (int i = 0; i < 10; i++) {
                // Заполняем параметры запроса
                stmt.setString(1, "FirstName_" + i);
                stmt.setString(2, "LastNAme_" + i);
                stmt.setString(3, "phone_" + i);
                stmt.setString(4, "email_" + i);
                // Запрос не выполняется, а укладывается в буфер, кторый выполняется сразу для всех команд
                stmt.addBatch();
            }
            // Выполняем все запросы разом
            stmt.executeBatch();
            // Получить список сгенерированных contact_id
            ResultSet gk = stmt.getGeneratedKeys();
            while(gk.next()) {
                System.out.println("Inserted:" + gk.getString(1));
            }
        }
    }

    private static void select(Connection con) throws SQLException {
        // Вы можете выполнить сразу несколько запросов внутри одного
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM jc_contact; DELETE FROM jc_contact")) {

            // true - первый результат возвращает ResultSet
            // false - первый результат выполнял update/delete/insert
            boolean test = stmt.execute();
            // Для проверки наличия еще одного выgолненного SQL-запроса можно проверить stmt.getUpdateCount()
            while (test || stmt.getUpdateCount() > -1) {
                if (test) {
                    try (ResultSet rs = stmt.getResultSet()) {
                        while (rs.next()) {
                            String str = rs.getString("contact_id") + ":" + rs.getString(2);
                            System.out.println("Contact:" + str);
                        }
                    }
                } else {
                    System.out.println("Update SQL is executed:" + stmt.getUpdateCount());
                }
                System.out.println("=============================");
                test = stmt.getMoreResults();
            }
        }
    }
}
