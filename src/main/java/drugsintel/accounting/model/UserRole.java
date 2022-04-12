package drugsintel.accounting.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "user_role", schema = "sign")
@IdClass(UserRoleId.class)
public class UserRole {
	@Id
	@Column(name = "user_id")
	Long userId;
	@Id
	@Column(name = "role_id")
	Long roleId;
	@Id
	@Column(name = "role_start")
	Timestamp dateStart;
	@Id
	@Column(name = "role_end")
	Timestamp dateEnd;
}
