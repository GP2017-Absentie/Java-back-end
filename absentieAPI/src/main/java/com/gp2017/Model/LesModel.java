package com.gp2017.Model;


import com.gp2017.Entity.*;
import com.gp2017.Entity.Les;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

@Repository
public class LesModel {
    @Autowired
    private AbsentieModel absentieModel;
    @Autowired
    private StudentModel studentModel;
    @Autowired
    private DocentModel docentModel;

    public ArrayList<Les> getAll(){
        try {
            ArrayList<Les> resultArray = new ArrayList<Les>();
            Statement stat = DatabaseModel.myConn.createStatement();
            ResultSet resultSet = stat.executeQuery("SELECT * FROM `les`");


            while(resultSet.next()) {
                int les_id = resultSet.getInt("id");
                int vak_FK = resultSet.getInt("vak_FK");
                String klas = resultSet.getString("klas_FK");
                int lokaal_FK = resultSet.getInt("lokaal_FK");
                int docent_FK = resultSet.getInt("docent_FK");
                Date datum = resultSet.getDate( "datum");
                Time starttijd = resultSet.getTime("starttijd"); // 10:00:00
                Time eindtijd = resultSet.getTime("eindtijd"); // 12:30:00

                Statement stat1 = DatabaseModel.myConn.createStatement();
                ResultSet vakResult = stat1.executeQuery("SELECT * FROM `vak` WHERE `id` = " + vak_FK);
                vakResult.next();

                String vakNaam = vakResult.getString("naam");
                String vakCode = vakResult.getString("code");

                Statement stat2 = DatabaseModel.myConn.createStatement();
                ResultSet lokaalResult = stat2.executeQuery("SELECT * FROM `lokaal` WHERE `id` = " + lokaal_FK);
                lokaalResult.next();

                String gebouw = lokaalResult.getString("gebouw");
                String lokaal_nummer = lokaalResult.getString("nummer");

                Docent docent = docentModel.getById(docent_FK);
                //ArrayList<Absentie> absenties  = AbsentieService.getById(les_id); ABSENTIEMODEL

                Les l = new Les(les_id, vakNaam, vakCode, gebouw, lokaal_nummer, datum, starttijd, eindtijd, klas, docent);
                resultArray.add(l);
                stat1.close();
                stat2.close();
                vakResult.close();
                lokaalResult.close();
            }
            resultSet.close();
            stat.close();

            return resultArray;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

	public Les getById(int id) { // VERVANG Les DATE door Les TIME
        try {
            Statement stat = DatabaseModel.myConn.createStatement();
            ResultSet res = stat.executeQuery("SELECT * FROM `les` WHERE `id` = " + id);           
            res.next();
            System.out.println("DEBUG: ID = " + res.getInt("id"));
            
            int les_id = res.getInt("id");
            int vak_FK = res.getInt("vak_FK");
            String klas = res.getString("klas_FK");
            int lokaal_FK = res.getInt("lokaal_FK");
            int docent_FK = res.getInt("docent_FK");
            Date datum = res.getDate("datum");
            Time starttijd = res.getTime("starttijd"); // 10:00:00
            Time eindtijd = res.getTime("eindtijd"); // 12:30:00
            
            
            System.out.println(starttijd);
            System.out.println(eindtijd);
            

            res = stat.executeQuery("SELECT * FROM `vak` WHERE `id` = " + vak_FK);
            res.next();

            String vakNaam = res.getString("naam");
            String vakCode = res.getString("code");
            
            
            
            res = stat.executeQuery("SELECT * FROM `lokaal` WHERE `id` = " + lokaal_FK);
            res.next();
            	
            String gebouw = res.getString("gebouw");
            String lokaal_nummer = res.getString("nummer");
                        
            Docent docent = docentModel.getById(docent_FK);
            //ArrayList<Absentie> absenties  = AbsentieService.getById(les_id); ABSENTIEMODEL
            
            Les l = new Les(les_id, vakNaam,vakCode,gebouw,lokaal_nummer,datum,starttijd,eindtijd, klas, docent);
                        
            res.close();
            stat.close();
            
            return l;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    public ArrayList<Absentie> getAbsentieByLesId(int id) {
        try {
            ArrayList<Absentie> absenties = new ArrayList<Absentie>();
            Statement stat = DatabaseModel.myConn.createStatement();
            ResultSet res = stat.executeQuery("SELECT * FROM `absentie` WHERE `les_FK` = " + id);

            while (res.next()) {
                absenties.add(absentieModel.getById(res.getInt("id")));
            }

            System.out.println("DEBUG: aantal absenties voor opgegevens les: " + absenties.size());

            res.close();
            stat.close();

            return absenties;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    public ArrayList<Student> getStudentenByLesId(int id) {
        try {
            ArrayList<Student> studenten = new ArrayList<>();

            PreparedStatement prepStat = DatabaseModel.myConn.prepareStatement("SELECT `klas_FK` FROM `les` WHERE `id` = (?)");
            prepStat.setInt(1,id);
            ResultSet res = prepStat.executeQuery();
            res.next();


            ResultSet res2 = prepStat.executeQuery("SELECT * FROM `persoon` WHERE `klas_FK` = '" + res.getString("klas_FK") + "'");

            while (res2.next()) {
                studenten.add(studentModel.getById(res2.getInt("id")));
            }

            res.close();
            res2.close();
            prepStat.close();

            return studenten;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }



}

