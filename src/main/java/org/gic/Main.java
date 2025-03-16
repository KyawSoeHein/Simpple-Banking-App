package org.gic;

import org.gic.enums.NavigationRoutes;
import org.gic.ui.navigator.Navigator;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Navigator.goTo(NavigationRoutes.GO_TO_MENU_PAGE);
    }
}