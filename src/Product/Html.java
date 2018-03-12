package Product;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Html {

	// ����url�������ȡ��ҳ�ı�
	public Document getHtmlTextByUrl(String url) {
		Document doc = null;
		try {
			// doc = Jsoup.connect(url).timeout(5000000).get();
			int i = (int) (Math.random() * 1000); // ��һ�������ʱ����ֹ��վ����
			while (i != 0) {
				i--;
			}
			doc = Jsoup.connect(url).data("query", "Java").userAgent("Mozilla").cookie("auth", "token").timeout(300000)
					.post();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				doc = Jsoup.connect(url).timeout(5000000).get();
			} catch (IOException e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
			}
		}

		return doc;
	}

	// ���ݱ���·����ȡ��ҳ�ı�����������ھ�ͨ��url�������ȡ������

	public Document getHtmlTextByPath(String name, String url) {
		String path = "D:/Html/" + name + ".html";
		Document doc = null;
		File input = new File(path);
		String urlcat = url;
		try {
			doc = Jsoup.parse(input, "GBK");
			if (!doc.children().isEmpty()) {
				doc = null;
				System.out.println("�Ѿ�����");
			}
		} catch (IOException e) {
			System.out.println("�ļ�δ�ҵ������ڴ������ȡ.......");
			doc = this.getHtmlTextByUrl(url);
			// ���ұ��浽����
			this.Save_Html(url, name);
		}
		return doc;
	} // �˴�Ϊ������ҳ�ĺ���

	// ����ҳ�����ڱ��أ�ͨ��url,�ͱ�������֣�
	public void Save_Html(String url, String name) {
		try {
			name = name + ".html";
			// System.out.print(name);
			File dest = new File("D:/Html/" + name);// D:\Html
			// �����ֽ�������
			InputStream is;
			// �ֽ������
			FileOutputStream fos = new FileOutputStream(dest);

			URL temp = new URL(url);
			is = temp.openStream();

			// Ϊ�ֽ��������ӻ���
			BufferedInputStream bis = new BufferedInputStream(is);
			// Ϊ�ֽ�������ӻ���
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			int length;

			byte[] bytes = new byte[1024 * 20];
			while ((length = bis.read(bytes, 0, bytes.length)) != -1) {
				fos.write(bytes, 0, length);
			}

			bos.close();
			fos.close();
			bis.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ����Ԫ�����Ի�ȡĳ��Ԫ���ڵ�elements�б�
	public Elements getEleByClass(Document doc, String className) {
		Elements elements = null;
		elements = doc.select(className);// ��������ǻ�ȡ����html�ı�doc���͹���class����ע��<tr class="provincetr">
		return elements; // �˴����صľ������е�tr����
	}

	// ��ȡʡ ���� ���صȵ���Ϣ
//	public ArrayList getProvince(String name, String url, String type) {
//		ArrayList result = new ArrayList();
//		// "tr.provincetr"
//		String classtype = "tr." + type + "tr";
//		// �������ϻ�ȡ��ҳ
//		// Document doc = this.getHtmlTextByUrl(url);
//		// �ӱ��ػ�ȡ��ҳ,���û����������ȡ
//		Document doc2 = this.getHtmlTextByPath(name, url);
//		System.out.println(name);
//		if (doc2 != null) {
//			Elements es = this.getEleByClass(doc2, classtype); // tr�ļ���
//			for (Element e : es) // ����ѭ��ÿ��Ԫ�أ�Ҳ����һ��tr
//			{
//				if (e != null) {
//					for (Element ec : e.children()) // һ��tr����Ԫ��td��td�ڰ���a��ǩ
//					{
//						String[] prv = new String[4]; // ��ݵ���Ϣ�� ԭ����url����ǰurl�� ���ƣ������� ����url��Ҳ���Ǳ�����url�� ���ͣ�prv��ʡ
//						if (ec.children().first() != null) {
//							// ԭ����url
//							prv[0] = url; // ���ǲ���url
//							// �������
//							System.out.println(ec.children().first().ownText());
//							prv[1] = ec.children().first().ownText(); // a��ǩ�ı� ��:����
//							// ���url��ַ
//							// System.out.println(ec.children().first().attr("href"));
//							// http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/index.html
//							String ownurl = ec.children().first().attr("abs:href"); // ������url
//							// ��Ϊ����ӱ���ȡ�û������Ϊ����url�����Ա�����һ�δ������ϵ�url����֤url��Ϊ��
//							if (ownurl.length() < 10) {
//								connectOrcl c = new connectOrcl();
//								ownurl = c.selectOne(prv[1]); // �����ݿ���ȡ��������һ���������ݿ⺯������������ȡurl
//							}
//							prv[2] = ownurl; // �磺�����Լ���url
//							System.out.println(prv[2]);
//							// ����
//							prv[3] = type; // ���Ǹոմ������ͣ��������city ��county��
//							// ��������ݼ���list��
//							result.add(prv);
//						}
//					}
//				}
//			}
//		}
//		return result; // �������е�ʡ����Ϣ���ϣ������ݿ⣬�ֶ�����Ϊ�� baseurl name ownurl levelname��type�� updatetime
//	}

	// //��ȡ�������ƺ�IP
	// public static void main(String[] args) {
	// InetAddress ia=null;
	// try {
	// ia = ia.getLocalHost();
	// String localname=ia.getHostName();
	// String localip=ia.getHostAddress();
	// System.out.println("���������ǣ�"+ localname);
	// System.out.println("������ip�� ��"+localip);
	// } catch (UnknownHostException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	//
	// //��ȡ������Ļ��Ϣ
	// public static void getScreen()
	// {
	// Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	// int width = (int)screensize.getWidth();
	// int height = (int)screensize.getHeight();
	// System.out.println("������أ�"+width+"�ߵ����أ�"+height);
	// //��ȡ��Ļ��dpi
	// int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
	// System.out.println(dpi);
	// //����dpi�����أ����Լ�������ߴ�
	// System.out.println("��"+width/dpi+"�ߣ�"+height/dpi);
	// }

}