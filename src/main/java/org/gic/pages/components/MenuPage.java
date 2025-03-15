package org.gic.pages.components;

import org.gic.enums.NavigationRoutes;
import org.gic.pages.elements.InputOptionReader;
import org.gic.pages.elements.MenuOptions;
import org.gic.pages.navigator.Navigator;

public class MenuPage {
    public static void showMenuPage() {
        try {
            MenuOptions.showOptions();
            NavigationRoutes nextRoute = InputOptionReader.showAndReceiveInput();
            Navigator.goTo(nextRoute);
        } catch (Exception e) {
            System.out.println("MenuPage : Something went wrong " + e.getMessage());
        }
    }
}
