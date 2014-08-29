/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Achtern (Christian Gärtner & Contributors)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.achtern.AchternEngine.lwjgl.input;

import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.MouseButton;
import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.math.Vector2f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.HashMap;
import java.util.Map;

public class LWJGLInput implements InputAdapter {

    public static final int NUM_KEYCODES = 256;
    public static final int NUM_MOUSEBUTTONS = 5;

    public static final int NONE            = 0x00;
    public static final int ESCAPE          = 0x01;
    public static final int N_1             = 0x02;
    public static final int N_2             = 0x03;
    public static final int N_3             = 0x04;
    public static final int N_4             = 0x05;
    public static final int N_5             = 0x06;
    public static final int N_6             = 0x07;
    public static final int N_7             = 0x08;
    public static final int N_8             = 0x09;
    public static final int N_9             = 0x0A;
    public static final int N_0             = 0x0B;
    public static final int MINUS           = 0x0C; /* - on main keyboard */
    public static final int EQUALS          = 0x0D;
    public static final int BACK            = 0x0E; /* backspace */
    public static final int TAB             = 0x0F;
    public static final int Q               = 0x10;
    public static final int W               = 0x11;
    public static final int E               = 0x12;
    public static final int R               = 0x13;
    public static final int T               = 0x14;
    public static final int Y               = 0x15;
    public static final int U               = 0x16;
    public static final int I               = 0x17;
    public static final int O               = 0x18;
    public static final int P               = 0x19;
    public static final int LBRACKET        = 0x1A;
    public static final int RBRACKET        = 0x1B;
    public static final int RETURN          = 0x1C; /* Enter on main keyboard */
    public static final int LCONTROL        = 0x1D;
    public static final int A               = 0x1E;
    public static final int S               = 0x1F;
    public static final int D               = 0x20;
    public static final int F               = 0x21;
    public static final int G               = 0x22;
    public static final int H               = 0x23;
    public static final int J               = 0x24;
    public static final int K               = 0x25;
    public static final int L               = 0x26;
    public static final int SEMICOLON       = 0x27;
    public static final int APOSTROPHE      = 0x28;
    public static final int GRAVE           = 0x29; /* accent grave */
    public static final int LSHIFT          = 0x2A;
    public static final int BACKSLASH       = 0x2B;
    public static final int Z               = 0x2C;
    public static final int X               = 0x2D;
    public static final int C               = 0x2E;
    public static final int V               = 0x2F;
    public static final int B               = 0x30;
    public static final int N               = 0x31;
    public static final int M               = 0x32;
    public static final int COMMA           = 0x33;
    public static final int PERIOD          = 0x34; /* . on main keyboard */
    public static final int SLASH           = 0x35; /* / on main keyboard */
    public static final int RSHIFT          = 0x36;
    public static final int MULTIPLY        = 0x37; /* * on numeric keypad */
    public static final int LMENU           = 0x38; /* left Alt */
    public static final int LALT            = LMENU; /* left Alt */
    public static final int SPACE           = 0x39;
    public static final int CAPITAL         = 0x3A;
    public static final int F1              = 0x3B;
    public static final int F2              = 0x3C;
    public static final int F3              = 0x3D;
    public static final int F4              = 0x3E;
    public static final int F5              = 0x3F;
    public static final int F6              = 0x40;
    public static final int F7              = 0x41;
    public static final int F8              = 0x42;
    public static final int F9              = 0x43;
    public static final int F10             = 0x44;
    public static final int NUMLOCK         = 0x45;
    public static final int SCROLL          = 0x46; /* Scroll Lock */
    public static final int NUMPAD7         = 0x47;
    public static final int NUMPAD8         = 0x48;
    public static final int NUMPAD9         = 0x49;
    public static final int SUBTRACT        = 0x4A; /* - on numeric keypad */
    public static final int NUMPAD4         = 0x4B;
    public static final int NUMPAD5         = 0x4C;
    public static final int NUMPAD6         = 0x4D;
    public static final int ADD             = 0x4E; /* + on numeric keypad */
    public static final int NUMPAD1         = 0x4F;
    public static final int NUMPAD2         = 0x50;
    public static final int NUMPAD3         = 0x51;
    public static final int NUMPAD0         = 0x52;
    public static final int DECIMAL         = 0x53; /* . on numeric keypad */
    public static final int F11             = 0x57;
    public static final int F12             = 0x58;
    public static final int F13             = 0x64; /*                     (NEC PC98) */
    public static final int F14             = 0x65; /*                     (NEC PC98) */
    public static final int F15             = 0x66; /*                     (NEC PC98) */
    public static final int KANA            = 0x70; /* (Japanese keyboard)            */
    public static final int CONVERT         = 0x79; /* (Japanese keyboard)            */
    public static final int NOCONVERT       = 0x7B; /* (Japanese keyboard)            */
    public static final int YEN             = 0x7D; /* (Japanese keyboard)            */
    public static final int NUMPADEQUALS    = 0x8D; /* = on numeric keypad (NEC PC98) */
    public static final int CIRCUMFLEX      = 0x90; /* (Japanese keyboard)            */
    public static final int AT              = 0x91; /*                     (NEC PC98) */
    public static final int COLON           = 0x92; /*                     (NEC PC98) */
    public static final int UNDERLINE       = 0x93; /*                     (NEC PC98) */
    public static final int KANJI           = 0x94; /* (Japanese keyboard)            */
    public static final int STOP            = 0x95; /*                     (NEC PC98) */
    public static final int AX              = 0x96; /*                     (Japan AX) */
    public static final int UNLABELED       = 0x97; /*                        (J3100) */
    public static final int NUMPADENTER     = 0x9C; /* Enter on numeric keypad */
    public static final int RCONTROL        = 0x9D;
    public static final int NUMPADCOMMA     = 0xB3; /* , on numeric keypad (NEC PC98) */
    public static final int DIVIDE          = 0xB5; /* / on numeric keypad */
    public static final int SYSRQ           = 0xB7;
    public static final int RMENU           = 0xB8; /* right Alt */
    public static final int RALT            = RMENU; /* right Alt */
    public static final int PAUSE           = 0xC5; /* Pause */
    public static final int HOME            = 0xC7; /* Home on arrow keypad */
    public static final int UP              = 0xC8; /* UpArrow on arrow keypad */
    public static final int PRIOR           = 0xC9; /* PgUp on arrow keypad */
    public static final int LEFT            = 0xCB; /* LeftArrow on arrow keypad */
    public static final int RIGHT           = 0xCD; /* RightArrow on arrow keypad */
    public static final int END             = 0xCF; /* End on arrow keypad */
    public static final int DOWN            = 0xD0; /* DownArrow on arrow keypad */
    public static final int NEXT            = 0xD1; /* PgDn on arrow keypad */
    public static final int INSERT          = 0xD2; /* Insert on arrow keypad */
    public static final int DELETE          = 0xD3; /* Delete on arrow keypad */
    public static final int LMETA           = 0xDB; /* Left Windows/Option key */
    public static final int LWIN            = LMETA; /* Left Windows key */
    public static final int RMETA           = 0xDC; /* Right Windows/Option key */
    public static final int RWIN            = RMETA; /* Right Windows key */
    public static final int APPS            = 0xDD; /* AppMenu key */
    public static final int POWER           = 0xDE;
    public static final int SLEEP           = 0xDF;

    public static final Map<Key, Integer> keyIntegerMap = new HashMap<Key, Integer>(NUM_KEYCODES);
    public static final Map<Integer, Key> integerKeyMap = new HashMap<Integer, Key>(NUM_KEYCODES);

    public static final Map<MouseButton, Integer> buttonIntegerMap = new HashMap<MouseButton, Integer>(NUM_MOUSEBUTTONS);
    public static final Map<Integer, MouseButton> integerButtonMap = new HashMap<Integer, MouseButton>(NUM_MOUSEBUTTONS);

    static {

        buttonIntegerMap.put(MouseButton.LEFT, 0);
        buttonIntegerMap.put(MouseButton.RIGHT, 1);
        buttonIntegerMap.put(MouseButton.MIDDLE, 2);

        integerButtonMap.put(0, MouseButton.LEFT);
        integerButtonMap.put(1, MouseButton.RIGHT);
        integerButtonMap.put(2, MouseButton.MIDDLE);


        keyIntegerMap.put(Key.NONE, NONE);
        keyIntegerMap.put(Key.ESCAPE, ESCAPE);
        keyIntegerMap.put(Key.N_1, N_1);
        keyIntegerMap.put(Key.N_2, N_2);
        keyIntegerMap.put(Key.N_3, N_3);
        keyIntegerMap.put(Key.N_4, N_4);
        keyIntegerMap.put(Key.N_5, N_5);
        keyIntegerMap.put(Key.N_6, N_6);
        keyIntegerMap.put(Key.N_7, N_7);
        keyIntegerMap.put(Key.N_8, N_8);
        keyIntegerMap.put(Key.N_9, N_9);
        keyIntegerMap.put(Key.N_0, N_0);
        keyIntegerMap.put(Key.MINUS, MINUS);
        keyIntegerMap.put(Key.EQUALS, EQUALS);
        keyIntegerMap.put(Key.BACK, BACK);
        keyIntegerMap.put(Key.TAB, TAB);
        keyIntegerMap.put(Key.Q, Q);
        keyIntegerMap.put(Key.W, W);
        keyIntegerMap.put(Key.E, E);
        keyIntegerMap.put(Key.R, R);
        keyIntegerMap.put(Key.T, T);
        keyIntegerMap.put(Key.Y, Y);
        keyIntegerMap.put(Key.U, U);
        keyIntegerMap.put(Key.I, I);
        keyIntegerMap.put(Key.O, O);
        keyIntegerMap.put(Key.P, P);
        keyIntegerMap.put(Key.LBRACKET, LBRACKET);
        keyIntegerMap.put(Key.RBRACKET, RBRACKET);
        keyIntegerMap.put(Key.RETURN, RETURN);
        keyIntegerMap.put(Key.LCONTROL, LCONTROL);
        keyIntegerMap.put(Key.A, A);
        keyIntegerMap.put(Key.S, S);
        keyIntegerMap.put(Key.D, D);
        keyIntegerMap.put(Key.F, F);
        keyIntegerMap.put(Key.G, G);
        keyIntegerMap.put(Key.H, H);
        keyIntegerMap.put(Key.J, J);
        keyIntegerMap.put(Key.K, K);
        keyIntegerMap.put(Key.L, L);
        keyIntegerMap.put(Key.SEMICOLON, SEMICOLON);
        keyIntegerMap.put(Key.APOSTROPHE, APOSTROPHE);
        keyIntegerMap.put(Key.GRAVE, GRAVE);
        keyIntegerMap.put(Key.LSHIFT, LSHIFT);
        keyIntegerMap.put(Key.BACKSLASH, BACKSLASH);
        keyIntegerMap.put(Key.Z, Z);
        keyIntegerMap.put(Key.X, X);
        keyIntegerMap.put(Key.C, C);
        keyIntegerMap.put(Key.V, V);
        keyIntegerMap.put(Key.B, B);
        keyIntegerMap.put(Key.N, N);
        keyIntegerMap.put(Key.M, M);
        keyIntegerMap.put(Key.COMMA, COMMA);
        keyIntegerMap.put(Key.PERIOD, PERIOD);
        keyIntegerMap.put(Key.SLASH, SLASH);
        keyIntegerMap.put(Key.RSHIFT, RSHIFT);
        keyIntegerMap.put(Key.MULTIPLY, MULTIPLY);
        keyIntegerMap.put(Key.LMENU, LMENU);
        keyIntegerMap.put(Key.LALT, LALT);
        keyIntegerMap.put(Key.SPACE, SPACE);
        keyIntegerMap.put(Key.CAPITAL, CAPITAL);
        keyIntegerMap.put(Key.F1, F1);
        keyIntegerMap.put(Key.F2, F2);
        keyIntegerMap.put(Key.F3, F3);
        keyIntegerMap.put(Key.F4, F4);
        keyIntegerMap.put(Key.F5, F5);
        keyIntegerMap.put(Key.F6, F6);
        keyIntegerMap.put(Key.F7, F7);
        keyIntegerMap.put(Key.F8, F8);
        keyIntegerMap.put(Key.F9, F9);
        keyIntegerMap.put(Key.F10, F10);
        keyIntegerMap.put(Key.NUMLOCK, NUMLOCK);
        keyIntegerMap.put(Key.SCROLL, SCROLL);
        keyIntegerMap.put(Key.NUMPAD7, NUMPAD7);
        keyIntegerMap.put(Key.NUMPAD8, NUMPAD8);
        keyIntegerMap.put(Key.NUMPAD9, NUMPAD9);
        keyIntegerMap.put(Key.SUBTRACT, SUBTRACT);
        keyIntegerMap.put(Key.NUMPAD4, NUMPAD4);
        keyIntegerMap.put(Key.NUMPAD5, NUMPAD5);
        keyIntegerMap.put(Key.NUMPAD6, NUMPAD6);
        keyIntegerMap.put(Key.ADD, ADD);
        keyIntegerMap.put(Key.NUMPAD1, NUMPAD1);
        keyIntegerMap.put(Key.NUMPAD2, NUMPAD2);
        keyIntegerMap.put(Key.NUMPAD3, NUMPAD3);
        keyIntegerMap.put(Key.NUMPAD0, NUMPAD0);
        keyIntegerMap.put(Key.DECIMAL, DECIMAL);
        keyIntegerMap.put(Key.F11, F11);
        keyIntegerMap.put(Key.F12, F12);
        keyIntegerMap.put(Key.F13, F13);
        keyIntegerMap.put(Key.F14, F14);
        keyIntegerMap.put(Key.F15, F15);
        keyIntegerMap.put(Key.KANA, KANA);
        keyIntegerMap.put(Key.CONVERT, CONVERT);
        keyIntegerMap.put(Key.NOCONVERT, NOCONVERT);
        keyIntegerMap.put(Key.YEN, YEN);
        keyIntegerMap.put(Key.NUMPADEQUALS, NUMPADEQUALS);
        keyIntegerMap.put(Key.CIRCUMFLEX, CIRCUMFLEX);
        keyIntegerMap.put(Key.AT, AT);
        keyIntegerMap.put(Key.COLON, COLON);
        keyIntegerMap.put(Key.UNDERLINE, UNDERLINE);
        keyIntegerMap.put(Key.KANJI, KANJI);
        keyIntegerMap.put(Key.STOP, STOP);
        keyIntegerMap.put(Key.AX, AX);
        keyIntegerMap.put(Key.UNLABELED, UNLABELED);
        keyIntegerMap.put(Key.NUMPADENTER, NUMPADENTER);
        keyIntegerMap.put(Key.RCONTROL, RCONTROL);
        keyIntegerMap.put(Key.NUMPADCOMMA, NUMPADCOMMA);
        keyIntegerMap.put(Key.DIVIDE, DIVIDE);
        keyIntegerMap.put(Key.SYSRQ, SYSRQ);
        keyIntegerMap.put(Key.RMENU, RMENU);
        keyIntegerMap.put(Key.RALT, RALT);
        keyIntegerMap.put(Key.PAUSE, PAUSE);
        keyIntegerMap.put(Key.HOME, HOME);
        keyIntegerMap.put(Key.UP, UP);
        keyIntegerMap.put(Key.PRIOR, PRIOR);
        keyIntegerMap.put(Key.LEFT, LEFT);
        keyIntegerMap.put(Key.RIGHT, RIGHT);
        keyIntegerMap.put(Key.END, END);
        keyIntegerMap.put(Key.DOWN, DOWN);
        keyIntegerMap.put(Key.NEXT, NEXT);
        keyIntegerMap.put(Key.INSERT, INSERT);
        keyIntegerMap.put(Key.DELETE, DELETE);
        keyIntegerMap.put(Key.LMETA, LMETA);
        keyIntegerMap.put(Key.LWIN, LWIN);
        keyIntegerMap.put(Key.RMETA, RMETA);
        keyIntegerMap.put(Key.RWIN, RWIN);
        keyIntegerMap.put(Key.APPS, APPS);
        keyIntegerMap.put(Key.POWER, POWER);
        keyIntegerMap.put(Key.SLEEP, SLEEP);

        //---------------------------

        integerKeyMap.put(NONE, Key.NONE);
        integerKeyMap.put(ESCAPE, Key.ESCAPE);
        integerKeyMap.put(N_1, Key.N_1);
        integerKeyMap.put(N_2, Key.N_2);
        integerKeyMap.put(N_3, Key.N_3);
        integerKeyMap.put(N_4, Key.N_4);
        integerKeyMap.put(N_5, Key.N_5);
        integerKeyMap.put(N_6, Key.N_6);
        integerKeyMap.put(N_7, Key.N_7);
        integerKeyMap.put(N_8, Key.N_8);
        integerKeyMap.put(N_9, Key.N_9);
        integerKeyMap.put(N_0, Key.N_0);
        integerKeyMap.put(MINUS, Key.MINUS);
        integerKeyMap.put(EQUALS, Key.EQUALS);
        integerKeyMap.put(BACK, Key.BACK);
        integerKeyMap.put(TAB, Key.TAB);
        integerKeyMap.put(Q, Key.Q);
        integerKeyMap.put(W, Key.W);
        integerKeyMap.put(E, Key.E);
        integerKeyMap.put(R, Key.R);
        integerKeyMap.put(T, Key.T);
        integerKeyMap.put(Y, Key.Y);
        integerKeyMap.put(U, Key.U);
        integerKeyMap.put(I, Key.I);
        integerKeyMap.put(O, Key.O);
        integerKeyMap.put(P, Key.P);
        integerKeyMap.put(LBRACKET, Key.LBRACKET);
        integerKeyMap.put(RBRACKET, Key.RBRACKET);
        integerKeyMap.put(RETURN, Key.RETURN);
        integerKeyMap.put(LCONTROL, Key.LCONTROL);
        integerKeyMap.put(A, Key.A);
        integerKeyMap.put(S, Key.S);
        integerKeyMap.put(D, Key.D);
        integerKeyMap.put(F, Key.F);
        integerKeyMap.put(G, Key.G);
        integerKeyMap.put(H, Key.H);
        integerKeyMap.put(J, Key.J);
        integerKeyMap.put(K, Key.K);
        integerKeyMap.put(L, Key.L);
        integerKeyMap.put(SEMICOLON, Key.SEMICOLON);
        integerKeyMap.put(APOSTROPHE, Key.APOSTROPHE);
        integerKeyMap.put(GRAVE, Key.GRAVE);
        integerKeyMap.put(LSHIFT, Key.LSHIFT);
        integerKeyMap.put(BACKSLASH, Key.BACKSLASH);
        integerKeyMap.put(Z, Key.Z);
        integerKeyMap.put(X, Key.X);
        integerKeyMap.put(C, Key.C);
        integerKeyMap.put(V, Key.V);
        integerKeyMap.put(B, Key.B);
        integerKeyMap.put(N, Key.N);
        integerKeyMap.put(M, Key.M);
        integerKeyMap.put(COMMA, Key.COMMA);
        integerKeyMap.put(PERIOD, Key.PERIOD);
        integerKeyMap.put(SLASH, Key.SLASH);
        integerKeyMap.put(RSHIFT, Key.RSHIFT);
        integerKeyMap.put(MULTIPLY, Key.MULTIPLY);
        integerKeyMap.put(LMENU, Key.LMENU);
        integerKeyMap.put(LALT, Key.LALT);
        integerKeyMap.put(SPACE, Key.SPACE);
        integerKeyMap.put(CAPITAL, Key.CAPITAL);
        integerKeyMap.put(F1, Key.F1);
        integerKeyMap.put(F2, Key.F2);
        integerKeyMap.put(F3, Key.F3);
        integerKeyMap.put(F4, Key.F4);
        integerKeyMap.put(F5, Key.F5);
        integerKeyMap.put(F6, Key.F6);
        integerKeyMap.put(F7, Key.F7);
        integerKeyMap.put(F8, Key.F8);
        integerKeyMap.put(F9, Key.F9);
        integerKeyMap.put(F10, Key.F10);
        integerKeyMap.put(NUMLOCK, Key.NUMLOCK);
        integerKeyMap.put(SCROLL, Key.SCROLL);
        integerKeyMap.put(NUMPAD7, Key.NUMPAD7);
        integerKeyMap.put(NUMPAD8, Key.NUMPAD8);
        integerKeyMap.put(NUMPAD9, Key.NUMPAD9);
        integerKeyMap.put(SUBTRACT, Key.SUBTRACT);
        integerKeyMap.put(NUMPAD4, Key.NUMPAD4);
        integerKeyMap.put(NUMPAD5, Key.NUMPAD5);
        integerKeyMap.put(NUMPAD6, Key.NUMPAD6);
        integerKeyMap.put(ADD, Key.ADD);
        integerKeyMap.put(NUMPAD1, Key.NUMPAD1);
        integerKeyMap.put(NUMPAD2, Key.NUMPAD2);
        integerKeyMap.put(NUMPAD3, Key.NUMPAD3);
        integerKeyMap.put(NUMPAD0, Key.NUMPAD0);
        integerKeyMap.put(DECIMAL, Key.DECIMAL);
        integerKeyMap.put(F11, Key.F11);
        integerKeyMap.put(F12, Key.F12);
        integerKeyMap.put(F13, Key.F13);
        integerKeyMap.put(F14, Key.F14);
        integerKeyMap.put(F15, Key.F15);
        integerKeyMap.put(KANA, Key.KANA);
        integerKeyMap.put(CONVERT, Key.CONVERT);
        integerKeyMap.put(NOCONVERT, Key.NOCONVERT);
        integerKeyMap.put(YEN, Key.YEN);
        integerKeyMap.put(NUMPADEQUALS, Key.NUMPADEQUALS);
        integerKeyMap.put(CIRCUMFLEX, Key.CIRCUMFLEX);
        integerKeyMap.put(AT, Key.AT);
        integerKeyMap.put(COLON, Key.COLON);
        integerKeyMap.put(UNDERLINE, Key.UNDERLINE);
        integerKeyMap.put(KANJI, Key.KANJI);
        integerKeyMap.put(STOP, Key.STOP);
        integerKeyMap.put(AX, Key.AX);
        integerKeyMap.put(UNLABELED, Key.UNLABELED);
        integerKeyMap.put(NUMPADENTER, Key.NUMPADENTER);
        integerKeyMap.put(RCONTROL, Key.RCONTROL);
        integerKeyMap.put(NUMPADCOMMA, Key.NUMPADCOMMA);
        integerKeyMap.put(DIVIDE, Key.DIVIDE);
        integerKeyMap.put(SYSRQ, Key.SYSRQ);
        integerKeyMap.put(RMENU, Key.RMENU);
        integerKeyMap.put(RALT, Key.RALT);
        integerKeyMap.put(PAUSE, Key.PAUSE);
        integerKeyMap.put(HOME, Key.HOME);
        integerKeyMap.put(UP, Key.UP);
        integerKeyMap.put(PRIOR, Key.PRIOR);
        integerKeyMap.put(LEFT, Key.LEFT);
        integerKeyMap.put(RIGHT, Key.RIGHT);
        integerKeyMap.put(END, Key.END);
        integerKeyMap.put(DOWN, Key.DOWN);
        integerKeyMap.put(NEXT, Key.NEXT);
        integerKeyMap.put(INSERT, Key.INSERT);
        integerKeyMap.put(DELETE, Key.DELETE);
        integerKeyMap.put(LMETA, Key.LMETA);
        integerKeyMap.put(LWIN, Key.LWIN);
        integerKeyMap.put(RMETA, Key.RMETA);
        integerKeyMap.put(RWIN, Key.RWIN);
        integerKeyMap.put(APPS, Key.APPS);
        integerKeyMap.put(POWER, Key.POWER);
        integerKeyMap.put(SLEEP, Key.SLEEP);

    }


    private boolean[] lastKeys = new boolean[NUM_KEYCODES];
    private boolean[] lastMouse = new boolean[NUM_MOUSEBUTTONS];

    @Deprecated
    private static boolean[] lastMouseStatic = new boolean[NUM_MOUSEBUTTONS];

    public void update() {
        for (int i = 0; i < NUM_KEYCODES; i++) {
            lastKeys[i] = getKey(i);
        }

        for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
            lastMouseStatic[i] = lastMouse[i] = getMouse(i);
        }
    }

    @Override
    public int keysTotal() {
        return NUM_KEYCODES;
    }

    @Override
    public boolean getKey(Key key) {
        return getKey(toInt(key));
    }

    protected boolean getKey(int keyCode) {
        return Keyboard.isKeyDown(keyCode);
    }

    @Override
    public boolean getKeyDown(Key key) {
        return getKey(key) && !lastKeys[toInt(key)];
    }

    @Override
    public boolean getKeyUp(Key key) {
        return !getKey(key) && lastKeys[toInt(key)];
    }

    @Override
    public boolean getMouse(MouseButton mouseButton) {
        return getMouse(toInt(mouseButton));
    }

    protected boolean getMouse(int mouseButton) {
        return Mouse.isButtonDown(mouseButton);
    }

    @Override
    public boolean getMouseDown(MouseButton mouseButton) {
        return getMouse(mouseButton) && !lastMouse[toInt(mouseButton)];
    }

    @Override
    public boolean getMouseUp(MouseButton mouseButton) {
        return !getMouse(mouseButton) && lastMouse[toInt(mouseButton)];
    }

    @Override
    public Vector2f getMousePosition() {
        return new Vector2f(Mouse.getX(), Mouse.getY());
    }

    @Override
    public void setMousePosition(Vector2f position) {
        Mouse.setCursorPosition((int)position.getX(), (int)position.getY());
    }

    @Override
    public void setCursor(boolean enabled) {
        Mouse.setGrabbed(!enabled);
    }

    protected int toInt(Key key) {
        if (key == null) return 0;
        return keyIntegerMap.get(key);
    }

    protected Key toKey(int key) {
        Key k = integerKeyMap.get(key);

        if (k == null) k = Key.NONE;

        return k;
    }

    protected int toInt(MouseButton button) {
        if (button == null) return -1;

        return buttonIntegerMap.get(button);
    }

    protected MouseButton toButton(int key) {
        MouseButton b = integerButtonMap.get(key);

        if (b == null) b = MouseButton.INVALID;

        return b;
    }

}
