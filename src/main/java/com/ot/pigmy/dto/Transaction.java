package com.ot.pigmy.dto;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "AutoGenerated")
	private long id;

	@NotBlank(message = "Please Enter The Customer-Id")
	@ApiModelProperty(required = true)
	private String customerId;

	@NotBlank(message = "Please Enter The Agent-Id")
	@ApiModelProperty(required = true)
	private String agentId;

	@NotNull(message = "Please Enter The amount")
	@ApiModelProperty(required = true)
	private int amount;

	@NotBlank(message = "Please Enter The Mode")
	@ApiModelProperty(required = true)
	private String mode;

	private boolean status;

	@UpdateTimestamp
	@JsonIgnore
	private LocalDate transactionDate;

	@NotBlank(message = "Please Enter The Account Number")
	@ApiModelProperty(required = true)
	private String accountNumber;

	@NotBlank(message = "Please Enter The Account Type")
	@ApiModelProperty(required = true)
	private String accountType;
}