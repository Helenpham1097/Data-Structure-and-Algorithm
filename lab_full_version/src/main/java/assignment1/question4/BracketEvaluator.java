package assignment1.question4;

import java.util.Stack;

public class BracketEvaluator {

    public boolean evaluate(String input) {

        Stack<Character> stack = new Stack<>();
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '{' || chars[i] == '[' || chars[i] == '<' || chars[i] == '(') {
                stack.push(chars[i]);
                continue;
            }

            boolean match;
            switch (chars[i]) {
                case '}':
                    match = checkIfLastElementIsExpected(stack, '{');
                    break;
                case ']':
                    match = checkIfLastElementIsExpected(stack, '[');
                    break;
                case '>':
                    match = checkIfLastElementIsExpected(stack, '<');
                    break;
                case ')':
                    match = checkIfLastElementIsExpected(stack, '(');
                    break;
                default:
                    match = true;
            }

            if (!match) {
                return false;
            }
        }
        if(stack.size()>0){
            throw new IllegalArgumentException("Opening braces still left on the stack. Lack of close braces");
        }

        return stack.empty();
    }

    private static boolean checkIfLastElementIsExpected(Stack<Character> stack, char expected) {
        if (stack.empty()) {
            throw new IllegalArgumentException("Closing brace encountered with no opening brace");
        }

        if(stack.pop().equals(expected)){
            return true;
        }else{
            throw new IllegalArgumentException("Pair of brace doesn't match");
        }
    }

    public static void main(String[] args) {
        BracketEvaluator evaluator = new BracketEvaluator();

        System.out.println("Evaluate (149)");
        System.out.println(evaluator.evaluate("(149)"));
        System.out.println();

        System.out.println("Evaluate (149}");
        try {
            evaluator.evaluate("(149}");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Evaluate {((2 x 5)+(3*-2 + 5))}");
        System.out.println(evaluator.evaluate("{((2 x 5)+(3*-2 + 5))}"));
        System.out.println();

        System.out.println("Evaluate { (2 x 5)+(3*-2 + 5))}");
        try {
            evaluator.evaluate("{ (2 x 5)+(3*-2 + 5))}");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Evaluate List<List<E>> ");
        System.out.println(evaluator.evaluate("List<List<E>>"));
        System.out.println();

        System.out.println("Evaluate List<List<E>");
        try {
            evaluator.evaluate("List<List<E>");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Evaluate {(<<eeeek>>){}{...}(e(e)e){hello}}");
        System.out.println(evaluator.evaluate("{(<<eeeek>>){}{...}(e(e)e){hello}}"));
        System.out.println();

        System.out.println("Evaluate {(< eeeek>>){}{...} e(e)e){hello} ");
        try {
            evaluator.evaluate("{(< eeeek>>){}{...} e(e)e){hello} ");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println();

    }
}
