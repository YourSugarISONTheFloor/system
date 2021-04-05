package cn.fantuan.system.modular.util.menu;

import cn.fantuan.system.modular.service.DeptServer;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库数据，转为父子关系包含的数据集合。列表封装工具类
 */
public class TreeUtil<T> {
	@Autowired
	private DeptServer deptServer;

	/**
	 * @param treeList 传递的数据集合
	 * @param pid      顶级父类ID
	 * @return
	 */
	public List<T> toTree(List<T> treeList, Long pid) {
		List<T> retList = new ArrayList<>();
		try {
			for (T parent : treeList) {
				Method getPid = parent.getClass().getMethod("getPid");
				if (pid.equals(getPid.invoke(parent))) {
					//System.out.println(parent);
					retList.add(findChildren(parent, treeList));
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return retList;
	}

	/**
	 * @param parent   父级数据
	 * @param treeList 其余集合数据
	 * @return
	 */
	private T findChildren(T parent, List<T> treeList) {
		try {
			//父级数据类
			Class p = parent.getClass();

			//父级数据类的一些方法声明
			//获取ID方法
			Method getId = p.getMethod("getId");
			//获取子类的方法
			Method getChild = p.getMethod("getChild");
			//设置子类的方法
			Method setChild = p.getMethod("setChild", List.class);

			//遍历其他数据集合
			for (T child : treeList) {
				//System.out.println("child数据：" + child);
				//其他数据集合的获取父类ID的方法
				Method getPid = child.getClass().getMethod("getPid");

				//判断其他数据集合中的父类ID是否与该传递的父类的ID相等
				if (getId.invoke(parent).equals(getPid.invoke(child))) {
					//判断该父类的子类是否有值
					if (getChild.invoke(parent) == null) {
						//没有值设置子类为空的数组
						setChild.invoke(parent, new ArrayList());
					}
					//不管是否有值都会执行
					//获取之类的数据格式
					Class<?> aClass = getChild.invoke(parent).getClass();
					//通过反射调用对象的方法
					Object newInstance = aClass.newInstance();
					//给这个对象赋值
					newInstance = getChild.invoke(parent);
					//调用该对象的方法，设置值
					//给该方法继续追加属于它的下级的数据
					newInstance.getClass().getMethod("add", Object.class).invoke(newInstance, findChildren(child, treeList));
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return parent;
	}


	/**
	 * @param treeList 传递的数据集合
	 * @return
	 */
	public List toDeptTree(List<T> treeList,Object msg) {
		ArrayList retList = new ArrayList<>();
		try {
			for (T parent : treeList) {
				Method getName = parent.getClass().getMethod("getName");
				Map map = new HashMap();
				map.put("name", getName.invoke(parent));
				map.put("dept", msg);
				retList.add(map);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return retList;
	}
}
