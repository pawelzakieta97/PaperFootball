package com.company;

import java.awt.*;
import java.util.Observable;

public abstract class GameObject extends Observable{

    protected abstract void render(Graphics g);
}
