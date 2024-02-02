package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;

    }
    public void addPatient() {
        System.out.print("Enter Patient Name : ");
        String name = scanner.next();
        System.out.println("Enter patient Age : ");
        int age = scanner.nextInt();
        System.out.println("Enter Patient Gender (M/F) : ");
        String gender = String.valueOf(scanner.next().toUpperCase().charAt(0));

        try {
            String query = "INSERT INTO Patients(name,  age, gender) VALUES (?, ?, ?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRRRows = preparedStatement.executeUpdate();
            if (affectedRRRows > 0) {
                System.out.println("Patient Added Successfully");
            } else {
                System.out.println("Failed to add Patient");
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
        public void viewPatients(){
            String query = "select * from patients";
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("Patients: ");
                System.out.println("+------------+--------------------+----------+------------+");
                System.out.println("| Patient Id | Name               | Age      | Gender     |");
                System.out.println("+------------+--------------------+----------+------------+");
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    String gender = resultSet.getString("gender");
                    System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
                    System.out.println("+------------+--------------------+----------+------------+");
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        public boolean getPatientById(int id) {
            String query = "select * from patients where id = ?";
            try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet =  preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else {
                return false;
            }

            }catch (SQLException e){
                e.printStackTrace();
            }
            return false;
        }

}
