package muti_network;

import java.awt.Font;
import java.util.Scanner;

public class MultiClientThread extends Thread{
    private MultiClient mc;
    
    /**
     * @wbp.parser.entryPoint
     */
    public MultiClientThread(MultiClient mc){
        this.mc = mc;
    }
    
    
    /**
     * @wbp.parser.entryPoint
     */
    public void run(){
        String message = null;
        String[] receivedMsg = null;
        
        boolean isStop = false;
        while(!isStop){
            try{                                              ////////////////////////////////////////
                message = (String)mc.getOis().readObject(); //클라이언트에있는 메시지를읽음
                receivedMsg = message.split("#");           //split("#")메세지를 배열에 저장하여 리턴시켜줌

            }catch(Exception e){                              /////////////////////////////////////
                e.printStackTrace();
                isStop = true;
            }
            System.out.println(receivedMsg[0]+","+receivedMsg[1]);
            if(receivedMsg[1].equals("exit")){
                if(receivedMsg[0].equals(mc.getId())){
                    mc.exit();
                }else{                                         ////////////////////////////////////
                    mc.getJta().append(
                    receivedMsg[0] +"님이 종료 하셨습니다."+
                    System.getProperty("line.separator"));         //exit를 채팅창에 입력시 나가지고 메세지를보냄
                    mc.getJta().setCaretPosition(
                    mc.getJta().getDocument().getLength());
                }                                              ////////////////////////////////////////
            }
            else if(receivedMsg[1].equals("clear")){             ///////////////////////////////////////////  
            	mc.Clear();
            }else if(receivedMsg[0].equals(mc.getId())){
            	mc.getJta().append(
            			receivedMsg[0] +" : "+receivedMsg[1]+          // clear를 채팅창에 입력시 채팅창클리어
            			System.getProperty("line.separator"));         //System.getProperty()= 실행되는 곳의 정보를 얻어오거나
                        mc.getJta().setCaretPosition(                  //실행 위치에 있는 파일을 읽어드려야 할때 사용하면 편리함
                            mc.getJta().getDocument().getLength());  
                                                                    ///  // ///////////////////////////     //
            
 
            }else{               
                mc.getJta().append(                                   //클라이언트에 메세지를 보내줌
                receivedMsg[0] +" : "+receivedMsg[1]+
                System.getProperty("line.separator"));
                mc.getJta().setCaretPosition(
                    mc.getJta().getDocument().getLength());     
            }
        }
    }
} 