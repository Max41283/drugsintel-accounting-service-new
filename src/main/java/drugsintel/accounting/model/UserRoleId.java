package drugsintel.accounting.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserRoleId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3895633133754208600L;
	
	Long userId;
	Long roleId;
	Timestamp dateStart;
	Timestamp dateEnd;
	
}
