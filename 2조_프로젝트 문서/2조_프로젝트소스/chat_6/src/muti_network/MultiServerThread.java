package muti_network;

import java.net.*;
import java.io.*;

public class MultiServerThread implements Runnable {
	private Socket socket;
	private MultiServer ms;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public MultiServerThread(MultiServer ms) {
		this.ms = ms;
	}

	public synchronized void run() {
		boolean isStop = false;
		try {
			socket = ms.getSocket(); // MultiServer클래스와 소켓연결
			ois = new ObjectInputStream(socket.getInputStream()); //받는 스트림생성
			oos = new ObjectOutputStream(socket.getOutputStream());//주는 스트림생성
			String message = null;
			while (!isStop) {//true일때
				message = (String) ois.readObject();
				String[] str = message.split("#");
				
				String name = "list"+"#";
				
				for(int i=0; i<ms.getList().size(); i++){
					name += ms.getList().get(i)+"#";
				}
				
				if (str[1].equals("exit")) {
					broadCasting(message);     //broadCasting=서버에있는 모든 유저에게 메세지를보냄
					isStop = true;
				}else if(str[1].equals("")){
					broadCasting(name);}		

				else {
					broadCasting(message);
				}
			}		
			ms.getList().remove(this);         //정상종료시 아래메세지출력
			System.out.println(socket.getInetAddress() + "정상적으로 종료하셨습니다");
			System.out.println("list size : " + ms.getList().size());
		}catch (Exception e) {
			ms.getList().remove(this);          //비정상종료시 아래메세지출력
			System.out.println(socket.getInetAddress() + "비정상적으로 종료하셨습니다");
			System.out.println("list size : " + ms.getList().size());
		}
	}

	public void broadCasting(String message) throws IOException {
		for (MultiServerThread ct : ms.getList()) {
			ct.send(message);
		}
	}

	public void send(String message) throws IOException {
		oos.writeObject(message);
	}
}