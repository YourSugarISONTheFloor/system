package cn.fantuan.system.core.shiro;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShiroUser implements Serializable {
	private static final long serialVersionUID = 1L;
	//用户主键ID
	private Long id;
	//电话
	private Long phone;
	//邮箱
	private String email;
	//密码
	private String password;
	//名字
	private String name;
	//状态
	private Integer status;
	//头像
	private String avatar;
	//部门id
	private Long deptId;
	//角色集
	private List<Long> roleList;
	//部门名称
	private String deptName;
	//角色名称集
	private List<String> roleNames;

}
