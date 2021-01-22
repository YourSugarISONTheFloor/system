package cn.fantuan.system.modular.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuVo {
	//ID
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
	//子类菜单
	private List<MenuVo> child;
}
