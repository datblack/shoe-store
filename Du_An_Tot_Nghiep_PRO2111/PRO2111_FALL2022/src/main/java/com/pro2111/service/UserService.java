package com.pro2111.service;

import java.util.List;
import java.util.Optional;

import com.pro2111.beans.CustomerBean;
import com.pro2111.entities.User;

public interface UserService {
	/**
	 * Lấy tất cả danh sách người dùng
	 * 
	 * @param
	 * @return
	 */
	List<User> getAll();

	/**
	 * Tìm người dùng theo mã
	 * 
	 * @param
	 * @return User
	 */
	User findById(Integer id);

	/**
	 * Thêm mới người dùng
	 * 
	 * @param
	 * @return User
	 */
	User create(User user);

	/**
	 * Cập nhật người dùng
	 * 
	 * @param user
	 * @return
	 */
	User update(User user);

	/**
	 * Tìm người dùng theo số điện thoại gần đúng
	 * 
	 * @param phone
	 * @return List<User>
	 */
	List<User> findApproximatePhone(String phone);

	/**
	 * Xóa người dùng
	 * 
	 * @param user
	 * @return User
	 */
	User delete(User user);

	/**
	 * Tìm người dùng theo email
	 * 
	 * @param email
	 * @return User
	 */
	User findByEmail(String email);

	/**
	 * Lấy người dùng theo sdt
	 * 
	 * @param phone
	 * @return User
	 */
	User findByPhone(String phone);

	/**
	 * Check trùng email
	 * 
	 * @param mail
	 * @param id
	 * @return Optional<User>
	 */
	Optional<User> checkEmailUpdate(String email, int id);

	/**
	 * Check trùng số điện thoại
	 * 
	 * @param phone
	 * @param id
	 * @return Optional<User>
	 */
	Optional<User> checkPhoneUpdate(String phone, int id);

	/**
	 * Tìm người dùng theo mã
	 * 
	 * @param id
	 * @return Optional<User>
	 */
	Optional<User> checkEmail(String email);

	/**
	 * Tìm list user theo số điện thoại hoặc email gần đúng
	 * 
	 * @param input
	 * @return List<User>
	 */
	List<User> findApproximatePhoneorEmail(String input);

	/**
	 * Lấy danh sách người bán
	 * 
	 * @return List<User>
	 */
	List<User> findUserSalse();

	/**
	 * Lấy danh sách người dùng theo trạng thái
	 * 
	 * @return List<User>
	 */
	List<User> findByStatus(Integer status);

	/**
	 * Thêm mới khách hàng
	 * 
	 * @param customerBean
	 * @return User
	 */
	User createCustomer(CustomerBean customerBean);
}
