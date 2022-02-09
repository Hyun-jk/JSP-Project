package kr.util;

public class StringUtil {
	//HTML �±׸� ����ϸ鼭 �ٹٲ�
	public static String useBrHtml(String str) {
		if(str == null) return null;
		
		return str.replaceAll("\r\n", "<br>")
				  .replaceAll("\r", "<br>")
				  .replaceAll("\n", "<br>");
	}
	
	//HTML �±׸� ������� �����鼭 �ٹٲ�
	public static String useBrNoHtml(String str) {
		if(str == null) return null;
		
		return str.replaceAll("<", "&lt;")
				  .replaceAll(">", "&gt;")
				  .replaceAll("\r\n", "<br>")
				  .replaceAll("\r", "<br>")
				  .replaceAll("\n", "<br>");
	}
	
	//HTML �±׸� ������� ����
	public static String useNoHtml(String str) {
		if(str == null) return null;
		
		return str.replaceAll("<", "&lt;")
				  .replaceAll(">", "&gt;");
	}
	
	//Ư�� ���ڿ� ���Ŀ� ...���� ó��
	public static String shortWords(int length, String content) {
		if(content == null) return null;
		
		if(content.length() > length) {
			return content.substring(0,length) + " ...";
		}
		return content;
	}
	
}





