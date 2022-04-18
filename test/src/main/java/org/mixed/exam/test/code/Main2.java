package org.mixed.exam.test.code;

import java.util.Scanner;
import java.util.Stack;

public class Main2{
    public static void main(String[] args) {
        new Main2().solve();
    }
    public void solve(){
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        System.out.println(isValid(s)?"true":"false");
    }
    public boolean isValid(String s) {
        Stack<Character> stack=new Stack<>();
        for(int i=0;i<s.length();i++)
        {
            if(isLeft(s.charAt(i)))
            {
                stack.push(s.charAt(i));
            }
            else if(isRight(s.charAt(i)))
            {
                if(stack.isEmpty())
                {
                    return false;
                }
                if(!Character.valueOf(getMatch(stack.pop())).equals(s.charAt(i)))
                {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
    private char getMatch(char c)
    {
        switch (c)
        {
            case '(':
                return ')';
            case '[':
                return ']';
            case '{':
                return '}';
        }
        return ' ';
    }
    private boolean isLeft(char c)
    {
        return (c=='('||c=='['||c=='{');
    }
    private boolean isRight(char c)
    {
        return (c==')'||c==']'||c=='}');
    }
}