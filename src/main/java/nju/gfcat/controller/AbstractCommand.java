package nju.gfcat.controller;

public abstract interface AbstractCommand {
    public abstract void execute();
    public abstract void undo();
} 