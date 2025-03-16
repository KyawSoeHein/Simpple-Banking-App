package org.gic.ui.navigator;

import org.gic.enums.NavigationRoutes;
import org.gic.ui.pages.AccountStatementPage;
import org.gic.ui.pages.DefineInterestRulePage;
import org.gic.ui.pages.TransactionPage;
import org.gic.ui.pages.MenuPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

//this test will perform better if I used dependency injection for navigator.
//but I don't see it as better option than using static
class NavigatorTest {

    private MockedStatic<MenuPage> menuMock;
    private MockedStatic<DefineInterestRulePage> defineInterestRulePageMock;
    private MockedStatic<TransactionPage> inputTransactionPageMock;
    private MockedStatic<AccountStatementPage> accountStatementPageMockedStatic;

    @BeforeEach
    void setUp() {
        menuMock = Mockito.mockStatic(MenuPage.class);
        defineInterestRulePageMock = Mockito.mockStatic(DefineInterestRulePage.class);
        inputTransactionPageMock = Mockito.mockStatic(TransactionPage.class);
        accountStatementPageMockedStatic = Mockito.mockStatic(AccountStatementPage.class);
    }

    @AfterEach
    void tearDown() {
        menuMock.close();
        defineInterestRulePageMock.close();
        inputTransactionPageMock.close();
        accountStatementPageMockedStatic.close();
    }

    @Test
    void testNavigatorWithMenuPage() {
        Navigator.goTo(NavigationRoutes.GO_TO_MENU_PAGE);
        menuMock.verify(MenuPage::showMenuPage);
        defineInterestRulePageMock.verifyNoInteractions();
        inputTransactionPageMock.verifyNoInteractions();
        accountStatementPageMockedStatic.verifyNoInteractions();
    }

    @Test
    void testNavigatorWithDefineInterestRulePage() {
        Navigator.goTo(NavigationRoutes.GO_TO_DEFINE_INTEREST_RULES_PAGE);
        defineInterestRulePageMock.verify(DefineInterestRulePage::showDefineInterestRulePage);
        menuMock.verifyNoInteractions();
        inputTransactionPageMock.verifyNoInteractions();
        accountStatementPageMockedStatic.verifyNoInteractions();
    }

//    @Test
//    void testNavigatorWithAccountStatementPage() {
//        Navigator.goTo(NavigationRoutes.GO_TO_ACCOUNT_STATEMENT_PAGE);
//        accountStatementPageMockedStatic.verify(AccountStatementPage.showAccountStatementPage(null));
//        menuMock.verifyNoInteractions();
//        defineInterestRulePageMock.verifyNoInteractions();
//        inputTransactionPageMock.verifyNoInteractions();
//    }

    @Test
    void testNavigatorWithInputTransactionPage() {
        Navigator.goTo(NavigationRoutes.GO_TO_TRANSACTION_INPUT_PAGE);
        inputTransactionPageMock.verify(TransactionPage::showInputTransactionPage);
        menuMock.verifyNoInteractions();
        defineInterestRulePageMock.verifyNoInteractions();
        accountStatementPageMockedStatic.verifyNoInteractions();
    }

    @Test
    void testNavigatorWithInvalid() {
        Navigator.goTo(null);
        menuMock.verify(MenuPage::showMenuPage);
        defineInterestRulePageMock.verifyNoInteractions();
        accountStatementPageMockedStatic.verifyNoInteractions();
        inputTransactionPageMock.verifyNoInteractions();
    }

    @Test
    void testNavigatorWithTestRoute() {
        Navigator.goTo(NavigationRoutes.TEST_ROUTE);
        menuMock.verify(MenuPage::showMenuPage);
        defineInterestRulePageMock.verifyNoInteractions();
        accountStatementPageMockedStatic.verifyNoInteractions();
        inputTransactionPageMock.verifyNoInteractions();
    }
}