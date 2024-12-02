package org.openjweb.common.util.gbconvert;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

//import org.apache.log4j.Logger;

/**
 * 被TagLib所使用的类。用来进行整个jsp页面的繁简体转化.
 * 
 * <pre>
 *
 *
 *
 * 使用的时候需要进行如下配置:
 *
 * 1.在WEB-INF/目录下增加GB2Big5Wrapper.tld文件，内容如下:
 * <?xml version="1.0" encoding="ISO-8859-1"?>
 *   <!DOCTYPE taglib
 *       PUBLIC "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN"
 *              "http://java.sun.com/dtd/web-jsptaglibrary_1_2.dtd">
 *
 *   <taglib>
 *       <tlib-version>1.0</tlib-version>
 *       <jsp-version>1.2</jsp-version>
 *       <short-name>newmenbase</short-name>
 *       <tag>
 *         <name>GB2Big5Wrapper</name>
 *         <tag-class>zeal.util.GB2Big5Wrapper</tag-class>
 *         <attribute>
 *           <name>isbig5</name>
 *           <rtexprvalue>true</rtexprvalue>
 *           <type>boolean</type>
 *         </attribute>
 *       </tag>
 *   </taglib>
 * 2.在需要进行转化的JSP页面里面加上:
 * <%@ taglib uri="/WEB-INF/GB2Big5Wrapper.tld" prefix="newmenbase"%>
 * <newmenbase:GB2Big5Wrapper isbig5="true">
 * 任何你需要转化的东西
 * </newmenbase:GB2Big5Wrapper>
 *
 *
 *
 * </pre>
 *
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: NewmenBase
 * </p>
 * 
 * @author Zeal Li
 * @version 1.0
 */

public class GB2Big5Wrapper extends BodyTagSupport {
	// private static final Logger logger = Logger .getLogger(GB2Big5Wrapper.class);
	public static final boolean CONVERT_FLAG_YES = true;
	public static final boolean CONVERT_FLAG_NO = false;

	// 是否需要转换的标志，1 需要 0 不需要
	private boolean isbig5 = CONVERT_FLAG_YES;
	private String defaultEncoding = "zh-cn";// big5

	public GB2Big5Wrapper() {
	}

	public boolean getIsbig5() {
		return this.isbig5;
	}

	// 这个一定要
	public void setIsbig5(boolean flag) {
		this.isbig5 = flag;
		/*
		 * try{ this.isbig5 = Boolean.getBoolean(flag); if(this.isbig5 !=
		 * CONVERT_FLAG_YES){ this.isbig5 = CONVERT_FLAG_NO; } } catch(Exception e){
		 * this.isbig5 = CONVERT_FLAG_NO; }
		 */
	}

	private String convert(String bodyToConvert, String sourceEncoding, String destEncoding) {
		try {

			// return GB2Big5.getInstance().big52gb(bodyToConvert);
			// 下面是默认为简体，转换为繁体
			if (sourceEncoding.equals("zh-cn") && destEncoding.equals("big5")) {
				// logger.info("执行简体转繁体。。。");
				return new String(GB2Big5.getInstance().gb2big5(bodyToConvert), "BIG5");
			} else if (sourceEncoding.equals("big5") && destEncoding.equals("zh-cn")) {
				// logger.info("执行繁体转简体。。。");
				return GB2Big5.getInstance().big52gb(bodyToConvert);
			} else {
				// logger.info("没做转换...");
				return bodyToConvert;
			}

		} catch (Exception e) {
			System.out.println("GB2Big5Wrapper Convertion Failed!");
			e.printStackTrace();
			return bodyToConvert;
		}
	}

	public int doEndTag() throws JspException {
		try {
			String currentContent = this.getBodyContent().getString();
			String convertContent = null;
			if ("zh-cn".equals(this.getDefaultEncoding())) {
				//logger.info("默认为简体");
				if (isbig5 == CONVERT_FLAG_YES) // 如果当前是选择繁体
				{
					// logger.info("选择了繁体，执行简体转繁体");
					convertContent = this.convert(currentContent, "zh-cn", "big5"); // 执行简体转繁体

				} else // 如果当前选择是简体
				{
					// logger.info("选择了简体，不转换");
					convertContent = currentContent;
				}
			} else {
				// logger.info("默认为繁体");
				if (isbig5 == CONVERT_FLAG_YES) // 如果当前是选择繁体
				{
					// logger.info("选择了繁体，不转换");
					convertContent = currentContent;

				} else // 如果当前选择是简体
				{
					// logger.info("选择了简体，执行繁体转简体 ");
					convertContent = this.convert(currentContent, "big5", "zh-cn"); // 执行简体转繁体
				}
			}

			this.getBodyContent().clear();
			// 下面这个会引起页面错位
			// this.getBodyContent().println(
			// "<!--Convert By GB2Big5Wrapper to isbig5 [" + this.isbig5 +
			// "]-->");
			this.getBodyContent().print(convertContent);
			this.getBodyContent().writeOut(this.pageContext.getOut());
		} catch (Exception e) {
			throw new JspException(e);
		}
		return this.EVAL_PAGE;
	}

	public String getDefaultEncoding() {
		return defaultEncoding;
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}
}
