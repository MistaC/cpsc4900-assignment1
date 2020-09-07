package cpsc4550AssignmentOne;

public interface Calculable {
	/**
     * @param expression An arithmetic expression that can only have one or more of the basic four operations: addition, subtraction, multiplication, and division (double division). Parentheses can be used in the expression. The type of the expression is String.
     * @return If the provided expression is valid, return the calculated value of the expression (up to five places after decimal point); If the expression is not valid, return "Invalid expression".
     */
    String calculate(String expression);
}
