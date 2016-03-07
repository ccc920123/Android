package com.cncyj.mostbrain.utli;

import java.util.StringTokenizer;
/**
 * ��������
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
	 * ������ʽ ��������ɨ�裬������numberջ���������operatorջ 
	 * +-�������ȼ�Ϊ1��
	 * ���������ȼ�Ϊ2��
	 * ����ջ������һ�����������������������
	 *  �ظ�ֱ����ǰ���������ջ��
	 *   ɨ������ʣ�µ���������������μ���
	 */
	public Integer process(String str) {
	int weightPlus = 0, topOp = 0, topNum = 0, flag = 1, weightTemp = 0;
	// weightPlusΪͬһ�����µĻ������ȼ���weightTemp��ʱ��¼���ȼ��ı仯
	// topOpΪweight[]��operator[]�ļ�������topNumΪnumber[]�ļ�����
	// flagΪ�������ļ�������1Ϊ������-1Ϊ����
	int weight[]; // ����operatorջ������������ȼ�����topOp����
	int number[]; // �������֣���topNum����
	char ch, ch_gai, operator[];// operator[]�������������topOp����
	String num;// ��¼���֣�str��+-����()sctgl!��^�ֶΣ�+-����()sctgl!��^�ַ�֮����ַ�����Ϊ����
	weight = new int[MAXLEN];
	number = new int[MAXLEN];
	operator = new char[MAXLEN];
	String expression = str;
	StringTokenizer expToken = new StringTokenizer(expression,
			"+-��");
	int i = 0;
	while (i < expression.length()) {
		ch = expression.charAt(i);
		// �ж�������
		if (i == 0) {
			if (ch == '-')
				flag = -1;
		} else if (expression.charAt(i - 1) == '(' && ch == '-')
			flag = -1;
		// ȡ�����֣�������������ת�Ƹ�����
		if (ch <= '9' && ch >= '0' || ch == '.' || ch == 'E') {
			num = expToken.nextToken();
			ch_gai = ch;
			// ȡ����������
			while (i < expression.length()
					&& (ch_gai <= '9' && ch_gai >= '0' || ch_gai == '.' || ch_gai == 'E')) {
				ch_gai = expression.charAt(i++);
			}
			// ��ָ���˻�֮ǰ��λ��
			if (i >= expression.length())
				i -= 1;
			else {
				i -= 2;
			}
			if (num.compareTo(".") == 0)
				number[topNum++] = 0;
			// ����������ת�Ƹ�����
			else {
				number[topNum++] = Integer.parseInt(num) * flag;
				flag = 1;
			}
		}
		// ��������������ȼ�
		if (ch == '(')
			weightPlus += 4;
		if (ch == ')')
			weightPlus -= 4;
		if (ch == '-' && flag == 1 || ch == '+' || ch == '��') {
			switch (ch) {
			// +-�����ȼ���ͣ�Ϊ1
			case '+':
			case '-':
				weightTemp = 1 + weightPlus;
				break;
			// x�µ����ȼ��Ըߣ�Ϊ2
			case '��':
				weightTemp = 2 + weightPlus;
				break;
			
			// �������ȼ�Ϊ4
			// case '^':
			// case '��':
			default:
				weightTemp = 4 + weightPlus;
				break;
			}
			// �����ǰ���ȼ����ڶ�ջ����Ԫ�أ���ֱ����ջ
			if (topOp == 0 || weight[topOp - 1] < weightTemp) {
				weight[topOp] = weightTemp;
				operator[topOp] = ch;
				topOp++;
				// ���򽫶�ջ����������ȡ����ֱ����ǰ��ջ��������������ȼ�С�ڵ�ǰ�����
			} else {
				while (topOp > 0 && weight[topOp - 1] >= weightTemp) {
					switch (operator[topOp - 1]) {
					// ȡ�������������ӦԪ�ؽ�������
					case '+':
						number[topNum - 2] += number[topNum - 1];
						break;
					case '-':
						number[topNum - 2] -= number[topNum - 1];
						break;
					case '��':
						number[topNum - 2] *= number[topNum - 1];
						break;
					
					}
					// ����ȡ��ջ����һ��Ԫ�ؽ����ж�
					topNum--;
					topOp--;
				}
				// ����������ջ
				weight[topOp] = weightTemp;
				operator[topOp] = ch;
				topOp++;
			}
		}
		i++;
	}
	// ����ȡ����ջ���������������
	while (topOp > 0) {
		// +-xֱ�ӽ�����ĺ���λ��ȡ������
		switch (operator[topOp - 1]) {
		case '+':
			number[topNum - 2] += number[topNum - 1];
			break;
		case '-':
			number[topNum - 2] -= number[topNum - 1];
			break;
		case '��':
			number[topNum - 2] *= number[topNum - 1];
			break;
		
		
		}
		// ȡ��ջ��һ��Ԫ�ؼ���
		topNum--;
		topOp--;
	}
	// ������ս��
	return number[0];
	
  }
}
	
