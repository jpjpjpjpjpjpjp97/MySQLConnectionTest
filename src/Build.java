import java.sql.*;

public class Build {
    public static void main(String[] args) throws SQLException {
        String url ="jdbc:mysql://localhost:3306/test_db";
        Connection connection = DriverManager.getConnection(url,"root", "");
        try (Statement stmt = connection.createStatement()) {
            System.out.println("Listing Resumes");
            System.out.println(listResumes(connection));
            System.out.println("Updating Resume with ID=2");
            updateResume(connection, 2, "Jane Doe", "Jane@gmail.com", "Masters in Electronics Engineering", "5 years as Engineering Manager");
            System.out.println(selectResume(connection, 2));
            System.out.println("Adding Resume with ID=3");
            createResume(connection, 3, "John Doe", "john@gmail.com", "None", "None");
            System.out.println(listResumes(connection));
            System.out.println("Deleting Resume with ID=1");
            deleteResume(connection, 1);
            System.out.println(listResumes(connection));

            connection.close();
        }catch( Exception e ) {
            e.printStackTrace();
        }
    }

    public static String resumesToString(ResultSet result) throws SQLException {
        StringBuilder resultString = new StringBuilder();
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            String email_address = result.getString("email_address");
            String education = result.getString("education");
            String experience = result.getString("experience");
            resultString.append(String.format(id + ", " + name + ", " + email_address +
                    ", " + education + ", " + experience + "\n"));
        }
        return resultString.toString();
    }

    public static String listResumes(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM resumes;");
            return resumesToString(resultSet);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String selectResume(Connection connection, int id) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM resumes WHERE id=" + id + ";");
            return resumesToString(resultSet);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static int createResume(Connection connection, int id, String name, String email_address, String education, String experience) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate("INSERT INTO resumes VALUES (" + id + ",'" + name + "','" + email_address + "','" + education + "','" + experience + "');");
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static int updateResume(Connection connection, int id, String name, String email_address, String education, String experience) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate("UPDATE resumes SET " + "name='" + name + "',email_address='" + email_address + "',education='" + education + "',experience='" + experience + "' WHERE id=" + id + ";");
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static int deleteResume(Connection connection, int id) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate("DELETE FROM resumes WHERE id=" + id + ";");
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
