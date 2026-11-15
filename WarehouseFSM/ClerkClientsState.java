/** Client-record operations moved from the clerk menu. */
public final class ClerkClientsState implements State {
    public static final int ID = 4;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "ClerkClientsState";
    }

    @Override
    public void run(Context context) {
        context.showScreen("ClerkClientsState: client records",
                context.button("Add a client", context::addClient),
                context.button("Show all clients", context::showClients),
                context.button("Show clients with outstanding balances",
                        context::showOutstandingBalances),
                context.button("Record a client payment", context::receivePayment),
                context.button("Back", () -> context.transition(Context.BACK)));
    }
}
