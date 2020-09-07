package cpsc4550AssignmentOne;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleCalculatorTester {

	@Test
	public void calculatorTest() {
		SimpleCalculator junit = new SimpleCalculator();
		assertEquals("4.0",junit.calculate("2+2"));
		assertEquals("7.0",junit.calculate("(2+2)+3"));
		assertEquals("10.0",junit.calculate("2+2*4"));
		assertEquals("10.0",junit.calculate("5+5"));
		assertEquals("14.0",junit.calculate("5+((1+2)*4)-3"));
		assertEquals("48.0",junit.calculate("((5+7)*(6-2))"));
		assertEquals("18.0",junit.calculate("((4+2)+(3*(5-1)))"));
		assertEquals("-16.0",junit.calculate("4*(5-(7+2))"));
		assertEquals("2.0",junit.calculate("((3+4)*2)/7"));
		assertEquals("5.0",junit.calculate("(2+.5)*2"));
		assertEquals("4.5",junit.calculate("1.5*3"));
		assertEquals("4.0001",junit.calculate("2.00005*2"));
		assertEquals("29.19536",junit.calculate("14.59768*2"));
		assertEquals("Invalid expression",junit.calculate("+2"));
		assertEquals("Invalid expression",junit.calculate("2++2"));
		assertEquals("Invalid expression",junit.calculate("2+2+"));
		assertEquals("Invalid expression",junit.calculate("2^2"));
		assertEquals("Invalid expression",junit.calculate("2+"));
	}

}
