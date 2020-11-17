package ua.kiev.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean islogin=false;
		User user=new User();
		try {
			System.out.println("Hello! You want to login or to register?");
			String to=scanner.nextLine();
		while (!islogin) {
			if (to.equals("/login")) {
				System.out.println("Enter your login: ");
				String login = scanner.nextLine();

				System.out.println("Enter you password: ");
				String pass = scanner.nextLine();

				user.setLogin(login);
				user.setPass(pass);
				user.loginAccount();
				islogin=user.getStatusLogin();

			} else if (to.equals("/register")) {
				System.out.println("Enter your login: ");
				String login = scanner.nextLine();
				System.out.println("Enter you password: ");
				String pass = scanner.nextLine();
				user.setLogin(login);
				user.setPass(pass);
				user.createAccount();
				islogin =user.getStatusLogin();
			}
		}


				Thread th = new Thread(new GetThread());
				th.setDaemon(true);
				th.start();

				System.out.println("Enter your message: ");
				while (true) {
					String text = scanner.nextLine();
					if (text.isEmpty()) break;

					Message m = new Message(user.getLogin(), text);
					int res = m.send(Utils.getURL() + "/add");

					if (res != 200) { // 200 OK
						System.out.println("HTTP error occured: " + res);
						return;
					}
				}

			} catch(IOException ex){
				ex.printStackTrace();
			} finally{
				scanner.close();
			}

	}
}
