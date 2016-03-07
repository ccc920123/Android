package com.cncyj.mostbrain.utli;

import java.util.StringTokenizer;
/**
 * 计算数据
 *Calc.java
 * @author CXY
 * @Description
 * @Create Time 2015-12-9
 */
public class Calc {
	public Calc() {

	}

	final int MAXLEN = 500;

	/*
	 * 计算表达式 从左向右扫描，数字入number栈，运算符入operator栈 
	 * +-基本优先级为1，
	 * ×基本优先级为2，
	 * 低于栈顶弹出一个运算符与两个数进行运算
	 *  重复直到当前运算符大于栈顶
	 *   扫描完后对剩下的运算符与数字依次计算
	 */
	public Integer process(String str) {
	int weightPlus = 0, topOp = 0, topNum = 0, flag = 1, weightTemp = 0;
	// weightPlus为同一（）下的基本优先级，weightTemp临时记录优先级的变化
	// topOp为weight[]，operator[]的计数器；topNum为number[]的计数器
	// flag为正负数的计数器，1为正数，-1为负数
	int weight[]; // 保存operator栈中运算符的优先级，以topOp计数
	int number[]; // 保存数字，以topNum计数
	char ch, ch_gai, operator[];// operator[]保存运算符，以topOp计数
	String num;// 记录数字，str以+-×÷()sctgl!√^分段，+-×÷()sctgl!√^字符之间的字符串即为数字
	weight = new int[MAXLEN];
	number = new int[MAXLEN];
	operator = new char[MAXLEN];
	String expression = str;
	StringTokenizer expToken = new StringTokenizer(expression,
			"+-×");
	int i = 0;
	while (i < expression.length()) {
		ch = expression.charAt(i);
		// 判断正负数
		if (i == 0) {
			if (ch == '-')
				flag = -1;
		} else if (expression.charAt(i - 1) == '(' && ch == '-')
			flag = -1;
		// 取得数字，并将正负符号转移给数字
		if (ch <= '9' && ch >= '0' || ch == '.' || ch == 'E') {
			num = expToken.nextToken();
			ch_gai = ch;
			// 取得整个数字
			while (i < expression.length()
					&& (ch_gai <= '9' && ch_gai >= '0' || ch_gai == '.' || ch_gai == 'E')) {
				ch_gai = expression.charAt(i++);
			}
			// 将指针退回之前的位置
			if (i >= expression.length())
				i -= 1;
			else {
				i -= 2;
			}
			if (num.compareTo(".") == 0)
				number[topNum++] = 0;
			// 将正负符号转移给数字
			else {
				number[topNum++] = Integer.parseInt(num) * flag;
				flag = 1;
			}
		}
		// 计算运算符的优先级
		if (ch == '(')
			weightPlus += 4;
		if (ch == ')')
			weightPlus -= 4;
		if (ch == '-' && flag == 1 || ch == '+' || ch == '×') {
			switch (ch) {
			// +-的优先级最低，为1
			case '+':
			case '-':
				weightTemp = 1 + weightPlus;
				break;
			// x÷的优先级稍高，为2
			case '×':
				weightTemp = 2 + weightPlus;
				break;
			
			// 其余优先级为4
			// case '^':
			// case '√':
			default:
				weightTemp = 4 + weightPlus;
				break;
			}
			// 如果当前优先级大于堆栈顶部元素，则直接入栈
			if (topOp == 0 || weight[topOp - 1] < weightTemp) {
				weight[topOp] = weightTemp;
				operator[topOp] = ch;
				topOp++;
				// 否则将堆栈中运算符逐个取出，直到当前堆栈顶部运算符的优先级小于当前运算符
			} else {
				while (topOp > 0 && weight[topOp - 1] >= weightTemp) {
					switch (operator[topOp - 1]) {
					// 取出数字数组的相应元素进行运算
					case '+':
						number[topNum - 2] += number[topNum - 1];
						break;
					case '-':
						number[topNum - 2] -= number[topNum - 1];
						break;
					case '×':
						number[topNum - 2] *= number[topNum - 1];
						break;
					
					}
					// 继续取堆栈的下一个元素进行判断
					topNum--;
					topOp--;
				}
				// 将运算符如堆栈
				weight[topOp] = weightTemp;
				operator[topOp] = ch;
				topOp++;
			}
		}
		i++;
	}
	// 依次取出堆栈的运算符进行运算
	while (topOp > 0) {
		// +-x直接将数组的后两位数取出运算
		switch (operator[topOp - 1]) {
		case '+':
			number[topNum - 2] += number[topNum - 1];
			break;
		case '-':
			number[topNum - 2] -= number[topNum - 1];
			break;
		case '×':
			number[topNum - 2] *= number[topNum - 1];
			break;
		
		
		}
		// 取堆栈下一个元素计算
		topNum--;
		topOp--;
	}
	// 输出最终结果
	return number[0];
	
  }
}
	
