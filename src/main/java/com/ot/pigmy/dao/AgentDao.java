package com.ot.pigmy.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.ot.pigmy.dto.Agent;
import com.ot.pigmy.repository.AgentRepository;

@Repository
public class AgentDao {

	@Autowired
	private AgentRepository agentRepository;

	public Agent saveAgent(Agent agent) {
		return agentRepository.save(agent);
	}

	public Agent getAgentById(String id) {
		Optional<Agent> agent = agentRepository.findById(id);
		return agent.orElse(null);
	}

	public List<Agent> getAllAgents() {
		return agentRepository.findAll();
	}

	public Page<Agent> findAgentsWithPaginationAndSorting(int offset, int pageSize, String field) {
		Page<Agent> agents = agentRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return agents;
	}

	public void deleteAgent(Agent agent) {
		agentRepository.delete(agent);
	}

	public Agent getAgentByEmail(String email) {
		Optional<Agent> agent = agentRepository.findByEmail(email);
		return agent.orElse(null);
	}

	public Agent getAgentByPhone(String phone) {
		Optional<Agent> agent = agentRepository.findByPhone(phone);
		return agent.orElse(null);
	}

	public List<Agent> getAgentByName(String agentName) {
		Optional<List<Agent>> agents = agentRepository.findByAgentName(agentName);
		return agents.orElse(null);
	}

	public Agent getUserByOtp(int otp) {
		Optional<Agent> agent = agentRepository.findByOtp(otp);
		return agent.orElse(null);
	}

	public Agent getUserByUuid(String uuid) {
		Optional<Agent> agent = agentRepository.findByUuid(uuid);
		return agent.orElse(null);
	}

	public List<Agent> fetchAgentByQuery(String query) {
		return agentRepository.findByAgentNameContaining(query);
	}
}