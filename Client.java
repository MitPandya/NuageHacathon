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
	 * Runs the client as an application. 
	 */
	public static void main(String[] args) throws IOException {
		
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
		Stack results = new Stack(20);
		Stack ops = new Stack(20);
		Stack expr = new Stack(20);
		char[] ch = function.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if(ch[i] == '('){
				
			}
			else if (ch[i] == '+' || ch[i] == '*'){
				expr.push(ch[i]);
			}
			else if(ch[i] == ')'){
				if(expr.isEmpty())
					continue;
				char o = (char) expr.pop();
				if(o == '+'){
					x = (Integer) results.pop() + (Integer) results.pop();
					results.push(x);
				}
				else if(o == '*'){
					x = (Integer) results.pop() * (Integer) results.pop();
					results.push(x);
				}
			}
			else if (ch[i] == 'A' || ch[i] == 'B' || ch[i] == 'C' || ch[i] == 'D' || ch[i] == 'E'){
				ops.push(ch[i]);
			}
			else if(ch[i] == 'x'){
				char c = (char) ops.pop();
				int res = getResult(c,x);
				results.push(res);
			}
		}
		return results.pop().toString();
	}
	static int getResult(char operation, double val){
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
		return (int) res;
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
