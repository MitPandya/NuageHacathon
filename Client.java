
import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Trivial client for the date server.
 */

public class Client {

	/**
	 * Runs the client as an application.  First it displays a dialog
	 * box asking for the IP address or hostname of a host running
	 * the date server, then connects to it and displays the date that
	 * it serves.
	 */
	public static void main(String[] args) throws IOException {
		/*Socket s = new Socket("localhost", 8086);
		DataOutput out = new DataOutputStream(s.getOutputStream());
		out.writeUTF("5.2");
		System.out.println("here");
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String answer = input.readLine();
		System.out.println(answer);
		System.exit(0);*/
		System.out.println(parseInputFile());
		
	}

	static double parseInputFile(){
		try{
			Scanner sc = new Scanner(new FileReader("input.txt"));
			int x = 0;
			String line = sc.nextLine();
			Pattern p = Pattern.compile("[0-9]+.[0-9]*|[0-9]*.[0-9]+|[0-9]+");
			Matcher m = p.matcher(line);
			while (m.find()) {
				x = Integer.parseInt(m.group().trim());
			};
			double ans = 0.0;
			while(sc.hasNext()){
				String function = sc.nextLine();
				ans = Double.parseDouble(parseFunction(x, function));
			}
			return ans;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	static String parseFunction(int x, String function){
		Stack ops = new Stack(20);
		Stack cat = new Stack(20);
		Stack results = new Stack(20);
		results.push(x);
		char[] fn = function.toCharArray();
		for (int i = 0; i < fn.length; i++) {
			if(fn[i] == '('){
				// DO nothing
			}
			else if (fn[i] == '+' || fn[i] == '*' || fn[i] == '-' || fn[i] == '/') {
				ops.push(fn[i]);
			}
			else if (fn[i] == 'A' || fn[i] == 'B' || fn[i] == 'C'
					|| fn[i] == 'D' || fn[i] == 'E'){
				cat.push(fn[i]);
			}
			else if (fn[i] == ')'){
				getResult((char) cat.pop(), (int) results.pop());
			}
		}
		return null;
	}
	static void doOperation(Stack ops, Stack cat, Stack results){
		String op = cat.pop().toString();
		if (op.equals("+")) {
			double d1 = getResult((char) ops.pop(),(double) results.pop());
			double d2 = getResult((char) ops.pop(),(double) results.pop());
			results.push(d1+d2);
		} else if (op.equals("*")) {
			double d1 = getResult((char) ops.pop(),(double) results.pop());
			double d2 = getResult((char) ops.pop(),(double) results.pop());
			results.push(d1*d2);
		}
	}
	static double getResult(char operation, double val){
		double res = 0.0;
		int port;
		switch (operation) {
		case 'A':
			port = 8086;
			res = getResultFromServer(val, port);
			break;
		case 'B':
			port = 8087;
			res = getResultFromServer(val, port);
			break;
		case 'C':
			port = 8088;
			res = getResultFromServer(val, port);
			break;
		case 'D':
			port = 8089;
			res = getResultFromServer(val, port);
			break;
		case 'E':
			port = 8090;
			res = getResultFromServer(val, port);
			break;

		default:
			break;
		}
		return res;
	}
	static double getResultFromServer(Double val, int port){
		try{
			Socket s = new Socket("localhost", port);
			DataOutput out = new DataOutputStream(s.getOutputStream());
			out.writeUTF(val.toString());
			BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String answer = input.readLine();
			return Double.parseDouble(answer);
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}