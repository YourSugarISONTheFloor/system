package cn.fantuan.system.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysMenu implements Serializable {
	// 复合主键要用这个注解
	@TableId(type = IdType.ASSIGN_ID)
	private MenuKey key;
	private Long pid;
	private String icon;
	private String target;
	private Integer sort;
	private Boolean status;
	private String remark;
	@CreatedDate
	private Date create_at;
	@CreatedDate
	private Date update_at;
	private Date delete_at;
}
