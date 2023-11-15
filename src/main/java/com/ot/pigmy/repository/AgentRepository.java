package com.ot.pigmy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ot.pigmy.dto.Agent;

public interface AgentRepository extends JpaRepository<Agent, String> {
	
	public Optional<Agent>  findById(String id);
	
	public Optional<Agent> findByEmail(String email);

	public Optional<Agent> findByPhone(String phone);

	public Optional<List<Agent>> findByAgentName(String agentName);
	
	public Optional<Agent> findByOtp(int otp);

	public Optional<Agent> findByUuid(String uuid);

	public List<Agent> findByAgentNameContaining(String agentName);

}