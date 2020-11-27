package model.entities;

import java.io.Serializable;

public class Department implements Serializable{
	// serializable em bytes quando precisamos trafegar em rede etc;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idInteger;
	private String nameString;
	
	public Department() {
		
	}

	public Department(Integer idInteger, String nameString) {
		//super();
		this.idInteger = idInteger;
		this.nameString = nameString;
	}

	public Integer getIdInteger() {
		return idInteger;
	}

	public void setIdInteger(Integer idInteger) {
		this.idInteger = idInteger;
	}

	public String getNameString() {
		return nameString;
	}

	public void setNameString(String nameString) {
		this.nameString = nameString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idInteger == null) ? 0 : idInteger.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		if (idInteger == null) {
			if (other.idInteger != null)
				return false;
		} else if (!idInteger.equals(other.idInteger))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Department [idInteger=" + idInteger + ", nameString=" + nameString + "]";
	}
	
}