package cn.fantuan.system.modular.entities.outside;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class Role {
	//角色ID
	@TableId(type = IdType.AUTO)
	private Long role_id;
	//角色名称
	private String name;
	//角色描述
	private String remark;
	//部门ID
	private Long dep_id;
}
