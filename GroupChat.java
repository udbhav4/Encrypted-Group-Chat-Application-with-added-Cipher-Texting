import java.net.*; 
import java.io.*; 
import java.util.*;

public class GroupChat extends ED
{ 

	private static final String TERMINATE = "leave";
    static String name; 
    static volatile boolean finished = false;
    static Scanner sc= new Scanner(System.in); 
    static File file; //= new File("Chat.txt");
    

    public static void main(String[] args) throws Exception
    { 
        if (args.length != 2) 
            System.out.println("Two arguments required: <multicast-host> <port-number>"); 

        else
        { 
            try
            { 
                InetAddress group = InetAddress.getByName(args[0]); 
                int port = Integer.parseInt(args[1]); 
                
                Scanner sc = new Scanner(System.in); 
                System.out.print("Enter your name: "); 
                name = sc.nextLine();
                file = new File("Chat" + name + ".txt");
                
                MulticastSocket socket = new MulticastSocket(port); 
 
                socket.setTimeToLive(0); 
                socket.joinGroup(group); 

                Thread t = new Thread(new ReadThread(socket,group,port)); 

                // Spawn a thread for reading messages. 
                t.start();  

                System.out.println("Start typing messages...\n"); 

                while(true)
                { 
                	String message;
                	message = sc.nextLine();
                    
                    if(message.equalsIgnoreCase(GroupChat.TERMINATE)) 
                    { 
                        finished = true; 
                        socket.leaveGroup(group); 
                        socket.close(); 
                        break;
                    } 

                    message = name + ": " + message;
                    message= Baconian.cipher(message);
                    byte[] cipherText= encrypt(message);
                    //System.out.println(Baconian.cipher(message));
                    byte[]buffer= message.getBytes();
                    DatagramPacket datagram = new DatagramPacket(buffer,buffer.length,group,port); 
                    socket.send(datagram);
                } 
            } 

            catch(SocketException se) 
            { 
                System.out.println("Error creating socket"); 
                se.printStackTrace(); 
            } 

            catch(IOException ie) 
            { 
                System.out.println("Error reading/writing from/to socket"); 
                ie.printStackTrace(); 
            } 
        } 
    } 
} 

class ReadThread extends ED implements Runnable 
{ 
    private MulticastSocket socket; 
    private InetAddress group; 
    private int port; 
    private static final int MAX_LEN = 1000; 

    ReadThread(MulticastSocket socket,InetAddress group,int port) 
    { 
        this.socket = socket; 
        this.group = group; 
        this.port = port; 
    } 

    @Override
    public void run()
    { 
        while(!GroupChat.finished) 
        { 
        	byte[]cipherTextArray = new byte[ReadThread.MAX_LEN]; 
            DatagramPacket datagram = new DatagramPacket(cipherTextArray,cipherTextArray.length,group,port); 
            String message;

            try
            {
                socket.receive(datagram);
                //message = decrypt(cipherTextArray);
                message= new String(cipherTextArray, 0, datagram.getLength(), "UTF-8");
                /*byte[]cipher= message.getBytes();
                message= decrypt(cipher);*/
                
                String message1= Baconian.decipher(message);
                
                if(!message1.startsWith(GroupChat.name.toUpperCase()))
                {
                    System.out.println("\n" + message + "\n");
                
                    message= message1;
                
                    FileWriter fw= new FileWriter(GroupChat.file, true);
                    for(int i=0; i<message.length(); ++i)
                    	fw.write(message.charAt(i));
                    fw.write("\n");
                    fw.close();
                }
            } 

            catch(IOException e) 
            { 
                System.out.println("Socket closed!"); 
            }
            
            catch (Exception e)
            {
				e.printStackTrace();
			} 
        } 
    } 
}