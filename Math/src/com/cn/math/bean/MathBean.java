package com.cn.math.bean;

public class MathBean {

	private int addend1;
	private int addend2;
	private String symbol;
	private String eql;
	private String setOther;
	public int getAddend1() {
		return addend1;
	}
	public void setAddend1(int addend1) {
		this.addend1 = addend1;
	}
	public int getAddend2() {
		return addend2;
	}
	public void setAddend2(int addend2) {
		this.addend2 = addend2;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getEql() {
		return eql;
	}
	public void setEql(String eql) {
		this.eql = eql;
	}
	public String getSetOther() {
		return setOther;
	}
	public void setSetOther(String setOther) {
		this.setOther = setOther;
	}
	
	public MathBean() {
		super();
	}
	public MathBean(int addend1, int addend2, String symbol, String eql,
			String setOther) {
		super();
		this.addend1 = addend1;
		this.addend2 = addend2;
		this.symbol = symbol;
		this.eql = eql;
		this.setOther = setOther;
	}
	
}
