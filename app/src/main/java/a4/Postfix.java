package a4;

import java.util.ArrayDeque;
import java.util.Stack;

public class Postfix {
    /**
     * Computes the arithmetic operation through postfix  
     * @param tokens provies a queue of the strings provided in postfix format
     * @return s the result after performing the postfix operation 
     *Postfix Format:  Similar in other forms of operation; numbers to be performing operations on followed by the operator
     */
    public static Double postfix(ArrayDeque<Object> tokens) { //tokens = queue of the string passed by the tokenizer
        Stack<Double> stack = new Stack<>(); //creating a stack to hold all the tokens

        while (!tokens.isEmpty()){ //when the queue of input still has values left to be processed
            Object x = tokens.poll(); //we take them out one by one
            
            if (x instanceof Double){//if it is a number
                stack.push((Double) x);//we push it to the stack 
            }else if (x instanceof Character){ //if it is a character(anyth other than number); in this case Operators
                //not enough operands
                if (stack.size() < 2 ){ //while the stack has less than two numbers, then we cannpt perform any operation.
                    throw new IllegalArgumentException("Not enough operands");
                }

                //if the stack has  2 or more numbers
                double num2 = stack.pop(); //we pop the number on top of the stack as 2nd number (because it was stacked after the first one)
                double num1 = stack.pop(); //then we pop out the first number (which was below the second)
                double result; 

                if ((char)x == '+'){
                    result = num1 + num2;
                } else if ((char) x == '-'){
                    result = num1 - num2;
                } else if ((char) x == '*' || (char) x == 'x'){
                    result = num1 * num2;
                } else if ((char) x == '/'){
                     // not supported operand - 0
                    if (num1 == 0|| num2 == 0){
                        throw new ArithmeticException("Cannot perform division on 0");
                    }
                    result = num1/num2;
                } else if ((char) x == '^'){
                    result = Math.pow(num1, num2);
                }else{
                    throw new ArithmeticException("Unsupported Operator");//unsupported operator 
                }
                stack.push(result);
               
            } else {// if the token is anything other than number or character, then we throw an exception
                throw new IllegalArgumentException("Invalid token");
            }

        }

        if (stack.size()!= 1){ //making sure we only have one number in the stack before we publish the result of the operation
            throw new IllegalStateException("More/Less result number");
        }
        return stack.pop(); //popping out the final result
    }
}

