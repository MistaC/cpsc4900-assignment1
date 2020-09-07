package cpsc4550AssignmentOne;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class SimpleCalculator implements Calculable {
	static ArrayList<String> postOrder = new ArrayList<String>();
	
	private static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } 
		  catch(NumberFormatException e) {  
		    return false;  
		  }  
		}
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	 
	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	private String doTheMath(String exp) {
		try {
			Stack<Double> stack = new Stack<Double>();
			if(exp.equals("Invalid expression")) {
				return "Invalid expression";
			}
			for(String sym : postOrder) {
				if(isNumeric(sym)) {
					stack.push(Double.parseDouble(sym));
				}
				else {
					Double val1 = stack.pop();
					Double val2 = stack.pop();
					switch (sym) { 
			        case "+": stack.push(val2+val1); break;
			        case "-": stack.push(val2-val1); break;
			        case "*": stack.push(val2*val1); break;
			        case "/": stack.push(val2/val1); break;
			        }
				}
			}
			Double tmp = stack.pop();
			tmp = round(tmp,5);
//			return stack.pop().toString();
			postOrder.clear();
			stack.clear();
			return tmp.toString();
		}
		catch(Exception e) {
			return "Invalid expression";
		}
	}
	
	String[] operators = {"+","-","*","/"};
	private int prec(char ch) 
    { 
        switch (ch) { 
        case '+': return 1;
        case '-': return 1;
        case '*': return 2;
        case '/': return 2;
        }
        return -1; 
    }
	private String postfix(String exp) {
		Stack<Character> stack = new Stack<Character>(); //Will temporarily hold operands and operators
		String post = new String(); //Will hold the completed postfix string.
		char[] expression = exp.toCharArray(); //Convert argument to array for traversal.
		String holder = "0"; //Holds multidigit numbers read from infix expression string. MUST BE STRING. Char type won't append properly.
		int counter = 0; //Tracks position in expression array.
		
		for(char sym : expression) {
			try {
				if(Character.isDigit(sym)) { //Checks if current symbol is an operand.
					if (counter+1 < expression.length && holder == "0") { //Next index exists and no multidigit has been made.
						if(Character.isDigit(expression[counter+1])) { //Start multidigit
							holder = Character.toString(sym);
						}
						else if(expression[counter+1] == '.') { //If next is decimal, start multidigit, decimal will continue elsewhere.
							holder = Character.toString(sym);
						}
						else {//If next symbol is not a digit, add the operand symbol to post string and list.
							post += Character.toString(sym)+" ";
							postOrder.add(Character.toString(sym));
						}
					}
					else if (counter+1 < expression.length && holder != "0") {//Check if next symbol exists and if a multidigit is being made.
						if(Character.isDigit(expression[counter+1])) {//If next symbol is a digit, add to existing multidigit.
							holder += Character.toString(sym); //Add to current holder. Extends the multidigit number.
						}
						else if(expression[counter+1] == '.') { //If next is decimal, start multidigit, decimal will continue elsewhere.
							holder += Character.toString(sym);
						}
						else {//If next symbol is not a digit, add current multidigit to post string and list, then reset holder to 0.
							holder += Character.toString(sym);
							post += holder+" ";
							postOrder.add(holder);
							holder = "0";
						}
					}
					else if(holder != "0" && counter+1 == expression.length) {//TODO THIS WAS CHANGED FROM COUNTER TO COUNTER+1
						holder += Character.toString(sym);
						post += holder+" ";
						postOrder.add(holder);
						holder = "0";
					}
					else {
						post += Character.toString(sym)+" ";
						postOrder.add(Character.toString(sym));
					}
					
				}
				else if(sym == '.') {
					if(counter+1 < expression.length) {//Next index exists
						if(!Character.isDigit(expression[counter+1])) {//Next symbol not digit
							return "Invalid expression";
						}
						else {//Next symbol is digit
							if(counter+2 < expression.length) {
								if(expression[counter+2] == '.') {
									return "Invalid expression";
								}
								else {
									holder += ".";
								}
							}
							else {
								holder += ".";
							}
						}
					}
					else {
						return "Invalid expression";
					}
				}
				else if(sym == '(') {
					stack.push(sym);
				}
				else if(sym == ')') 
	            { 
	                while(!stack.isEmpty() && stack.peek() != '(') {
	                	char tmp = stack.pop();
	                	post += tmp+" ";
	                	postOrder.add(Character.toString(tmp));
	                }
	                if(!stack.isEmpty() && stack.peek() != '(') {
	                	return "Invalid expression";
	                }
	                else {
	                	stack.pop();
	                }
	            }
				else { //If current infix symbol is an operator.
					if(counter == 0) {
						return "Invalid expression";
					}
					while (!stack.isEmpty() && prec(sym) <= prec(stack.peek())){ 
	                    if(stack.peek() == '(') {
	                    	return "Invalid expression";
	                    }
	                    char tmp = stack.pop();
	                	post += tmp+" ";
	                	postOrder.add(Character.toString(tmp));
					}
					//!Character.isLetter(sym) && 
					if(Arrays.stream(operators).anyMatch(Character.toString(sym)::equals)) {//TODO Make this a check across the accepted operators.
						stack.push(sym);
					}
					else {
						return "Invalid expression";
					}
				}
				counter++;
			}
			catch (Exception e) {
				return "Invalid expression";
			}
		}
		while (!stack.isEmpty()){ 
            if(stack.peek() == '(') 
                return "Invalid expression"; 
            char tmp = stack.pop();
        	post += tmp+" ";
        	postOrder.add(Character.toString(tmp)); 
         }
//		for(String e : postOrder) {
//			print(e);
//		}
		holder = "0";
		counter = 0;
		stack.clear();
		return post;
	}
	
	@Override
	public String calculate(String expression) {
		return doTheMath(postfix(expression));
	}
	
	private static void print(Object line) {
	    System.out.println(line);
	}

	

	public static void main(String[] args) {
		//Begin runtime of the calculator with instructions.
		//calc calls postfix and doTheMath to return answer.
		SimpleCalculator calc = new SimpleCalculator();
		print("Thank you for using SimpleCalculator. Your equation may include parentheses and the four basic operators: +, -, /, *. Spaces are not allowed.");
		print("Enter HELP to repeat instructions. Enter EXIT to close the calculator.");
		while(true) {
			Scanner in = new Scanner(System.in);
			System.out.print("Input: ");
			String input = in.nextLine();
			input = input.strip();
			if(input.equals("HELP")) {
				print("Thank you for using SimpleCalculator. Your equation may include spaces, parentheses, and the four basic operators: +, -, /, *.");
				print("Enter HELP to repeat instructions. Enter EXIT to close the calculator.");
			}
			else if(input.equals("EXIT")) {
				print("Goodbye");
				in.close();
				break;
			}
			else {
				print(calc.calculate(input));
				postOrder.clear();
			}
		}
	}

}
