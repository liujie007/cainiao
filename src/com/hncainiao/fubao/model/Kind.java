package com.hncainiao.fubao.model;

/**
 * @author zhaojing
 * @version 2010年8月7日 下午2:30:08
 * 
 */
public class Kind {
	
	
public Kind(){
	
}

	public Kind(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	private String id;

	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Kind [id=" + id + ", name=" + name + "]";
	}
}
