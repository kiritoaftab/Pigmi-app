package com.ot.pigmy.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ot.pigmy.dao.AdminDao;
import com.ot.pigmy.dto.Admin;
import com.ot.pigmy.dto.ResponseStructure;
import com.ot.pigmy.exception.DataNotFoundException;
import com.ot.pigmy.exception.DuplicateDataEntryException;
import com.ot.pigmy.exception.EmailIdNotFoundException;
import com.ot.pigmy.exception.IdNotFoundException;
import com.ot.pigmy.exception.InvalidCredentialException;
import com.ot.pigmy.exception.PhoneNumberNotFoundException;
import com.ot.pigmy.util.EmailSender;
import com.ot.pigmy.util.Encryption;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private EmailSender emailSender;

	public ResponseEntity<ResponseStructure<Admin>> saveAdmin(Admin admin) {

		ResponseStructure<Admin> responseStructure = new ResponseStructure<>();

		if (adminDao.getAdminByEmail(admin.getEmail()) != null || adminDao.getAdminByPhone(admin.getPhone()) != null) {

			throw new DuplicateDataEntryException("Admin Already Exist's");

		} else {
			admin.setPassword(Encryption.encrypt(admin.getPassword()));
			emailSender.sendSimpleEmail(admin.getEmail(),
					"Greetings \nYour Profile in Pigmy Account Has Been Created.\nThank You.",
					"Hello " + admin.getAdminName());
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("Admin Saved Successfully");
			responseStructure.setData(adminDao.saveAdmin(admin));
			return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
		}
	}

	public ResponseEntity<ResponseStructure<Admin>> getAdminById(String id) {
		ResponseStructure<Admin> responseStructure = new ResponseStructure<>();
		Admin admin = adminDao.getAdminById(id);
		if (admin != null) {
			admin.setPassword(Encryption.decrypt(admin.getPassword()));
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Admin Details By Id");
			responseStructure.setData(admin);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Admin ID " + id + ", NOT FOUND");
		}
	}

	public ResponseEntity<ResponseStructure<List<Admin>>> getAllAdmin() {
		ResponseStructure<List<Admin>> responseStructure = new ResponseStructure<>();
		List<Admin> list = adminDao.getAllAdmins();
		if (list.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Admin Fetched");
			responseStructure.setData(list);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Admin Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<Page<Admin>>> getAdminsWithPaginationAndSorting(int offset, int pageSize,
			String field) {
		ResponseStructure<Page<Admin>> responseStructure = new ResponseStructure<>();
		Page<Admin> page = adminDao.findAdminWithPaginationAndSorting(offset, pageSize, field);
		if (page.getSize() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Admins Fetched");
			responseStructure.setRecordCount(page.getSize());
			responseStructure.setData(page);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Admin Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<String>> deleteAdminById(String id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		Admin admin = adminDao.getAdminById(id);
		if (admin != null) {
			adminDao.deleteAdmin(admin);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Admin Of Id " + id + " Data Deleted");
			responseStructure.setData("Admin Data Deleted Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Admin Id " + id + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<Admin>> updateAdmin(Admin admin) {
		ResponseStructure<Admin> responseStructure = new ResponseStructure<>();
		Admin admin1 = adminDao.getAdminById(admin.getId());
		if (admin1 != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Admin Updated Successfully");
			admin.setPassword(Encryption.encrypt(admin.getPassword()));
			responseStructure.setData(adminDao.saveAdmin(admin));
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Admin Id " + admin.getId() + ", Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<Admin>> getAdminByEmail(String email) {
		Admin admin = adminDao.getAdminByEmail(email);
		if (admin != null) {
			ResponseStructure<Admin> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Admin By Email-Id");
			responseStructure.setData(admin);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new EmailIdNotFoundException("Admin-Email : " + email + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<Admin>> getAdminByPhone(String phone) {
		Admin admin = adminDao.getAdminByPhone(phone);
		if (admin != null) {
			ResponseStructure<Admin> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Admin By PhoneNumber");
			responseStructure.setData(admin);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new PhoneNumberNotFoundException("Admin-PhoneNumber : " + phone + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<List<Admin>>> getAdminByName(String adminName) {
		ResponseStructure<List<Admin>> responseStructure = new ResponseStructure<>();
		List<Admin> users = adminDao.getAdminByName(adminName);
		for (Admin user : users) {
			if (user.getAdminName().equalsIgnoreCase(adminName) && users.size() > 0) {
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Fetched Admin By Name");
				responseStructure.setData(users);
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new DataNotFoundException("Admin Data Not Present");
			}
		}
		throw new DataNotFoundException("Admin Data Not Present");
	}

	public ResponseEntity<ResponseStructure<Object>> adminLogin(String email, String password) {

		Admin admin = adminDao.getAdminByEmail(email);
		if (admin != null) {
			String checkPass = Encryption.decrypt(admin.getPassword());
			if (password.equals(checkPass)) {
				ResponseStructure<Object> responseStructure = new ResponseStructure<Object>();
				int otp = (int) (Math.random() * (9999 - 1000) + 1000);
				admin.setOtp(otp);
				adminDao.saveAdmin(admin);
				emailSender.sendSimpleEmail(admin.getEmail(),
						"Enter the Otp to Validate Your Self \n The Generated Otp " + otp, "Verify Your Otp");
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("OTP SENT");
				responseStructure.setData(admin);
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new InvalidCredentialException("Invalid Password");
			}
		} else {
			throw new EmailIdNotFoundException("Admin-Email : " + email + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<Admin>> validateOtp(int otp) {
		Admin admin = adminDao.getAdminByOtp(otp);
		if (admin != null) {
			ResponseStructure<Admin> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Success");
			responseStructure.setData(admin);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new InvalidCredentialException("Invalid OTP");
		}
	}

	public ResponseEntity<ResponseStructure<Object>> verifyEmailBeforeUpdate(String email) {
		Admin admin = adminDao.getAdminByEmail(email);
		if (admin != null) {
			ResponseStructure<Object> responseStructure = new ResponseStructure<>();
			String uuid = UUID.randomUUID().toString();
			String partOfUuid = uuid.substring(0, 11);
			if (partOfUuid.contains("-")) {
				String replace = partOfUuid.replace("-", "");
				admin.setUuid(replace);
				adminDao.saveAdmin(admin);
				emailSender.sendSimpleEmail(admin.getEmail(),
						"Enter the Unique to Validate Your Account \n The Generated Unique ID " + replace,
						"Verify Your Unique Id Before You Change YOur Password");
			} else {
				admin.setUuid(partOfUuid);
				adminDao.saveAdmin(admin);
				emailSender.sendSimpleEmail(admin.getEmail(),
						"Enter the Unique to Validate Your Account \n The Generated Unique ID " + partOfUuid,
						"Verify Your Unique Id Before You Change Your Password");
			}
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Verify Admin By Email-Id");
			responseStructure.setData("Uuid Send To User Email-Id Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new EmailIdNotFoundException("User-Email : " + email + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<Object>> updatePasswordByUuid(String uuid, String newPassword) {
		ResponseStructure<Object> responseStructure = new ResponseStructure<>();
		Admin admin = adminDao.getAdminByUuid(uuid);
		if (admin != null) {
			admin.setPassword(Encryption.encrypt(newPassword));
			adminDao.saveAdmin(admin);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Password Reset");
			responseStructure.setData("Successfully Password Updated");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new InvalidCredentialException("User-Uuid : " + uuid + ", Not Match");
		}
	}

}