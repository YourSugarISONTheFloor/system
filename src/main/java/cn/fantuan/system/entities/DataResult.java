package cn.fantuan.system.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataResult<T> {
	private Integer code;
	private String msg;
	private Integer count;
	private T data;
}
