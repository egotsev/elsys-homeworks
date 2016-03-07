package org.elsys.postfix;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Postfix {

	private Map<String, Operation> operations = new HashMap<String, Operation>();
	private Stack<Double> context = new Stack<Double>();

	public void addOperation(Operation op) {
		op.setContext(context);
		operations.put(op.getName(), op);
	}

	public Stack<Double> getContext() {
		return context;
	}

	public Operation getOperation(Plus plus) {
		return operations.get(plus);
	}

	public void interpret(Scanner scanner) {
		while (scanner.hasNext()) {
			String token = scanner.next();
			// check for operation <token>
			if (operations.containsKey(token)) {
				Operation op = operations.get(token);
				System.out.println("O: <" + token + ">");
				op.eval();
				System.out.println(context.lastElement());
			} else {
				// try to interpret <token> as double
				try {
					double value = Double.parseDouble(token);
					context.push(value);
					System.out.println("<"+context.size()+">");
				} catch (NumberFormatException ex) {
					// unknown operation
					// ....
					System.out.println("Unknown operation <" + token + ">");
				}
			}
		}

		scanner.close();
	}

	public static void main(String[] args) {
		Postfix postfix = new Postfix();
		postfix.addOperation(new Plus());
		postfix.addOperation(new Minus());
		postfix.addOperation(new Divide());
		postfix.addOperation(new Multiply());
		Scanner scanner = new Scanner(System.in);
		postfix.interpret(scanner);
	}
}
