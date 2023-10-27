package com.ot.pigmy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ot.pigmy.dto.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {

	public Optional<Admin> findById(String id);

	public Optional<Admin> findByEmail(String email);

	public Optional<Admin> findByPhone(String phone);

	public Optional<List<Admin>> findByAdminName(String adminName);

	public Optional<Admin> findByOtp(int otp);

	public Optional<Admin> findByUuid(String uuid);

}