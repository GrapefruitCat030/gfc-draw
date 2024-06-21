package nju.gfcat.controller;

import java.util.ArrayList;
import java.util.List;

public class ThemeController {
    private List<ThemeObserver> observers = new ArrayList<>();
    private String currentTheme;

    public void addThemeObserver(ThemeObserver observer) {
        observers.add(observer);
    }

    public void removeThemeObserver(ThemeObserver observer) {
        observers.remove(observer);
    }

    public void setTheme(String theme) {
        this.currentTheme = theme;
        notifyObservers();
    }

    private void notifyObservers() {
        for (ThemeObserver observer : observers) {
            observer.updateTheme(currentTheme);
        }
    }
}