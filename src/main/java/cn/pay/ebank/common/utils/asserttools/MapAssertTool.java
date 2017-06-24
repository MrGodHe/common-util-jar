package cn.pay.ebank.common.utils.asserttools;


import java.util.Map;

import cn.pay.ebank.common.utils.CollectionUtils;

/**
 * Created by zhyang on 2015/2/16 0016.
 */
public class MapAssertTool extends AssertToolBase<MapAssertTool, Map<?, ?>> {

	private MapAssertTool (Map<?, ?> maps, String name) {
		super (maps, name);
	}

	/**
	 * 断言对象
	 * <p/>
	 * assertThat(name,"Name");
	 *
	 * @param maps 需要断言的字符串
	 * @param name 对象名称
	 *
	 * @return
	 */
	public static MapAssertTool assertThat (Map<?, ?> maps, String name) {
		return new MapAssertTool (maps, name);
	}


	/**
	 * 断言对象
	 * <p/>
	 * assertThat(str);
	 *
	 * @param maps 需要断言的对象
	 *
	 * @return
	 */
	public static MapAssertTool assertThat (Map<?, ?> maps) {
		return assertThat (maps, "");
	}

	public MapAssertTool isEmpty () {
		if (CollectionUtils.isNotEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 应为空!");
		}
		return this;
	}

	public MapAssertTool isNotEmpty () {
		if (CollectionUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 不应为空!");
		}
		return this;
	}

	public MapAssertTool hasSize (int size) {
		if (CollectionUtils.isEmpty (object) || object.size () != size) {
			throw new IllegalArgumentException (objectName + " 长度应该[" + size + "]!");
		}
		return this;
	}

	public MapAssertTool containsKeys (Object ...keys) {
		if(keys!=null){
			for(Object key:keys){
				containsKey (key);
			}
		}
		return this;
	}
	public MapAssertTool containsKey (Object key) {
		if (CollectionUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 不应为空!");
		} else {
			if (object.containsKey (key)) {
				return this;
			}
		}
		throw new IllegalArgumentException (objectName + " 应包含[" + key + "]!");
	}

	public MapAssertTool containsValue (Object value) {
		if (CollectionUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 不应为空!");
		} else {
			if (object.containsValue (value)) {
				return this;
			}
		}
		throw new IllegalArgumentException (objectName + " 应包含[" + value + "]!");
	}

	public MapAssertTool contains (Object key, Object value) {
		containsKey (key);
		containsValue (value);
		return this;
	}


}
