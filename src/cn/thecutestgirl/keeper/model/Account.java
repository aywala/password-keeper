package cn.thecutestgirl.keeper.model;

import javafx.beans.property.*;

public class Account {
	private final StringProperty site;
	private final StringProperty username;
	private final StringProperty password;
	private final StringProperty note;
	
	
	public Account() {
		this.site=new SimpleStringProperty("");
		this.username=new SimpleStringProperty("");
		this.password=new SimpleStringProperty("");
		this.note=new SimpleStringProperty("");
	}
	
	public String getSite() {
        return site.get();
    }
	
	public void setSite(String site) {
        this.site.set(site);
    }
	
	public StringProperty siteProperty() {
        return site;
    }
	
	public String getUsername() {
        return username.get();
    }
	
	public void setUsername(String username) {
        this.username.set(username);
    }
	
	public StringProperty usernameProperty() {
        return username;
    }
	
	public String getPassword() {
        return password.get();
    }
	
	public void setPassword(String password) {
        this.password.set(password);
    }
	
	public StringProperty passwordProperty() {
        return password;
    }
	
	public String getNote() {
        return note.get();
    }
	
	public void setNote(String note) {
        this.note.set(note);
    }
	
	public StringProperty noteProperty() {
        return note;
    }

}
