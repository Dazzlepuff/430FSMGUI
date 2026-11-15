/** Menu and operations available to a warehouse clerk. */
public final class ClerkMenuState implements State {
    public static final int ID = 2;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "ClerkMenuState";
    }

    @Override
    public void run(Context context) {
        context.showScreen("ClerkMenuState: clerk menu",
                context.button("Client records", () -> context.transition(Context.CLIENT_RECORDS)),
                context.button("Show products", context::showProducts),
                context.button("Become a client", context::becomeClient),
                context.button("Save warehouse data", context::saveWarehouse),
                context.button("Logout", context::logout));
    }
}
