package muti_network;

import java.io.*;
import java.net.*;
import java.util.*;

public class MultiServer {
	private ArrayList<MultiServerThread> list;
	private Socket socket;

	public MultiServer() throws IOException {//생성자
		list = new ArrayList<MultiServerThread>();
		ServerSocket serverSocket = new ServerSocket(2111);//new ServerSocket(포트번호)에 자신의 주소로된 서버를 열음
		MultiServerThread mst = null;
		boolean isStop = false;
		while (!isStop) {
			System.out.println("Server ready...");//접속성공시 메시지 출력
			socket = serverSocket.accept();// 서버에 소켓을 연결 
			mst = new MultiServerThread(this);//MultiServerThread를 가져옴
			list.add(mst);//               list에 MultiServerThread를추가한다
			Thread t = new Thread(mst);//.java에있는 기본클래스 Thread를 가져옴
			t.start();
		}
	}
	


	public ArrayList<MultiServerThread> getList(){
		return list;
	}

	public Socket getSocket() {
		return socket;
	}

	public static void main(String arg[]) throws IOException {
		new MultiServer();
	}
}