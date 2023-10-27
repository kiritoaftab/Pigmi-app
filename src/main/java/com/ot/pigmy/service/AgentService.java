package com.ot.pigmy.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ot.pigmy.dao.AgentDao;
import com.ot.pigmy.dto.Agent;
import com.ot.pigmy.dto.AgentAadharCardImage;
import com.ot.pigmy.dto.AgentPanCardImage;
import com.ot.pigmy.dto.AgentProfileImage;
import com.ot.pigmy.dto.ResponseStructure;
import com.ot.pigmy.exception.DataNotFoundException;
import com.ot.pigmy.exception.DuplicateDataEntryException;
import com.ot.pigmy.exception.EmailIdNotFoundException;
import com.ot.pigmy.exception.IdNotFoundException;
import com.ot.pigmy.exception.InvalidCredentialException;
import com.ot.pigmy.exception.PhoneNumberNotFoundException;
import com.ot.pigmy.exception.UserStatusIsOffline;
import com.ot.pigmy.util.EmailSender;
import com.ot.pigmy.util.Encryption;
import com.ot.pigmy.util.ImageUtils;

@Service
public class AgentService {

	@Autowired
	private AgentDao agentDao;

	@Autowired
	private EmailSender emailSender;

	public ResponseEntity<ResponseStructure<Agent>> saveAgent(Agent agent) {

		ResponseStructure<Agent> responseStructure = new ResponseStructure<>();

		if (agentDao.getAgentByEmail(agent.getEmail()) != null || agentDao.getAgentByPhone(agent.getPhone()) != null) {

			throw new DuplicateDataEntryException("Agent Already Exist's");

		} else {
			agent.setPassword(Encryption.encrypt(agent.getPassword()));
			emailSender.sendSimpleEmail(agent.getEmail(),
					"Greetings \nYour Profile in Pigmy Account Has Been Created.\nThank You.",
					"Hello " + agent.getAgentName());
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("Admin Saved Successfully");
			responseStructure.setData(agentDao.saveAgent(agent));
			return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
		}
	}

	public ResponseEntity<ResponseStructure<Agent>> getAgentById(String id) {
		ResponseStructure<Agent> responseStructure = new ResponseStructure<>();
		Agent agent = agentDao.getAgentById(id);
		if (agent != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Agent Details By Id");
			responseStructure.setData(agent);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Agent ID " + id + ", NOT FOUND");
		}
	}

	public ResponseEntity<ResponseStructure<List<Agent>>> getAllAgent() {
		ResponseStructure<List<Agent>> responseStructure = new ResponseStructure<>();
		List<Agent> list = agentDao.getAllAgents();
		if (list.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Agent Fetched");
			responseStructure.setData(list);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Agent Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<Page<Agent>>> getAgentsWithPaginationAndSorting(int offset, int pageSize,
			String field) {
		ResponseStructure<Page<Agent>> responseStructure = new ResponseStructure<>();
		Page<Agent> page = agentDao.findAgentsWithPaginationAndSorting(offset, pageSize, field);
		if (page.getSize() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Agents Fetched");
			responseStructure.setRecordCount(page.getSize());
			responseStructure.setData(page);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Agents Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<String>> deleteAgentById(String id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		Agent agent = agentDao.getAgentById(id);
		if (agent != null) {
			agentDao.deleteAgent(agent);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Agent Of Id " + id + " Data Deleted");
			responseStructure.setData("Agent Data Deleted Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Agent Id " + id + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<Agent>> updateAgent(Agent agent) {
		ResponseStructure<Agent> responseStructure = new ResponseStructure<>();
		Agent agent1 = agentDao.getAgentById(agent.getId());
		if (agent1 != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Agent Updated Successfully");
			agent.setPassword(Encryption.encrypt(agent.getPassword()));
			responseStructure.setData(agentDao.saveAgent(agent));
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Agent Id " + agent.getId() + ", Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<Agent>> getAdminByEmail(String email) {
		Agent agent = agentDao.getAgentByEmail(email);
		if (agent != null) {
			ResponseStructure<Agent> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Agent By Email-Id");
			responseStructure.setData(agent);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new EmailIdNotFoundException("Agent-Email : " + email + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<Agent>> getAgentByPhone(String phone) {
		Agent agent = agentDao.getAgentByPhone(phone);
		if (agent != null) {
			ResponseStructure<Agent> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Agent By PhoneNumber");
			responseStructure.setData(agent);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new PhoneNumberNotFoundException("Agent-PhoneNumber : " + phone + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<List<Agent>>> getAgentByName(String agentName) {
		ResponseStructure<List<Agent>> responseStructure = new ResponseStructure<>();
		List<Agent> agents = agentDao.getAgentByName(agentName);
		for (Agent agent : agents) {
			if (agent.getAgentName().equalsIgnoreCase(agentName) && agents.size() > 0) {
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Fetched Agent By Name");
				responseStructure.setData(agents);
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new DataNotFoundException("Agent Data Not Present");
			}
		}
		throw new DataNotFoundException("Agent Data Not Present");
	}

	public ResponseEntity<ResponseStructure<Object>> agentLogin(String email, String password) {

		Agent agent = agentDao.getAgentByEmail(email);
		if (agent != null) {
			String checkPass = Encryption.decrypt(agent.getPassword());
			if (password.equals(checkPass)) {
				if (agent.isStatus() == true) {
					ResponseStructure<Object> responseStructure = new ResponseStructure<Object>();
					int otp = (int) (Math.random() * (9999 - 1000) + 1000);
					agent.setOtp(otp);
					agentDao.saveAgent(agent);
					emailSender.sendSimpleEmail(agent.getEmail(),
							"Enter the Otp to Validate Your Self \n The Generated Otp " + otp, "Verify Your Otp");
					responseStructure.setStatus(HttpStatus.OK.value());
					responseStructure.setMessage("OTP SENT");
					responseStructure.setData(agent);
					return new ResponseEntity<>(responseStructure, HttpStatus.OK);
				} else {
					throw new UserStatusIsOffline("Ask Admin To Make You Online");
				}
			} else {
				throw new InvalidCredentialException("Invalid Password");
			}
		} else {
			throw new EmailIdNotFoundException("Agent-Email : " + email + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<Object>> changeAgentProfileStatusToOnline(String agentId) {
		Agent agent = agentDao.getAgentById(agentId);
		if (agent != null) {
			ResponseStructure<Object> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Status Changed");
			agent.setStatus(true);
			agentDao.saveAgent(agent);
			responseStructure.setData("Status Changed To Online");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Agent Id " + agentId + ", Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<Object>> changeAgentProfileStatusToOffline(String agentId) {
		Agent agent = agentDao.getAgentById(agentId);
		if (agent != null) {
			ResponseStructure<Object> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Status Changed");
			agent.setStatus(false);
			agentDao.saveAgent(agent);
			responseStructure.setData("Status Changed To Offline");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Agent Id " + agentId + ", Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<Agent>> validateOtp(int otp) {
		Agent agent = agentDao.getUserByOtp(otp);
		if (agent != null) {
			ResponseStructure<Agent> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Success");
			responseStructure.setData(agent);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new InvalidCredentialException("Invalid OTP");
		}
	}

	public ResponseEntity<ResponseStructure<Object>> verifyEmailBeforeUpdate(String email) {
		Agent agent = agentDao.getAgentByEmail(email);
		if (agent != null) {
			ResponseStructure<Object> responseStructure = new ResponseStructure<>();
			String uuid = UUID.randomUUID().toString();
			String partOfUuid = uuid.substring(0, 11);
			if (partOfUuid.contains("-")) {
				String replace = partOfUuid.replace("-", "");
				agent.setUuid(replace);
				agentDao.saveAgent(agent);
				emailSender.sendSimpleEmail(agent.getEmail(),
						"Enter the Unique to Validate Your Account \n The Generated Unique ID " + replace,
						"Verify Your Unique Id Before You Change YOur Password");
			} else {
				agent.setUuid(partOfUuid);
				agentDao.saveAgent(agent);
				emailSender.sendSimpleEmail(agent.getEmail(),
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
		Agent agent = agentDao.getUserByUuid(uuid);
		if (agent != null) {
			agent.setPassword(Encryption.encrypt(newPassword));
			agentDao.saveAgent(agent);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Password Reset");
			responseStructure.setData("Successfully Password Updated");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new InvalidCredentialException("User-Uuid : " + uuid + ", Not Match");
		}
	}

	public ResponseEntity<ResponseStructure<String>> uploadAgentAadharCardImageByAgentId(String agentId,
			MultipartFile file) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		Agent agent = agentDao.getAgentById(agentId);
		if (agent != null) {
			if (agent.getAgentAadharCardImage() != null) {

				if (file.getSize() > 4 * 1024 * 1024) {
					throw new InvalidCredentialException("File size exceeds the maximum allowed limit (4MB)");
				} else {
					try {
						agent.setAgentAadharCardImage(AgentAadharCardImage.builder().name(file.getOriginalFilename())
								.type(file.getContentType()).imageData(ImageUtils.compressImage(file.getBytes()))
								.id(agent.getAgentAadharCardImage().getId()).build());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					agent.setAgentAadharCardImage(AgentAadharCardImage.builder().name(file.getOriginalFilename())
							.type(file.getContentType()).imageData(ImageUtils.compressImage(file.getBytes())).build());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			agentDao.saveAgent(agent);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Agent Aadhar Card Image Saved Successfully");
			responseStructure.setData("Image Saved Successfully For Agent of Id -> " + agentId);
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
		} else {

			throw new IdNotFoundException("Agent ID " + agentId + " not Found");
		}
	}

	public ResponseEntity<?> downloadAgentAadharCardImageByAgentId(String agentId) {
		Agent agent = agentDao.getAgentById(agentId);
		if (agent != null) {
			AgentAadharCardImage aadharCardImage = agent.getAgentAadharCardImage();
			if (aadharCardImage != null) {
				aadharCardImage.setImageData(ImageUtils.decompressImage(aadharCardImage.getImageData()));
				byte[] img = aadharCardImage.getImageData();
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(img);
			} else {
				throw new IdNotFoundException("Agent Aadhar Card Image is Not Present" + agentId);
			}
		} else {
			throw new IdNotFoundException("Agent Id is Not Present" + agentId);
		}
	}

	public ResponseEntity<ResponseStructure<String>> uploadAgentPanCardImageByAgentId(String agentId,
			MultipartFile file) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		Agent agent = agentDao.getAgentById(agentId);
		if (agent != null) {
			if (agent.getAgentPanCardImage() != null) {

				if (file.getSize() > 4 * 1024 * 1024) {
					throw new InvalidCredentialException("File size exceeds the maximum allowed limit (4MB)");
				} else {
					try {
						agent.setAgentPanCardImage(AgentPanCardImage.builder().name(file.getOriginalFilename())
								.type(file.getContentType()).imageData(ImageUtils.compressImage(file.getBytes()))
								.id(agent.getAgentAadharCardImage().getId()).build());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					agent.setAgentPanCardImage(AgentPanCardImage.builder().name(file.getOriginalFilename())
							.type(file.getContentType()).imageData(ImageUtils.compressImage(file.getBytes())).build());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			agentDao.saveAgent(agent);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Agent Pan Card Image Saved Successfully");
			responseStructure.setData("Image Saved Successfully For Agent of Id -> " + agentId);
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
		} else {

			throw new IdNotFoundException("Agent ID " + agentId + " not Found");
		}
	}

	public ResponseEntity<?> downloadAgentPanCardImageByAgentId(String agentId) {
		Agent agent = agentDao.getAgentById(agentId);
		if (agent != null) {
			AgentPanCardImage agentPanCardImage = agent.getAgentPanCardImage();
			if (agentPanCardImage != null) {
				agentPanCardImage.setImageData(ImageUtils.decompressImage(agentPanCardImage.getImageData()));
				byte[] img = agentPanCardImage.getImageData();
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(img);
			} else {
				throw new IdNotFoundException("Agent Pan Card Image is Not Present" + agentId);
			}
		} else {
			throw new IdNotFoundException("Agent Id is Not Present" + agentId);
		}
	}

	public ResponseEntity<ResponseStructure<String>> uploadAgentProfileImageByAgentId(String agentId,
			MultipartFile file) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		Agent agent = agentDao.getAgentById(agentId);
		if (agent != null) {
			if (agent.getAgentProfileImage() != null) {

				if (file.getSize() > 4 * 1024 * 1024) {
					throw new InvalidCredentialException("File size exceeds the maximum allowed limit (4MB)");
				} else {
					try {
						agent.setAgentProfileImage(AgentProfileImage.builder().name(file.getOriginalFilename())
								.type(file.getContentType()).imageData(ImageUtils.compressImage(file.getBytes()))
								.id(agent.getAgentAadharCardImage().getId()).build());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					agent.setAgentProfileImage(AgentProfileImage.builder().name(file.getOriginalFilename())
							.type(file.getContentType()).imageData(ImageUtils.compressImage(file.getBytes())).build());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			agentDao.saveAgent(agent);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Agent Pan Card Image Saved Successfully");
			responseStructure.setData("Image Saved Successfully For Agent of Id -> " + agentId);
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
		} else {

			throw new IdNotFoundException("Agent ID " + agentId + " not Found");
		}
	}

	public ResponseEntity<?> downloadAgentProfileImageByAgentId(String agentId) {
		Agent agent = agentDao.getAgentById(agentId);
		if (agent != null) {
			AgentProfileImage agentProfileImage = agent.getAgentProfileImage();
			if (agentProfileImage != null) {
				agentProfileImage.setImageData(ImageUtils.decompressImage(agentProfileImage.getImageData()));
				byte[] img = agentProfileImage.getImageData();
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(img);
			} else {
				throw new IdNotFoundException("Agent Pan Card Image is Not Present" + agentId);
			}
		} else {
			throw new IdNotFoundException("Agent Id is Not Present" + agentId);
		}
	}

	public ResponseEntity<ResponseStructure<Agent>> getAgentByIdOrAgentByName(String input) {
		ResponseStructure<Agent> responseStructure = new ResponseStructure<>();
		if (input.matches(".*-(\\d+)")) {
			Agent agent = agentDao.getAgentById(input);
			if (agent != null) {
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Fetched Agent Details By Id");
				responseStructure.setData(agent);
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new IdNotFoundException("Agent ID " + input + ", NOT FOUND");
			}
		} else {
			List<Agent> agents = agentDao.getAgentByName(input);
			for (Agent agent : agents) {
				if (agent.getAgentName().equalsIgnoreCase(input) && agents.size() > 0) {
					responseStructure.setStatus(HttpStatus.OK.value());
					responseStructure.setMessage("Fetched Agent By Name");
					responseStructure.setData(agent);
					return new ResponseEntity<>(responseStructure, HttpStatus.OK);
				} else {
					throw new DataNotFoundException("Agent Data Not Present");
				}
			}
			return null;
		}
	}

}