/**
 * CS 2110 Fall 2016 HW2
 * Part 2 - Coding with bases
 *
 * @author Alan Chiang
 *
 * Global rules for this file:
 * - You may not use more than 2 conditionals per method. Conditionals are
 *   if-statements, if-else statements, or ternary expressions. The else block
 *   associated with an if-statement does not count toward this sum.
 * - You may not use more than 2 looping constructs per method. Looping
 *   constructs include for loops, while loops and do-while loops.
 * - You may not use nested loops.
 * - You may not declare any file-level variables.
 * - You may not declare any objects, other than String in select methods.
 * - You may not use switch statements.
 * - You may not use the unsigned right shift operator (>>>)
 * - You may not write any helper methods, or call any other method from this or
 *   another file to implement any method.
 * - The only Java API methods you are allowed to invoke are:
 *     String.length()
 *     String.charAt()
 *     String.equals()
 * - You may not invoke the above methods from string literals.
 *     Example: "12345".length()
 * - When concatenating numbers with Strings, you may only do so if the number
 *   is a single digit.
 *
 * Method-specific rules for this file:
 * - You may not use multiplication, division or modulus in any method, EXCEPT
 *   strdtoi.
 * - You may declare exactly one String variable each in itostrb, and itostrx.
 */
public class HW2Bases
{
    /**
	 * strbtoi - Binary String to int
	 *
	 * Convert a string containing ASCII characters (in binary) to an int.
	 * You do not need to handle negative numbers. The Strings we will pass in will be
	 * valid binary numbers, and able to fit in a 32-bit signed integer.
	 *
	 * Example: strbtoi("111"); // => 7
	 */
	public static int strbtoi(String binary)
	{
		int sum = 0;
		int shift = 0;
		for (int i = binary.length(); i > 0; i--) {
			int num = binary.charAt(i - 1) - 48;
			sum = sum + (num << shift);
			shift++;
		}
		return sum;
		//TODO implement this method
	}
	/**
	 * strdtoi - Decimal String to int
	 *
	 * Convert a string containing ASCII characters (in decimal) to an int.
	 * You do not need to handle negative numbers. The Strings we will pass in will be
	 * valid decimal numbers, and able to fit in a 32-bit signed integer.
	 *
	 * Example: strdtoi("123"); // => 123
	 */
	public static int strdtoi(String decimal)
	{
		int multiple = 1;
		int sum = 0;
		for (int i = decimal.length(); i > 0; i--) {
			sum = sum + (decimal.charAt(i - 1) - 48) * multiple;
			multiple = 10 * multiple;
		}
		return sum;
		//TODO implement this method
	}

	/**
	 * strxtoi - Hexadecimal String to int
	 *
	 * Convert a string containing ASCII characters (in hex) to an int.
	 * The input string will only contain numbers and uppercase letters A-F.
	 * You do not need to handle negative numbers. The Strings we will pass in will be
	 * valid hexadecimal numbers, and able to fit in a 32-bit signed integer.
	 *
	 * Example: strxtoi("A6"); // => 166
	 */
	public static int strxtoi(String hex)
	{
		int sum = 0;
		int shift = 0;
		int num = 0;
		for (int i = hex.length(); i > 0; i--) {
			char ascii = hex.charAt(i - 1);
			if (ascii > 47 && ascii < 58) {
				num = ascii - 48;
			} else {
				num = ascii - 55;
			}
			sum = sum + (num << shift);
			shift += 4;
		}
		return sum;
        //TODO implement this method
	}

	/**
	 * itostrb - int to Binary String
	 *
	 * Convert a int into a String containing ASCII characters (in binary).
	 * You do not need to handle negative numbers.
	 * The String returned should contain the minimum number of characters necessary to
	 * represent the number that was passed in.
	 *
	 * Example: itostrb(7); // => "111"
	 */
	public static String itostrb(int binary) //minimum number of char?
	{
		String convert = "";
		while (binary != 0) {
			if ((binary & 0x1) == 1) {
				convert = "1" + convert;
			} else {
				convert = "0" + convert;
			}
			binary = binary >> 1;
		}
		if (convert.equals("")) {
			convert = "0";
		}
		return convert;
		//TODO implement this method
	}

	/**
	 * itostrx - int to Hexadecimal String
	 *
	 * Convert a int into a String containing ASCII characters (in hexadecimal).
	 * The output string should only contain numbers and uppercase letters A-F.
	 * You do not need to handle negative numbers.
	 * The String returned should contain the minimum number of characters necessary to
	 * represent the number that was passed in.
	 *
	 * Example: itostrx(166); // => "A6"
	 */
	public static String itostrx(int hex)
	{
		String convert = "";
		while (hex != 0) {
			if ((hex & 0xF) <= 9) {
				convert = (hex & 0xF) + convert;
			} else {
				char ascii = (char) ((0xF & hex) + 55);
				convert = ascii + convert;
			}
			hex = hex >> 4;
		}
		if (convert.equals("")) {
			convert = "0";
		}
		return convert;
       //TODO implement this method
	}
}