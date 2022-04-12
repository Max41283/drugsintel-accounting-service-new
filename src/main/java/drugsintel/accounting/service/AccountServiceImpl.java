package drugsintel.accounting.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import drugsintel.accounting.dao.AccountRepository;
import drugsintel.accounting.dao.RoleRepository;
import drugsintel.accounting.dao.UserRoleRepository;
import drugsintel.accounting.dto.ChangeRoleDto;
import drugsintel.accounting.dto.UserAccountDto;
import drugsintel.accounting.dto.UserRegisterDto;
import drugsintel.accounting.dto.UserUpdateDto;
import drugsintel.accounting.exceptions.LoginExistsExeption;
import drugsintel.accounting.exceptions.EntityNotFoundException;
import drugsintel.accounting.model.Account;
import drugsintel.accounting.model.Role;
import drugsintel.accounting.model.UserRole;

@Service
public class AccountServiceImpl implements AccountService {
	
	AccountRepository accountRepository;
	UserRoleRepository userRoleRepository;
	RoleRepository roleRepository;
	ModelMapper modelMapper;
	PasswordEncoder passwordEncoder;

	@Autowired
	public AccountServiceImpl(AccountRepository accountRepository, UserRoleRepository userRoleRepository,
			RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
		this.accountRepository = accountRepository;
		this.userRoleRepository = userRoleRepository;
		this.roleRepository = roleRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public void addUser(UserRegisterDto userRegisterDto) {
		Account account = accountRepository.findByUserName(userRegisterDto.getUserName()).orElse(null);
		if (account != null) {
			throw new LoginExistsExeption(userRegisterDto.getUserName());
		}
		account = modelMapper.map(userRegisterDto, Account.class);
		account.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
		Long userId = accountRepository.save(account).getId();
		Long roleId = roleRepository.findByRoleName("USER").get().getId();
		Timestamp start = Timestamp.valueOf(LocalDateTime.now());
		Timestamp end = Timestamp.valueOf(LocalDateTime.now().plusYears(100));
		UserRole userRole = new UserRole(userId, roleId, start, end);
		userRoleRepository.save(userRole);
		return;
	}

//	@Override
//	public UserTokenDto loginUser(String login) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	@Transactional(readOnly = true)
	public UserAccountDto getUser(Long id) {
		Timestamp now = Timestamp.valueOf(LocalDateTime.now());
		Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User " + id));
		UserAccountDto userAccountDto = modelMapper.map(account, UserAccountDto.class);
		UserRole userRole = userRoleRepository.findByUserIdAndDateStartLessThanEqualAndDateEndGreaterThanEqual(id, now, now);
		userAccountDto.setExpiryDate(userRole.getDateEnd().toLocalDateTime().toLocalDate());
		Role role = roleRepository.findById(userRole.getRoleId()).get();
		userAccountDto.setRole(role.getRoleName());
		return userAccountDto;
	}

	@Override
	@Transactional
	public UserAccountDto removeUser(Long id) {
		Timestamp now = Timestamp.valueOf(LocalDateTime.now());
		Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User " + id));
		UserAccountDto userAccountDto = modelMapper.map(account, UserAccountDto.class);
		UserRole userRole = userRoleRepository.findByUserIdAndDateStartLessThanEqualAndDateEndGreaterThanEqual(id, now, now);
		userAccountDto.setExpiryDate(userRole.getDateEnd().toLocalDateTime().toLocalDate());
		Role role = roleRepository.findById(userRole.getRoleId()).get();
		userAccountDto.setRole(role.getRoleName());
		accountRepository.deleteById(id);
		return userAccountDto;
	}

	@Override
	@Transactional
	public UserAccountDto updateUser(Long id,UserUpdateDto userUpdateDto) {
		Timestamp now = Timestamp.valueOf(LocalDateTime.now());
		Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User " + id));
		UserRole userRole = userRoleRepository.findByUserIdAndDateStartLessThanEqualAndDateEndGreaterThanEqual(id, now, now);
		account.setUserName(userUpdateDto.getUsername());
		account.setEmail(userUpdateDto.getEmail());
		account = accountRepository.save(account);
		UserAccountDto userAccountDto = modelMapper.map(account, UserAccountDto.class);
		userAccountDto.setExpiryDate(userRole.getDateEnd().toLocalDateTime().toLocalDate());
		Role role = roleRepository.findById(userRole.getRoleId()).get();
		userAccountDto.setRole(role.getRoleName());
		return userAccountDto;
	}

	@Override
	public void changePassword(Long id, String newPassword) {
		Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User " + id));
		newPassword = passwordEncoder.encode(newPassword);
		account.setPassword(newPassword);
		accountRepository.save(account);
	}

	@Override
	public UserAccountDto changeRole(ChangeRoleDto changeRoleDto) {
		Account account = accountRepository.findByUserName(changeRoleDto.getUserName())
				.orElseThrow(() -> new EntityNotFoundException(changeRoleDto.getUserName()));
		Role role = roleRepository.findByRoleName(changeRoleDto.getRoleName())
				.orElseThrow(() -> new EntityNotFoundException("Role " + changeRoleDto.getRoleName()));
		Long userId = account.getId();
		Long roleId = role.getId();
		Timestamp start = Timestamp.valueOf(LocalDateTime.now());
		Timestamp end = Timestamp.valueOf(LocalDateTime.now().plusDays(changeRoleDto.getValidityInDays()));
		UserRole userRole = new UserRole(userId, roleId, start, end);
		UserAccountDto userAccountDto = modelMapper.map(account, UserAccountDto.class);
		userAccountDto.setRole(role.getRoleName());
		userAccountDto.setExpiryDate(userRole.getDateEnd().toLocalDateTime().toLocalDate());
		userRoleRepository.save(userRole);
		return userAccountDto;
	}

}
