package tn.esprit.spring.entities;

import java.util.List;

public class MissionDTO {
    private int id;
    private String name;
    private String description;
    private Departement departement;
    private List<Timesheet> timesheets;

    public MissionDTO() {
      getMission();
    }

    public Mission getMission(){
        Mission miss = new Mission();
        miss.setId(this.getId());
        miss.setTimesheets(this.getTimesheets());
        miss.setDescription(this.getDescription());
        miss.setName(this.getName());
        return miss;
    }
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Departement getDepartement() {
        return departement;
    }


    public void setDepartement(Departement departement) {
        this.departement = departement;
    }


    public List<Timesheet> getTimesheets() {
        return timesheets;
    }


    public void setTimesheets(List<Timesheet> timesheets) {
        this.timesheets = timesheets;
    }
}
