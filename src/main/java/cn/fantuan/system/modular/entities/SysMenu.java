package cn.fantuan.system.modular.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_menu")
public class SysMenu implements Serializable {
	//ID
	@TableId(type = IdType.AUTO)
	private Long id;
	//父ID
	private Long pid;
	//名称
	private String title;
	//图标
	private String icon;
	//链接
	private String href;
	//链接打开方式
	private String target;
	//菜单排序
	private Integer sort;
	//状态(0:禁用,1:启用)
	private Boolean status;
	//是否为菜单
	//@TableField指定表中的字段名
	@TableField("isMenu")
	private Boolean isMenu;
}
