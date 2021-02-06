package cn.fantuan.system.modular.entities.outside;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User implements Serializable {
	@TableId(type = IdType.AUTO)
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
	//部门ID
	@TableField("deptId")
	private Integer deptId;
	//状态
	//MySQL里有四个常量：true,false,TRUE,FALSE,它们分别代表1,0,1,0
	private Integer status;
	//头像
	private String avatar;
}
