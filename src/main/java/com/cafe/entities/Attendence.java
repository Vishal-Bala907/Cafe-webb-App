package com.cafe.entities;

import jakarta.persistence.*;

@Entity
public class Attendence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long att_id;

    private String date;
    private String emp_username;
    private String in_time;
    private String out_time;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "u_id")
    private UserDAO att_user;

    // Constructors, getters, and setters
    public Attendence() {}

	public Attendence(String date, String emp_username, String in_time, String out_time, UserDAO att_user) {
		super();
		this.date = date;
		this.emp_username = emp_username;
		this.in_time = in_time;
		this.out_time = out_time;
		this.att_user = att_user;
	}

	public long getAtt_id() {
		return att_id;
	}

	public void setAtt_id(long att_id) {
		this.att_id = att_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEmp_username() {
		return emp_username;
	}

	public void setEmp_username(String emp_username) {
		this.emp_username = emp_username;
	}

	public String getIn_time() {
		return in_time;
	}

	public void setIn_time(String in_time) {
		this.in_time = in_time;
	}

	public String getOut_time() {
		return out_time;
	}

	public void setOut_time(String out_time) {
		this.out_time = out_time;
	}

	public UserDAO getAtt_user() {
		return att_user;
	}

	public void setAtt_user(UserDAO att_user) {
		this.att_user = att_user;
	}

	@Override
	public String toString() {
		return "Attendence [att_id=" + att_id + ", date=" + date + ", emp_username=" + emp_username + ", in_time="
				+ in_time + ", out_time=" + out_time + ", att_user=" + att_user + "]";
	}

    // Add other constructors, getters, and setters as needed
    
}
