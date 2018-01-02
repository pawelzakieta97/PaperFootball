package com.company.Model;

import java.awt.*;
import java.util.Observable;

public abstract class GameObject extends Observable{

    protected abstract void render(Graphics g);
}
