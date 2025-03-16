package org.gic.ui.pages;

import org.gic.enums.NavigationRoutes;
import org.gic.ui.components.InputOptionReader;
import org.gic.ui.components.MenuOptions;
import org.gic.ui.navigator.Navigator;

//Page does not include any business logic. Just showing UI and navigating
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
