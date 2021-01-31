/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.fantuan.system.modular.page;

import lombok.Data;


/**
 * 分页结果的封装(for Layui Table)
 */
@Data
public class LayuiPageInfo {

	private Integer code = 0;

	private String msg = "请求成功";

	private Object data;

	private long count;

	public LayuiPageInfo() {

	}

	public LayuiPageInfo(long count, Object data) {
		this.count = count;
		this.data = data;
	}

}
