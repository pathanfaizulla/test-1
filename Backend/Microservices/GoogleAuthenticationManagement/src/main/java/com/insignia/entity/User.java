package com.insignia.entity;


//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "users")
public class User {
	
	private String username;
	
	private Boolean enabled;
	
//	@Enumerated(EnumType.STRING)
    private Provider provider;
	
	 public Provider getProvider() {
	        return provider;
	    }
	 
	    public void setProvider(Provider provider) {
	        this.provider = provider;
	    }

	
	
	
	
         
}
