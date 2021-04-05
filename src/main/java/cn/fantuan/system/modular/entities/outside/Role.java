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
@TableName("sys_role")
public class Role implements Serializable {
	//角色ID
	@TableId(type = IdType.AUTO)
	@TableField("role_id")
	private Long roleId;
	//角色名称
	private String name;
	//角色描述
	private String remark;
}
