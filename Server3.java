import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server3 implements MainServer{
	// A server dedicated to return Squared value of input number.
	final static int PORT = 8088;
	public static void main(String[] args) throws IOException{
		Server3 server = new Server3();
		server.runServer(PORT);
	}

	@Override
	public void runServer(int port) throws IOException{
		ServerSocket listener = new ServerSocket(port);
        try {
            while (true) {
                Socket socket = listener.accept();
                System.out.println("client connected");
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                String line = inputStream.readUTF();
                Double inputValue = 0.0;
                try {
                	inputValue = Double.parseDouble(line);
                }
                catch (NumberFormatException e) {
					e.printStackTrace();
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("Bad input, please provide a valid Numeric.");
				}
                Double squaredNo = dedicatedFunction(inputValue);
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(squaredNo);
                } 
                finally {
                    socket.close();
                }
            }
        }
        catch (IOException e) {
			e.printStackTrace();
		}
        finally {
            listener.close();
        }
		
	}

	@Override
	public double dedicatedFunction(double in) {
		// Implements square function
		return in + 20;
	}

}
