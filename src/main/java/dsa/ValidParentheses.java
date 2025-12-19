package dsa;


import java.util.Map;
import java.util.Stack;

public class ValidParentheses {
    public static boolean hasValidParentheses(String str) {
        // edge cases
        if (str == null) return false;
        if (str.isEmpty()) return true;
        if (str.length() % 2 != 0) return false;

        Map<Character, Character> parenthesesMap = Map.of(
                ')', '(',
                '}', '{',
                ']', '['
        );

        Stack<Character> stack = new Stack<>();

        for (char c : str.toCharArray()) {
            // check if its a closing brace
            if (parenthesesMap.containsKey(c)) {
                // if stack is empty, there's a closing bracket without an opener -> Invalid
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if (top != parenthesesMap.get(c)) {
                    return false;
                }
            } else {
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(hasValidParentheses("({[]})")); // True
        System.out.println(hasValidParentheses("([)]"));   // False
        System.out.println(hasValidParentheses("{[}"));    // False
        System.out.println(hasValidParentheses(""));       // True
    }
}