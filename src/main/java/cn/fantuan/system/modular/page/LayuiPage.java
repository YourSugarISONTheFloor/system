package cn.fantuan.system.modular.page;

import lombok.Data;

/**
 * @ Description   :  layui表格前端返回
 * @ Author        :  zhuoxiaoya
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */
@Data
public class LayuiPage<T> {
    //第几页
    private Integer page;
    //每页行数
    private Integer limit;

    //开始行数
    private Integer start = 0;
    //结束行数
    private Integer end;


    //更新一下page类的页码
    public  LayuiPage getLayuiPage(LayuiPage page){
        if(page.getPage() == 1){
            page.setEnd(page.getLimit());
        }else {
            page.setStart((page.getPage()-1)* page.getLimit());
            page.setEnd(page.getPage() * page.getLimit());
        }
        return page;
    }
}
