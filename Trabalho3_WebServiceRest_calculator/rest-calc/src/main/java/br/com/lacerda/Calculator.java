package br.com.lacerda;

public class Calculator {

	private enum Operation {
		SUM, DIFF, MULT, DIV;

		public static Operation getEnum(String oper) {
			switch (oper.toUpperCase()) {
			case "SUM":
			case "+":
				return SUM;
			case "DIFF":
			case "-":
				return DIFF;
			case "MULT":
			case "*":
			case "x":
				return MULT;
			case "DIV":
			case "/":
				return DIV;
			default:
				return null;
			}
		}
	}

	private Operation operation;
	private double numA;
	private double numB;
	private double result;

	public Calculator(String operation, double numA, double numB) {
		this.operation = Operation.getEnum(operation);
		this.numA = numA;
		this.numB = numB;
		this.result = calculate();
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public double getNumA() {
		return numA;
	}

	public void setNumA(double numA) {
		this.numA = numA;
	}

	public double getNumB() {
		return numB;
	}

	public void setNumB(double numB) {
		this.numB = numB;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	private double calculate() {
		switch (this.operation) {
		case SUM:
			return this.numA + this.numB;
		case DIFF:
			return this.numA - this.numB;
		case MULT:
			return this.numA * this.numB;
		case DIV:
			return this.numA / this.numB;
		default:
			return Double.NEGATIVE_INFINITY;
		}
	}

}
