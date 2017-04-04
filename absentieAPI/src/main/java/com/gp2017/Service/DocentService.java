package com.gp2017.Service;

import com.gp2017.Entity.Docent;
import com.gp2017.Entity.Student;
import com.gp2017.Model.DocentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;

@Service
public class DocentService {
    @Autowired
    private DocentModel docentModel;

    public ArrayList<Docent> getAll() { return docentModel.getAll(); }

    public Docent getById(int id) { return docentModel.getById(id); }
}
