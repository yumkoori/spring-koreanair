package org.doit.admin.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
	
	private int userno;
	private String grade;
	private String userid;
	private String pw;
	private String email;
	private String koname;
	private String enname;
	private Date birthdate;
	private String gender;
	private String address;
	private String phonenumber;
	private Date createdat;
	private String status;
}
