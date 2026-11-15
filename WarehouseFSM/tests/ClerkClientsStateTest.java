import java.awt.Component;
import java.awt.Container;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/** Exercises real Swing button actions without opening windows or saving data. */
public final class ClerkClientsStateTest {
    private static final Deque<String> answers = new ArrayDeque<String>();

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(ClerkClientsStateTest::runChecks);
        System.out.println("PASS: client-record operations, Back, and clerk/manager role history");
    }

    private static void runChecks() {
        StoreSystem store = StoreSystem.instance();
        Context context = new Context(store);
        context.setPrompter(new Context.Prompter() {
            public String ask(String prompt) {
                if (answers.isEmpty()) throw new AssertionError("Unexpected prompt: " + prompt);
                return answers.removeFirst();
            }
            public void tell(String message) {
                throw new AssertionError("Unexpected dialog: " + message);
            }
        });
        context.begin();
        click(context, "Login as Clerk");
        check(find(context.getRootPanel(), "Add a client") == null, "Old operation still in clerk menu");
        click(context, "Client records");
        state(context, "ClerkClientsState");
        check(find(context.getRootPanel(), "Become a client") == null, "Role action leaked into records");
        answers.addAll(Arrays.asList("Owen Test", "123 Test Street"));
        click(context, "Add a client");
        Client client = store.getAllClients().get(0);
        check(client.getName().equals("Owen Test"), "Client was not added");
        click(context, "Show all clients");
        check(context.getOutputText().contains("Owen Test"), "Client missing from output");
        click(context, "Show clients with outstanding balances");
        check(context.getOutputText().contains("No outstanding balances."), "Empty balances missing");
        client.debit(new BigDecimal("25.00"));
        click(context, "Show clients with outstanding balances");
        check(context.getOutputText().contains("25.00"), "Outstanding balance missing");
        answers.addAll(Arrays.asList(String.valueOf(client.getClientID()), "10.00"));
        click(context, "Record a client payment");
        check(client.getBalance().compareTo(new BigDecimal("15.00")) == 0, "Payment not applied");
        state(context, "ClerkClientsState");
        click(context, "Back");
        state(context, "ClerkMenuState");
        becomeClientAndReturn(context, client);
        click(context, "Logout");
        state(context, "OpeningState");

        click(context, "Login as Manager");
        click(context, "Become a clerk");
        for (int i = 0; i < 3; i++) {
            click(context, "Client records");
            click(context, "Back");
            state(context, "ClerkMenuState");
        }
        becomeClientAndReturn(context, client);
        click(context, "Logout");
        state(context, "ManagerMenuState");
        click(context, "Logout");
        state(context, "OpeningState");
        check(answers.isEmpty(), "Unused answers");
    }

    private static void becomeClientAndReturn(Context context, Client client) {
        answers.add(String.valueOf(client.getClientID()));
        click(context, "Become a client");
        state(context, "ClientMenuState");
        check(context.getActiveClientId() == client.getClientID(), "Wrong active client");
        click(context, "Logout");
        state(context, "ClerkMenuState");
    }

    private static JButton find(Container parent, String label) {
        for (Component child : parent.getComponents()) {
            if (child instanceof JButton && ((JButton) child).getText().equals(label)) {
                return (JButton) child;
            }
            if (child instanceof Container) {
                JButton result = find((Container) child, label);
                if (result != null) return result;
            }
        }
        return null;
    }

    private static void click(Context context, String label) {
        JButton button = find(context.getRootPanel(), label);
        check(button != null, "Missing button: " + label);
        button.doClick();
    }

    private static void state(Context context, String expected) {
        check(context.getCurrentStateName().equals(expected),
                "Expected " + expected + ", got " + context.getCurrentStateName());
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
