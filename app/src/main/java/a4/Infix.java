package a4;

import java.util.ArrayDeque;
import java.util.HashMap;

public class Infix {

    /**
     * Hashmap to map the operators and their precedence value according to Shunting Yard Algo
     */
    private final static HashMap<Character,Integer> precedenceMap = new HashMap<>();
    static{
        precedenceMap.put('^', 3);
        precedenceMap.put('*', 2);
        precedenceMap.put('/', 2);
        precedenceMap.put('+', 1);
        precedenceMap.put('-', 1);
        precedenceMap.put('(', -1); //so when the stack top is ( or ), the precedence value comparison doesn't throw error
        precedenceMap.put(')', -1);

    }

    /**
     * Converts the regular mathematical expression into postfix format and simplies parenthesis
     * @param tokens provies a queue of the strings provided in infix format (Regular format)
     * @return s the result of the operation 
     * Note that this class itself doesn't perform the calculation 
     */
    public static Double infixToPostfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Object> outputQueue = new ArrayDeque<>(); //queue that will be passed to the postfix class 
        ArrayDeque<Character> actAsStack = new ArrayDeque<>(); //stack that will store the operators or parenthesis until they are ordered in output queue

        while (!tokens.isEmpty()){
            Object x = tokens.poll(); //takes the items from the  queue one by one
            if (x instanceof Double){//if it is a number
                outputQueue.add(x); //we directly add it to the queue
            }else if ((char)x == '('){ //if it is left parenthesis 
                actAsStack.push((Character)x); //we push it to the stack
            } else if ((char)x == ')'){ //if it is right parenthesis
                try { //in order to prioritize the operation inside parenthesis, we add all the operators in the stack to the outputQueue that might be between ( and )
                    while ((char)actAsStack.peekFirst() != '('){
                        outputQueue.add(actAsStack.pop());
                    }
                } catch (Exception IllegalStateException) { //if we donot find a left parenthesis we throw an error
                    System.out.println("Mismatched Parenthesis");
                }
                actAsStack.pop();//we dissolve the ( parenthesis
            } else if (x instanceof Character){// in this case, character = operators
                Character queueOperator = (Character)x;    //temporary declaration
                if (actAsStack.size()!= 0){ //if the stack is not empty
                    if (queueOperator == '^'){ //for exponential, we get precedece using only less than instead of less than or equal (according to Shunting Yard algorithm)
                        while(actAsStack.peekFirst()!=null && precedenceMap.get(actAsStack.peekFirst()) > precedenceMap.get(queueOperator)){
                            outputQueue.add(actAsStack.pop());
                        }
                    }else{//normal precedence for other operators 
                        while(actAsStack.peekFirst()!=null && precedenceMap.get(actAsStack.peekFirst()) >= precedenceMap.get(queueOperator)){
                        outputQueue.add(actAsStack.pop());
                        }
                    }
                } 
                actAsStack.push(queueOperator); //finally push the operator to the queue
            }
        }
        
        while (!actAsStack.isEmpty()){ //when the token has emptied but the stack still has items left
            if((char)actAsStack.peek()==')'||(char) actAsStack.peek()=='('){ //if the left item is ( or ) then throw exception, because the other parenthesis hasn't been found which is not right (Mistmatched Parenthesis)
                throw new IllegalStateException("Parenthesis Mismatch");
            }else if(actAsStack.peek() instanceof Character){ //if it is remaining operators, we simply pop them out of the stack and add them to the queue. 
                outputQueue.add(actAsStack.pop());
            }
        }

    double result = Postfix.postfix(outputQueue); //pushing the converted postfix format to be calculated in postfix
    return result; //returning the final result
    }

}