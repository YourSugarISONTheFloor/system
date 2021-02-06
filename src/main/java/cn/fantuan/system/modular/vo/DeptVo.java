package cn.fantuan.system.modular.vo;

import lombok.Data;

import java.util.List;

@Data
public class DeptVo {
	//ID
	private Long id;
	//父ID
	private Long pid;
	//名称
	private String title;
	//子类菜单
	private List<DeptVo> child;
}
