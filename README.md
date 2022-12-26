# ContactBook (MVVM, DaggerHilt, Room, Firebase)
Work Contact List

IDE --> Android Studio Bumblebee | 2021.1.1 Patch 2

Emulator Recommeded --> Pixel 4 XL API 30

*Use the emulator keyboard to avoid crashes.

Architecture --> Clean Architecture

Architecture Pattern --> MVVM

Injecting Dependencies --> Dagger Hilt

Database --> Room

Firebase --> Firebase Authentication, FireStore

///////////////////////////////////////////////////////////////


Simple application.

The user interacts with a list of work contacts.

The user can add, update and delete contacts.

Also the user can perform searches filtering by name and delete all contacts from the list.

The work contact list is displayed by a RecyclerView.

Each contact in the list is represented by a CardView.

To add a new contact click on the bottom right button with the + sign.

By clicking on a contact, you will be redirected to a menu, where you can update the contact information or delete it.

In order to access the application you must first register and then log in.

If you do not log out, the application keeps you logged in.

Login and registration are implemented by Firebase.

Login with Google and Facebook not implemented

