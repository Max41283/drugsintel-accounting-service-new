package drugsintel.accounting.dao;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import drugsintel.accounting.model.UserRole;
import drugsintel.accounting.model.UserRoleId;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
	
	UserRole findByUserIdAndDateStartLessThanEqualAndDateEndGreaterThanEqual(Long userId, Timestamp start, Timestamp end);
	
}
