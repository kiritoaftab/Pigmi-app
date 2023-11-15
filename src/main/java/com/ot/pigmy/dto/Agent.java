package com.ot.pigmy.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agent {

	@Id
	private String id;

	@NotBlank(message = "Please Enter The Agent-Name")
	@ApiModelProperty(required = true)
	private String agentName;

	@Column(unique = true)
	@NotBlank(message = "Please Enter The Agent-Email")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Enter a Valid User-Email")
	@ApiModelProperty(required = true)
	private String email;

	@Column(unique = true)
	@NotBlank(message = "Please Enter The Agent-PhoneNumber")
	@ApiModelProperty(required = true)
	@Pattern(regexp = "^[6-9]{1}[0-9]{9}+$", message = "Enter Proper User-PhoneNumber")
	private String phone;

	@NotBlank(message = "Please Enter the Agent-Password")
	@ApiModelProperty(required = true)
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{6,15})", message = "Enter Proper Ajent-Password "
			+ "\n" + " The User-Password Should Have Atleast " + "\n" + " 1 Upper Case " + "\n" + " 1 Lower Case "
			+ "\n" + " 1 Special Character " + "\n" + " And 1 Numric Character " + "\n"
			+ " The Length OF The Password Must Be Minimum OF 6 Character And Maximum OF 15 Character ")
	private String password;

	@ApiModelProperty(required = true)
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private boolean status;

	@JsonGetter
	public boolean isStatus() {
		return status;
	}

	@JsonIgnore
	public void setStatus(boolean status) {
		this.status = status;
	}

	@NotBlank(message = "Please Enter the Agent-Address")
	@ApiModelProperty(required = true)
	private String address;

	@Column(unique = true)
	@NotNull(message = "Please Enter the Agent-Aadhaar_Number")
	@Pattern(regexp = "^[0-9]{12}+$", message = "Enter Proper Agent-Aadhaar_Number")
	@ApiModelProperty(required = true)
	private String aadhaarNumber;

	@ApiModelProperty(required = true)
	private String designation;

	@JsonIgnore
	private int otp;

	@JsonIgnore
	private String uuid;

	private String agentAadharCardImage;
	
	private String agentProfileImage;
	
	private String agentPanCardImage;

}