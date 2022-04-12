package drugsintel.accounting.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import drugsintel.accounting.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Optional<Account> findByUserName(String userName);

}
