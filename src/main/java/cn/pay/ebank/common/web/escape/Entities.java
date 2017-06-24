/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@ebank.pay.cn 2015-02-06 10:56 创建
 *
 */
package cn.pay.ebank.common.web.escape;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.UnhandledException;

/**
 * @author
 */
public class Entities {
	
	private static final String[][] BASIC_ARRAY = { { "quot", "34" }, // " - double-quote
													//{ "amp", "38" }, // & - ampersand
													{ "lt", "60" }, // < - less-than
													{ "gt", "62" }, // > - greater-than
													{ "acute", "39" } };//' -  single-quote
	
	public static final Entities BASIC;
	static {
		Entities basic = new Entities();
		basic.addEntities(BASIC_ARRAY);
		BASIC = basic;
	}
	
	private final EntityMap map;
	
	/**
	 * Default constructor.
	 */
	public Entities() {
		map = new LookupEntityMap();
	}
	
	public void addEntities(String[][] entityArray) {
		for (int i = 0; i < entityArray.length; ++i) {
			addEntity(entityArray[i][0], Integer.parseInt(entityArray[i][1]));
		}
	}
	
	public void addEntity(String name, int value) {
		map.add(name, value);
	}
	
	public String entityName(int value) {
		return map.name(value);
	}
	
	public void escape(Writer writer, String str) throws IOException {
		int len = str.length();
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			String entityName = this.entityName(c);
			if (entityName == null) {
				writer.write(c);
			} else {
				writer.write('&');
				writer.write(entityName);
				writer.write(';');
			}
		}
	}
	
	public String escape(String str) {
		if (str == null) {
			return null;
		}
		try {
			StringWriter writer = new StringWriter((int) (str.length() * 1.5));
			escape(writer, str);
			return writer.toString();
		} catch (IOException ioe) {
			//should be impossible
			throw new UnhandledException(ioe);
		}
	}
	
	static interface EntityMap {
		/**
		 * <p>
		 * Add an entry to this entity map.
		 * </p>
		 *
		 * @param name the entity name
		 * @param value the entity value
		 */
		void add (String name, int value);
		
		/**
		 * <p>
		 * Returns the name of the entity identified by the specified value.
		 * </p>
		 *
		 * @param value the value to locate
		 * @return entity name associated with the specified value
		 */
		String name (int value);
		
		/**
		 * <p>
		 * Returns the value of the entity identified by the specified name.
		 * </p>
		 *
		 * @param name the name to locate
		 * @return entity value associated with the specified name
		 */
		int value (String name);
	}
	
	static class LookupEntityMap extends PrimitiveEntityMap {
		// TODO this class is not thread-safe
		private String[] lookupTable;
		
		private static final int LOOKUP_TABLE_SIZE = 256;
		
		/**
		 * {@inheritDoc}
		 */
		public String name(int value) {
			if (value < LOOKUP_TABLE_SIZE) {
				return lookupTable()[value];
			}
			return super.name(value);
		}
		
		/**
		 * <p>
		 * Returns the lookup table for this entity map. The lookup table is
		 * created if it has not been previously.
		 * </p>
		 *
		 * @return the lookup table
		 */
		private String[] lookupTable() {
			if (lookupTable == null) {
				createLookupTable();
			}
			return lookupTable;
		}
		
		/**
		 * <p>
		 * Creates an entity lookup table of LOOKUP_TABLE_SIZE elements,
		 * initialized with entity names.
		 * </p>
		 */
		private void createLookupTable() {
			lookupTable = new String[LOOKUP_TABLE_SIZE];
			for (int i = 0; i < LOOKUP_TABLE_SIZE; ++i) {
				lookupTable[i] = super.name(i);
			}
		}
	}
	
	static class PrimitiveEntityMap implements EntityMap {
		private final Map mapNameToValue = new HashMap();
		
		private final IntHashMap mapValueToName = new IntHashMap();
		
		/**
		 * {@inheritDoc}
		 */
		// TODO not thread-safe as there is a window between changing the two maps
		public void add(String name, int value) {
			mapNameToValue.put(name, Integer.valueOf(value));
			mapValueToName.put(value, name);
		}
		
		/**
		 * {@inheritDoc}
		 */
		public String name(int value) {
			return (String) mapValueToName.get(value);
		}
		
		/**
		 * {@inheritDoc}
		 */
		public int value(String name) {
			Object value = mapNameToValue.get(name);
			if (value == null) {
				return -1;
			}
			return ((Integer) value).intValue();
		}
	}
	
}
