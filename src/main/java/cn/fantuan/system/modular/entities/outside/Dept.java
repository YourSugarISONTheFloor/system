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
@TableName("sys_dept")
public class Dept implements Serializable {
	private static final long serialVersionUID = 1L;
	//部门ID
	@TableId(type = IdType.AUTO)
	private Long deptId;

	//父级部门ID
	@TableField("pid")
	private Long pid;

	//部门名称
	@TableField("name")
	private String deptName;
}
