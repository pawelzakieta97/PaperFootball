package com.company;

import java.awt.*;

public abstract class GameObject {

    protected abstract void tick();
    protected abstract void render(Graphics g);
}
