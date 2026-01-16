package io.core.ui;

import com.badlogic.gdx.Gdx;

import java.util.Stack;

public class UIDisplayManager
{
    private static final Stack<UIDisplay> stack = new Stack<>();

    public static void push(UIDisplay d) {
        stack.push(d);
        d.show();
        Gdx.input.setInputProcessor(d.stage);
    }

    public static void pop() {
        if (stack.isEmpty()) return;
        UIDisplay top = stack.pop();
        top.hide();

        if (!stack.isEmpty())
            Gdx.input.setInputProcessor(stack.peek().stage);
        else
            Gdx.input.setInputProcessor(null);
    }

    public static void tick() {
        if (!stack.isEmpty())
            stack.peek().tick();
    }

    public static void render() {
        for (UIDisplay d : stack)
            d.render();
    }

    public static boolean blocksGameInput() {
        return !stack.isEmpty() && stack.peek().blocksGameInput();
    }
}
