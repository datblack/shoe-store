package com.pro2111.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * Lấy danh sách người dùng theo số điện thoại gần đúng
	 * 
	 * @param phone
	 * @return List<User>
	 */
	@Query("SELECT u FROM User u WHERE u.phone LIKE %:phone%")
	List<User> findApproximatePhone(String phone);

	/**
	 * Tìm người dùng theo email
	 * 
	 * @param email
	 * @return User
	 */
	User findByEmailLike(String email);

	/**
	 * Tìm người dùng theo phone
	 * 
	 * @param phone
	 * @return User
	 */
	User findByPhoneLike(String phone);

	/**
	 * Lấy danh sách người dùng theo email hoặc sdt
	 * 
	 * @param input
	 * @return List<User>
	 */
	@Query("SELECT u FROM User u WHERE u.phone LIKE %:input% OR u.email LIKE %:input%")
	List<User> findApproximatePhoneorEmail(String input);

	/**
	 * Lấy danh sách người bán
	 * 
	 * @return List<User>
	 */
	@Query("SELECT u FROM User u WHERE u.role > 1 AND u.status =1")
	List<User> findUserSalse();

	/**
	 * Lấy danh sách người dùng theo trạng thái
	 * 
	 * @param status
	 * @return List<User>
	 */
	List<User> findByStatusLike(Integer status);

	/**
	 * Tìm người dùng theo email
	 * 
	 * @param email
	 * @return Optional<User>
	 */
	@Query("SELECT u From User u WHERE lower(u.email) Like lower(:mail)")
	Optional<User> checkEmail(@Param("mail") String email);

	/**
	 * Tìm người dùng theo email
	 * 
	 * @param email
	 * @param id
	 * @return Optional<User>
	 */
	@Query("SELECT u FROM User u WHERE u.email like :email AND u.userId != :idUser")
	Optional<User> checkEmailUpdate(@Param("email") String email, @Param("idUser") int id);

	/**
	 * Tìm người dùng theo phone
	 * 
	 * @param phone
	 * @param id
	 * @return Optional<User>
	 */
	@Query("SELECT u FROM User u WHERE u.phone like :phone AND u.userId != :idUser")
	Optional<User> checkPhoneUpdate(@Param("phone") String phone, @Param("idUser") int id);

}
