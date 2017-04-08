package com.gp2017.Model;

import com.gp2017.Entity.Absentie;
import com.gp2017.Entity.Les;
import com.gp2017.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

@Repository
public class StudentModel {
	private static Map<Integer, Student> students;
	@Autowired
	private AbsentieModel absentieModel;
	@Autowired
	private LesModel lesModel;

	public ArrayList<Student> getAll(){
		try {
			Statement stat = DatabaseModel.myConn.createStatement();
			ArrayList<Student> students = new ArrayList<Student>();
			ResultSet res = stat.executeQuery("SELECT * FROM `persoon` WHERE `rol` = 'student'");
			while (res.next()){
				Student s = new Student(res.getInt("id"), res.getString("naam"), res.getString("email"), res.getString("wachtwoord"), res.getString("klas_FK"));
				students.add(s);
			}

			res.close();
			stat.close();

			return students;

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return null;
	}

	public Student getById(int id) {

	        try {
				PreparedStatement prepStat = DatabaseModel.myConn.prepareStatement("SELECT * FROM `persoon` WHERE `id` = (?) AND `rol` = 'student'");
				prepStat.setInt(1,id);
				ResultSet res = prepStat.executeQuery();

	            res.next();
	            System.out.println("DEBUG: STUDENT ID = " + res.getInt("id"));
	            System.out.println("NAAM: " + res.getString("naam"));
	            
	            Student s = new Student(res.getInt("id"), res.getString("naam"), res.getString("email"), res.getString("wachtwoord"), res.getString("klas_FK"));
                updateLessen(s);
                updateAbsenties(s);

	            res.close();
	            prepStat.close();
	            
	            return s;

	        } catch (SQLException ex) {
	            // handle any errors
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	        }
	        return null;
	    }


    public void updateLessen(Student student) {
        try {
            PreparedStatement prepStat = DatabaseModel.myConn.prepareStatement("SELECT * FROM `les` WHERE `klas_FK` = (?)");
            prepStat.setString(1,student.getKlas());
            ResultSet res = prepStat.executeQuery();


            while (res.next()) {
                student.addLes(lesModel.getById(res.getInt("id")));
            }
            res.close();
            prepStat.close();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }


	public void updateAbsenties(Student student) {
		try {

			Statement stat = DatabaseModel.myConn.createStatement();
			ResultSet res = stat.executeQuery("SELECT * FROM `absentie` WHERE `persoon_FK` =  '" + student.getId() + "'");

			while(res.next()) {
				student.addAbsentie(absentieModel.getById(res.getInt("id")));
			}

			res.close();
			stat.close();


		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}


	}    
	    
	    
	}
