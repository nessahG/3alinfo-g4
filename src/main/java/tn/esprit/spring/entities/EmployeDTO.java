package tn.esprit.spring.entities;

import java.util.List;

public class EmployeDTO {

	private int id;

	private String prenom;

	private String nom;

	private String email;

	private String password;

	private boolean actif;

	private Role role;

	private List<Departement> departements;

	private Contrat contrat;

	private List<Timesheet> timesheets;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Departement> getDepartements() {
		return departements;
	}

	public void setDepartements(List<Departement> departements) {
		this.departements = departements;
	}

	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public List<Timesheet> getTimesheets() {
		return timesheets;
	}

	public void setTimesheets(List<Timesheet> timesheets) {
		this.timesheets = timesheets;
	}

	public EmployeDTO() {
		getEmploye();
	}

	public Employe getEmploye() {
		Employe emp = new Employe();

		emp.setId(this.getId());
		emp.setPrenom(this.getPrenom());
		emp.setNom(this.getNom());
		emp.setEmail(this.getEmail());
		emp.setPassword(this.getPassword());
		emp.setRole(this.getRole());
		return emp;
	}

}
