package com.hncainiao.fubao.model;

/**
 * @author zhaojing
 * @version 2010年8月7日 下午2:29:05
 * 
 */
public class Group {
	
	

public Group(){
	
}

	public Group(String name) {
		super();
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Group [name=" + name + "]";
	}
}
