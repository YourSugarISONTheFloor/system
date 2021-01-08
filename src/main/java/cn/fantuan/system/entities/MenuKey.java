package cn.fantuan.system.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuKey implements Serializable {
	private Long id;
	private String title;
	private String href;
}
