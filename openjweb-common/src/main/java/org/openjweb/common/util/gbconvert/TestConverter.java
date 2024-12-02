package org.openjweb.common.util.gbconvert;

/**
 * 说明：在SpringBoot中未测试，使用前需再测试下。
 */
public class TestConverter 
{
	public static void main(String[] args) throws Exception 
	{
		if (args.length < 2) 
		{
			System.out.println("Usage: MyTest [-gb | -big5] inputstring");
			System.exit(1);
			return;
		}

		boolean bIsGB = true;
		String inStr = "";
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("-gb")) {
				bIsGB = true;
			} else if (args[i].equalsIgnoreCase("-big5")) {
				bIsGB = false;
			} else {
				inStr = args[i];
			}
		}

		// 得到一个繁简体字符处理的实例
		GB2Big5 pTmp = GB2Big5.getInstance();

		String outStr = "";
		if (bIsGB) {
			// 如果需要把big5码的字符转化成gb2312的，就使用big52gb()方法。
			// 传入字符串参数，传出的也是字符串。
			outStr = pTmp.big52gb(inStr);
		} else {
			// 如果需要把gb2312码的字符转化成big5的，就使用gb2big5()方法。
			// 传入的是字符串参数，传出的是字节数组（因为有可能需要把big5码的内容
			// 写入文件，就必须用字节数组的方式写入，否则经过字节->字符串的转化之后
			// 再写入文件就变成乱码了）。如果需要直接显示出来，就new一个BIG5的字符
			// 串就行了。
			outStr = new String(pTmp.gb2big5(inStr), "BIG5");
		}

		System.out.println("String [" + inStr + "] converted into:\n[" + outStr
				+ "]");
	}
}
