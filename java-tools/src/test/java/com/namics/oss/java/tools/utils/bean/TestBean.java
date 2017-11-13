package com.namics.oss.java.tools.utils.bean;

public class TestBean {

	private String username;
	private String firstname;
	private String lastname;
	private String pleaseIgnore;
	private String id;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}


	public TestBean username(String username) {
		setUsername(username);
		return this;
	}

	public TestBean firstname(String firstname) {
		setFirstname(firstname);
		return this;
	}

	public TestBean lastname(String lastname) {
		setLastname(lastname);
		return this;
	}

	public TestBean id(String id){
		setId(id);
		return this;
	}

	public String getPleaseIgnore() {
		return pleaseIgnore;
	}

	public void setPleaseIgnore(String pleaseIgnore) {
		this.pleaseIgnore = pleaseIgnore;
	}

	public TestBean pleaseIgnore(String pleaseIgnore) {
		setPleaseIgnore(pleaseIgnore);
		return this;
	}



	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		TestBean testBean = (TestBean) o;

		if (username != null ? !username.equals(testBean.username) : testBean.username != null) {
			return false;
		}
		if (firstname != null ? !firstname.equals(testBean.firstname) : testBean.firstname != null) {
			return false;
		}
		return !(lastname != null ? !lastname.equals(testBean.lastname) : testBean.lastname != null);

	}

	@Override
	public int hashCode() {
		int result = username != null ? username.hashCode() : 0;
		result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
		result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
		return result;
	}


	@Override
	public String toString() {
		return "TestBean{" +
		       "username='" + username + '\'' +
		       ", firstname='" + firstname + '\'' +
		       ", lastname='" + lastname + '\'' +
		       '}';
	}

}