package com.pro2111.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.beans.CustomerBean;
import com.pro2111.entities.User;
import com.pro2111.repositories.UserRepository;
import com.pro2111.service.UserService;
import com.pro2111.utils.Constant;
import com.pro2111.utils.EncryptUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Lấy ra danh sách người dùng
	 * @param 
	 * @return List<User>
	 */
	@Override
	public synchronized List<User> getAll() {
		return userRepository.findAll();
	}
	
	/**
	 * Lấy ra người dùng theo mã
	 * @param id
	 * @return User
	 */
	@Override
	public synchronized User findById(Integer id) {
		return userRepository.findById(id).get();
	}
	
	/**
	 * Thêm mới người dùng
	 * @param 
	 * @return User
	 */
	@Override
	public synchronized User create(User user) {
		return userRepository.save(user);
	}
	
	/**
	 * Cập nhật người dùng 
	 * @param 
	 * @return User
	 */
	@Override
	public synchronized User update(User user) {
		return userRepository.save(user);
	}
	
	/**
	 * Lấy ra người dùng theo số điện thoại gần đúng
	 * @param phone
	 * @return List<User>
	 */
	@Override
	public synchronized List<User> findApproximatePhone(String phone) {
		return userRepository.findApproximatePhone(phone);
	}
	
	/**
	 * Xóa người dùng
	 * @param user
	 * @return User
	 */
	@Override
	public synchronized User delete(User user) {
		userRepository.delete(user);
		return user;
	}
	
	/**
	 * Lấy người dùng theo email
	 * @param email
	 * @return User
	 */
	@Override
	public synchronized User findByEmail(String email) {
		return userRepository.findByEmailLike(email);
	}
	
	/**
	 * Lấy người dùng theo số điện thoại
	 * @param phone
	 * @return User
	 */
	@Override
	public synchronized User findByPhone(String phone) {
		return userRepository.findByPhoneLike(phone);
	}
	
	/**
	 * Lấy danh sách  người dùng theo số điện thoại hoặc email gần đúng
	 * @param input
	 * @return List<User>
	 */
	@Override
	public synchronized List<User> findApproximatePhoneorEmail(String input) {
		return userRepository.findApproximatePhoneorEmail(input);
	}
	
	/**
	 * Lấy danh sách  người bán
	 * @param 
	 * @return List<User>
	 */
	@Override
	public synchronized List<User> findUserSalse() {
		return userRepository.findUserSalse();
	}
	
	/**
	 * Lấy danh sách  người dùng theo trạng thái
	 * @param status
	 * @return List<User>
	 */
	@Override
	public synchronized List<User> findByStatus(Integer status) {
		return userRepository.findByStatusLike(status);
	}
	
	/**
	 * Kiểm tra người dùng theo email
	 * @param email
	 * @return Optional<User>
	 */
	@Override
	public synchronized Optional<User> checkEmail(String email) {
		return userRepository.checkEmail(email);
	}
	
	/**
	 * Kiểm tra người dùng theo phone
	 * @param phone
	 * @return Optional<User>
	 */
	@Override
	public synchronized User createCustomer(CustomerBean customerBean) {
		User user = new User();
		user.setFullName(customerBean.getFullname());
		user.setEmail(customerBean.getEmail());
		user.setPhone(customerBean.getPhone());
		user.setCreatedDate(LocalDateTime.now());
		user.setSex(customerBean.getSex());
		user.setRole(Constant.ROLE_CUSTOMER_USER);
		user.setStatus(Constant.STATUS_ACTIVE_USER);
		user.setPassword(EncryptUtil.encrypt(Constant.PASSWORD_DEFAULT_USER));
		userRepository.save(user);
		return user;
	}
	
	/**
	 * Kiểm tra người dùng theo email
	 * @param email
	 * @param id
	 * @return Optional<User>
	 */
	@Override
	public Optional<User> checkEmailUpdate(String email, int id) {	
		return userRepository.checkEmailUpdate(email, id);
	}
	
	/**
	 * Kiểm tra người dùng theo phone
	 * @param phone
	 * @param id
	 * @return Optional<User>
	 */
	@Override
	public Optional<User> checkPhoneUpdate(String phone, int id) {	
		return userRepository.checkPhoneUpdate(phone, id);
	}

}
