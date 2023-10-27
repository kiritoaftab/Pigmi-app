package com.ot.pigmy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ot.pigmy.dto.Admin;
import com.ot.pigmy.dto.ResponseStructure;
import com.ot.pigmy.service.AdminService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@ApiOperation(value = "Save Admin", notes = "Input is Admin Object and return Admin object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 409, message = "Admin Already Exist") })
	@PostMapping(value = "/save", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Admin>> saveAdmin(@RequestBody @Validated Admin admin) {
		return adminService.saveAdmin(admin);
	}

	@ApiOperation(value = "Fetch Admin by id", notes = "Input Is Id Of The Admin Object and return Admin Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Admin>> findUserById(@PathVariable String id) {
		return adminService.getAdminById(id);
	}

	@ApiOperation(value = "Fetch All Admin", notes = "Return The List Of Admins")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Admins Object") })
	@GetMapping(value = "/getAllAdmin", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Admin>>> getAllUsers() {
		return adminService.getAllAdmin();
	}

	@ApiOperation(value = "Delete Admin Object", notes = "Input Is Id Of The Admin Object And Output Is String")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 404, message = "Not Found") })
	@DeleteMapping(value = "/deleteAdmin/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> deleteUserById(@PathVariable String id) {
		return adminService.deleteAdminById(id);
	}

	@ApiOperation(value = "Update Admin Object", notes = "Input Is Admin Object And Return Updated Admin Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(value = "/updateAdmin", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Admin>> updateAdmin(@RequestBody @Validated Admin admin) {
		return adminService.updateAdmin(admin);
	}

	@ApiOperation(value = "Fetch Admin By Name", notes = "Input Is Name Of The Admin Object And Return Admin Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/getAdminbyname/{userName}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Admin>>> findUserByName(@PathVariable String userName) {
		return adminService.getAdminByName(userName);
	}

	@ApiOperation(value = "Fetch Admin By Email-Id", notes = "Input Is Email-Id Of The Admin Object And Return Admin Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/email/{email}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Admin>> findAdminByEmail(@PathVariable String email) {
		return adminService.getAdminByEmail(email);
	}

	@ApiOperation(value = "Fetch Admin By PhoneNumber", notes = "Input Is PhoneNumber Of The Admin Object And Return Admin Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/phone/{phone}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Admin>> findAdminByPhone(@PathVariable String phone) {
		return adminService.getAdminByPhone(phone);
	}

	@ApiOperation(value = "Validate Admin By Email", notes = "Inputs are Admin email id and password and return Admin object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/validate/email/{email}/{password}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Object>> adminLogin(@PathVariable String email,
			@PathVariable String password) {
		return adminService.adminLogin(email, password);
	}

	@GetMapping(value = "/otp", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Admin>> validateOtp(@RequestParam int otp) {
		return adminService.validateOtp(otp);
	}

	@ApiOperation(value = "Fetch All Admins With Pagination And Sort", notes = "Return The List Of Admins With Pagination And Sort")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Users Object") })
	@GetMapping(value = "/getAllAdmins/{offset}/{pageSize}/{field}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Page<Admin>>> getAdminsWithPaginationAndSort(@PathVariable int offset,
			@PathVariable int pageSize, @PathVariable String field) {
		return adminService.getAdminsWithPaginationAndSorting(offset, pageSize, field);
	}
}