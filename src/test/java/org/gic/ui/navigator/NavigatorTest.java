package org.gic.ui.navigator;

import org.gic.enums.NavigationRoutes;
import org.gic.model.Account;
import org.gic.ui.pages.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.math.BigDecimal;

//this test will perform better if I used dependency injection for navigator.
//but I don't see it as better option than using static
class NavigatorTest {

    private MockedStatic<MenuPage> menuMock;
    private MockedStatic<DefineInterestRulePage> defineInterestRulePageMock;
    private MockedStatic<InputTransactionDetailPage> inputTransactionPageMock;
    private MockedStatic<AccountStatementPage> accountStatementPageMockedStatic;
    private MockedStatic<PrintStatementPage> printStatementPageMockedStatic;
    private MockedStatic<ExitPage> exitPageMockedStatic;
    private MockedStatic<InterestRuleListPage> interestRuleListPageMockedStatic;

    @BeforeEach
    void setUp() {
        menuMock = Mockito.mockStatic(MenuPage.class);
        defineInterestRulePageMock = Mockito.mockStatic(DefineInterestRulePage.class);
        inputTransactionPageMock = Mockito.mockStatic(InputTransactionDetailPage.class);
        accountStatementPageMockedStatic = Mockito.mockStatic(AccountStatementPage.class);
        printStatementPageMockedStatic = Mockito.mockStatic(PrintStatementPage.class);
        exitPageMockedStatic = Mockito.mockStatic(ExitPage.class);
        interestRuleListPageMockedStatic = Mockito.mockStatic(InterestRuleListPage.class);
    }

    @AfterEach
    void tearDown() {
        menuMock.close();
        defineInterestRulePageMock.close();
        inputTransactionPageMock.close();
        accountStatementPageMockedStatic.close();
        printStatementPageMockedStatic.close();
        exitPageMockedStatic.close();
        interestRuleListPageMockedStatic.close();
    }

    @Test
    void testNavigatorWithMenuPage() {
        Navigator.goTo(NavigationRoutes.GO_TO_MENU_PAGE);
        menuMock.verify(MenuPage::showMenuPage);
        defineInterestRulePageMock.verifyNoInteractions();
        inputTransactionPageMock.verifyNoInteractions();
        accountStatementPageMockedStatic.verifyNoInteractions();
        printStatementPageMockedStatic.verifyNoInteractions();
        exitPageMockedStatic.verifyNoInteractions();
        interestRuleListPageMockedStatic.verifyNoInteractions();
    }

    @Test
    void testNavigatorWithDefineInterestRulePage() {
        Navigator.goTo(NavigationRoutes.GO_TO_DEFINE_INTEREST_RULES_PAGE);
        defineInterestRulePageMock.verify(DefineInterestRulePage::showDefineInterestRulePage);
        menuMock.verifyNoInteractions();
        inputTransactionPageMock.verifyNoInteractions();
        accountStatementPageMockedStatic.verifyNoInteractions();
        printStatementPageMockedStatic.verifyNoInteractions();
        exitPageMockedStatic.verifyNoInteractions();
        interestRuleListPageMockedStatic.verifyNoInteractions();
    }

    @Test
    void testNavigatorWithAccountStatementPage() {
        Navigator.goTo(NavigationRoutes.GO_TO_ACCOUNT_STATEMENT_PAGE);
        accountStatementPageMockedStatic.verify(() -> AccountStatementPage.showAccountStatementPage(null));
        menuMock.verifyNoInteractions();
        defineInterestRulePageMock.verifyNoInteractions();
        inputTransactionPageMock.verifyNoInteractions();
        printStatementPageMockedStatic.verifyNoInteractions();
        exitPageMockedStatic.verifyNoInteractions();
        interestRuleListPageMockedStatic.verifyNoInteractions();
    }

    @Test
    void testNavigatorWithAccountStatementPageWithAccount() {
        Account account = new Account("", new BigDecimal("0.00"), null);

        Navigator.goTo(NavigationRoutes.GO_TO_ACCOUNT_STATEMENT_PAGE, account);
        accountStatementPageMockedStatic.verify(() -> AccountStatementPage.showAccountStatementPage(account));
        menuMock.verifyNoInteractions();
        defineInterestRulePageMock.verifyNoInteractions();
        inputTransactionPageMock.verifyNoInteractions();
        printStatementPageMockedStatic.verifyNoInteractions();
        exitPageMockedStatic.verifyNoInteractions();
        interestRuleListPageMockedStatic.verifyNoInteractions();
    }

    @Test
    void testNavigatorWithInputTransactionPage() {
        Navigator.goTo(NavigationRoutes.GO_TO_TRANSACTION_INPUT_PAGE);
        inputTransactionPageMock.verify(InputTransactionDetailPage::showInputTransactionPage);
        menuMock.verifyNoInteractions();
        defineInterestRulePageMock.verifyNoInteractions();
        accountStatementPageMockedStatic.verifyNoInteractions();
        printStatementPageMockedStatic.verifyNoInteractions();
        exitPageMockedStatic.verifyNoInteractions();
        interestRuleListPageMockedStatic.verifyNoInteractions();
    }

    @Test
    void testNavigatorWithInvalid() {
        Navigator.goTo(null);
        menuMock.verify(MenuPage::showMenuPage);
        defineInterestRulePageMock.verifyNoInteractions();
        accountStatementPageMockedStatic.verifyNoInteractions();
        inputTransactionPageMock.verifyNoInteractions();
        printStatementPageMockedStatic.verifyNoInteractions();
        exitPageMockedStatic.verifyNoInteractions();
        interestRuleListPageMockedStatic.verifyNoInteractions();
    }

    @Test
    void testNavigatorWithTestRoute() {
        Navigator.goTo(NavigationRoutes.TEST_ROUTE);
        menuMock.verify(MenuPage::showMenuPage);
        defineInterestRulePageMock.verifyNoInteractions();
        accountStatementPageMockedStatic.verifyNoInteractions();
        inputTransactionPageMock.verifyNoInteractions();
        printStatementPageMockedStatic.verifyNoInteractions();
        exitPageMockedStatic.verifyNoInteractions();
        interestRuleListPageMockedStatic.verifyNoInteractions();
    }

    @Test
    void testNavigatorWithExitRoute() {
        Navigator.goTo(NavigationRoutes.QUIT);
        exitPageMockedStatic.verify(ExitPage::showExitPage);
        menuMock.verifyNoInteractions();
        defineInterestRulePageMock.verifyNoInteractions();
        accountStatementPageMockedStatic.verifyNoInteractions();
        inputTransactionPageMock.verifyNoInteractions();
        printStatementPageMockedStatic.verifyNoInteractions();
        interestRuleListPageMockedStatic.verifyNoInteractions();
    }

    @Test
    void testNavigatorWithPrintStatementRoute() {
        Navigator.goTo(NavigationRoutes.GO_TO_PRINTING_STATEMENT_PAGE);
        printStatementPageMockedStatic.verify(PrintStatementPage::showPrintStatementPage);
        exitPageMockedStatic.verifyNoInteractions();
        menuMock.verifyNoInteractions();
        defineInterestRulePageMock.verifyNoInteractions();
        accountStatementPageMockedStatic.verifyNoInteractions();
        inputTransactionPageMock.verifyNoInteractions();
        interestRuleListPageMockedStatic.verifyNoInteractions();
    }

    @Test
    void testNavigatorWithInterestRuleListRoute() {
        Navigator.goTo(NavigationRoutes.GO_TO_INTEREST_RULE_LIST_PAGE);
        interestRuleListPageMockedStatic.verify(InterestRuleListPage::showInterestRuleListPage);
        printStatementPageMockedStatic.verifyNoInteractions();
        exitPageMockedStatic.verifyNoInteractions();
        menuMock.verifyNoInteractions();
        defineInterestRulePageMock.verifyNoInteractions();
        accountStatementPageMockedStatic.verifyNoInteractions();
        inputTransactionPageMock.verifyNoInteractions();
    }
}