package com.ot.pigmy.controller;

import java.io.IOException;
import java.util.List;

import com.ot.pigmy.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ot.pigmy.dto.Agent;
import com.ot.pigmy.dto.ResponseStructure;
import com.ot.pigmy.service.AgentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/agent")
@CrossOrigin(origins = "*")
public class AgentController {

	@Autowired
	private AgentService agentService;

	@ApiOperation(value = "Save Agent", notes = "Input is Agent Object and return Agent object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 409, message = "Agent Already Exist") })
	@PostMapping(value = "/save", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Agent>> saveAgent(@RequestBody @Validated Agent agent) {
		return agentService.saveAgent(agent);
	}

	@ApiOperation(value = "Fetch Agent by id", notes = "Input Is Id Of The Agent Object and return Agent Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Agent>> findAgentById(@PathVariable String id) {
		return agentService.getAgentById(id);
	}

	@ApiOperation(value = "Fetch All Agent", notes = "Return The List Of Agents")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Agents Object") })
	@GetMapping(value = "/getAllAgents", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Agent>>> getAllAgents() {
		return agentService.getAllAgent();
	}

	@ApiOperation(value = "Delete Agent Object", notes = "Input Is Id Of The Agent Object And Output Is String")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 404, message = "Not Found") })
	@DeleteMapping(value = "/deleteAgent/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> deleteUserById(@PathVariable String id) {
		return agentService.deleteAgentById(id);
	}

	@ApiOperation(value = "Update Agent Object", notes = "Input Is Agent Object And Return Updated Agent Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(value = "/updateAgent", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Agent>> updateAgent(@RequestBody @Validated Agent agent) {
		return agentService.updateAgent(agent);
	}

	@ApiOperation(value = "Fetch Agent By Name", notes = "Input Is Agent-Name Of The Admin Object And Return Agent Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/getAgentbyname/{agentName}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Agent>>> findAgentByName(@PathVariable String agentName) {
		return agentService.getAgentByName(agentName);
	}

	@ApiOperation(value = "Fetch Agent By Email-Id", notes = "Input Is Email-Id Of The Agent Object And Return Agent Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/email/{email}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Agent>> findAdminByEmail(@PathVariable String email) {
		return agentService.getAgentByEmail(email);
	}

	@ApiOperation(value = "Fetch Agent By PhoneNumber", notes = "Input Is PhoneNumber Of The Agent Object And Return Agent Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/phone/{phone}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Agent>> findAdminByPhone(@PathVariable String phone) {
		return agentService.getAgentByPhone(phone);
	}

	@ApiOperation(value = "Validate Agent By Email", notes = "Inputs are Agent email id and password and return Agent object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/agentLogin/email/{email}/{password}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Object>> agentLogin(@PathVariable String email,
			@PathVariable String password) {
		return agentService.agentLogin(email, password);
	}

	@GetMapping(value = "/otp", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Agent>> validateOtp(@RequestParam int otp) {
		return agentService.validateOtp(otp);
	}

	@ApiOperation(value = "Fetch All Agents With Pagination And Sort", notes = "Return The List Of Agents With Pagination And Sort")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Agents Object") })
	@GetMapping(value = "/getAllAgents/{offset}/{pageSize}/{field}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Page<Agent>>> getAdminsWithPaginationAndSort(@PathVariable int offset,
			@PathVariable int pageSize, @PathVariable String field) {
		return agentService.getAgentsWithPaginationAndSorting(offset, pageSize, field);
	}

	@ApiOperation(value = "Change Agent Profile Status To Online", notes = "Inputs are Agent-Id and return String Status Changed")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PatchMapping(value = "/online/{agentId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Object>> changeAgentProfileStatusToOnline(@PathVariable String agentId) {
		return agentService.changeAgentProfileStatusToOnline(agentId);
	}

	@ApiOperation(value = "Change Agent Profile Status To Offline", notes = "Inputs are Agent-Id and return String Status Changed")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PatchMapping(value = "/offline/{agentId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Object>> changeAgentProfileStatusToOffline(@PathVariable String agentId) {
		return agentService.changeAgentProfileStatusToOffline(agentId);
	}

	@ApiOperation(value = "Save Agent Aadhar Card Image", notes = "Input is Agent Aadhar Card Image file")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PostMapping(value = "/uploadAgentAadharCardImage", consumes = { MediaType.ALL_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> uploadAgentAadharCardImage(@RequestParam("agentId") String agentId,
			@RequestParam("file") MultipartFile file) throws IOException {
		return agentService.uploadAgentAadharCardImageByAgentId(agentId, file);
	}

	@ApiOperation(value = "Download Agent Aadhar Card Image", notes = "Input is Agent Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/downloadAgentAadharCardImage", produces = { MediaType.ALL_VALUE })
	public ResponseEntity<?> downloadAgentAadharCardImage(@RequestParam String agentId) {
		return agentService.downloadAgentAadharCardImageByAgentId(agentId);
	}

	@ApiOperation(value = "Save Agent Pan Card Image", notes = "Input is Agent Pan Card Image file")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PostMapping(value = "/uploadAgentPanCardImage/{agentId}", consumes = { MediaType.ALL_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> uploadAgentPanCardImage(@PathVariable String agentId,
			@RequestParam("file") MultipartFile file) throws IOException {
		return agentService.uploadAgentPanCardImageByAgentId(agentId, file);
	}

	@ApiOperation(value = "Download Agent Pan Card Image", notes = "Input is Agent Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/downloadAgentPanCardImage", produces = { MediaType.ALL_VALUE })
	public ResponseEntity<?> downloadAgentPanCardImage(@RequestParam String agentId) {
		return agentService.downloadAgentPanCardImageByAgentId(agentId);
	}

	@ApiOperation(value = "Save Agent Profile Image", notes = "Input is Agent Profile Image file")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PostMapping(value = "/uploadAgentProfileImage/{agentId}", consumes = { MediaType.ALL_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> uploadAgentProfileImage(@PathVariable String agentId,
			@RequestParam("file") MultipartFile file) throws IOException {
		return agentService.uploadAgentProfileImageByAgentId(agentId, file);
	}

	@ApiOperation(value = "Download Agent Profile Image", notes = "Input is Agent Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/downloadAgentProfileImage", produces = { MediaType.ALL_VALUE })
	public ResponseEntity<?> downloadAgentProfileImage(@RequestParam String agentId) {
		return agentService.downloadAgentProfileImageByAgentId(agentId);
	}

	@ApiOperation(value = "Fetch Agent By Id Or Name", notes = "Input is Agent Id Or Agent Name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/getAgentByIdOrAgentByName/{query}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Agent>>> getAgentByIdOrAgentByName(@PathVariable String query) {
		return agentService.searchQuery(query);
	}

	@ApiOperation(value = "Fetch Customers for Agent ", notes = "Input is Agent Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/getCustomers", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Customer>>> getCustomers(@RequestParam String agentId) {
		return agentService.getCustomersByAgentId(agentId);
	}

	@ApiOperation(value = "Verify Customer Agent mapping ", notes = "Input is Agent Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/verifyCustomer", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Customer>> getCustomers(@RequestParam String agentId, @RequestParam String customerId) {
		return agentService.verifyCustomerBelongsToAgent(agentId,customerId);
	}


}