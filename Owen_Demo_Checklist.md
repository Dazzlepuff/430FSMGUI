# Owen's demonstration and submission checklist

## Prepare and demonstrate

Use your MinnState Zoom account, start a meeting with screen sharing and audio,
and record to the cloud. Show your GitHub repository, then compile and run:

```powershell
cd WarehouseFSM
javac *.java
java UserInterface
```

Choose No at the saved-data prompt to start with a fresh warehouse. Explain:
“My new state is ClerkClientsState. I moved four client-record operations out
of the clerk menu. Client records enters this state, and Back returns to the
clerk menu using the transition matrix in Context.”

Follow the assignment's full warehouse sequence, with client-record actions
accessed through your new state:

1. Login as clerk. Open Client records; create C1 through C5 with addresses.
   Show all clients and their balances. Back, then Logout.
2. Login as manager. Create P1 through P5 with quantities 10, 20, 30, 40, 50
   and prices $1, $2, $3, $4, $5 respectively.
3. Become a clerk and Show products.
4. Become client C1. Add 5 each of P1, P3, P5 to the wishlist and display it.
5. Logout to clerk, become C2, add 7 each of P1, P2, P4, and display the wishlist.
6. Logout to clerk, then manager, then opening. Login as C3; add 6 each of
   P1, P2, P5 and display the wishlist. Logout.
7. Login as C2, place the order, then Logout. C2's balance should be $49.
8. Login as clerk, open Client records, and Show all clients. Back.
9. Become C3, place the order, then Logout to clerk. C3's balance should be
   $45 because only 3 units of P1 remain; the other 3 are waitlisted.
10. Open Client records; Show all clients and Show clients with outstanding
    balances. Record a $10 payment for C2. Show all clients again: C2 should
    owe $39 and C3 should owe $45. Back.
11. Become C1 and Logout to confirm return to the clerk menu. Logout again.

The assignment's example mentions a Wishlist ops state. Owen's agreed state
is client records, so wishlist actions remain on the client menu in this branch.
The sequence above demonstrates the same warehouse operations plus Owen's
new state and its payment and Back actions.

## Finish submission

- Review `Owen_Completion_Report.md` and update the verification section after
  completing the visible demonstration.
- The assignment calls for an individual fork of the private group repository,
  completed code on GitHub, and a secret gist link. The individual fork is
  https://github.com/Dazzlepuff/430FSMGUI and the implementation branch is
  `state-owen`. The secret gist still needs to be created.
- Submit the individual completion report and required code links in D2L.
- End the Zoom recording and meeting. Once the cloud recording is available,
  put its public link in a Word or text file and submit that file in D2L.
