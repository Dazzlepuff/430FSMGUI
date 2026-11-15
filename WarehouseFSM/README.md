# WarehouseFSM (GUI version)

Swing GUI for the Group 1 warehouse FSM, with Owen's individual client-records state:

- OpeningState
- ClientMenuState
- ClerkMenuState
- ManagerMenuState
- ClerkClientsState

From the clerk menu, choose **Client records** to add clients, list clients,
show outstanding balances, or record payments. **Back** returns to the clerk
menu. Becoming a client and logging out still returns to the clerk; a clerk
entered from the manager menu still returns to the manager on logout.

The backend classes (StoreSystem, Client, Product, Invoice, Money, and so on) are unchanged. Only the user interface layer was replaced.

## How it works

- `Context` holds the transition matrix, the current state, the role history, and the one window every state uses.
- Each state's `run` method puts its own buttons in the window.
- Every button is an `ActionButton`, which is its own `ActionListener` and holds the action it runs. No code checks which button fired an event.
- Output goes to the text area on the right. Input (names, IDs, quantities, amounts) is requested with dialog boxes.

## Run

```
cd WarehouseFSM
javac *.java
java UserInterface
```

A window opens and asks whether to use saved warehouse data. Choose No for a fresh warehouse.

## Run in a Codespace

A Codespace has no screen, so start the virtual desktop first (tightvncserver and novnc), then:

```
export DISPLAY=:1
cd WarehouseFSM
javac *.java
java UserInterface
```

Open port 6080 in the browser to see the window.

## Files

- `UserInterface.java` starts the program.
- `Context.java` is the FSM and the window.
- `State.java` defines the state interface. `OpeningState.java`, `ClientMenuState.java`, `ClerkMenuState.java`, `ManagerMenuState.java`, and `ClerkClientsState.java` implement the five states.
- `ActionButton.java` is the button class.
- Everything else is the unchanged backend.

## Automated navigation check (PowerShell)

Run from `WarehouseFSM`:

```powershell
New-Item -ItemType Directory -Force "$env:TEMP/owen-fsm-check" | Out-Null
javac -d "$env:TEMP/owen-fsm-check" *.java tests/ClerkClientsStateTest.java
java '-Djava.awt.headless=true' -cp "$env:TEMP/owen-fsm-check" ClerkClientsStateTest
```

This clicks Swing buttons on the event dispatch thread using scripted dialog
answers. It checks client creation, listings, payment, Back, and nested role
history without opening a window or reading/writing saved warehouse data.
