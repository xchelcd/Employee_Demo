# Employee_Demo

## Credentials
you can use the next credentials:
- email: testdemo@gmail.com
- pass: 3fp39msi

## UI

### Login
It has 3 buttons. The first one is for register: you have to create a new account with your email and create a new password; 
the second one is for log in and naviigate to the menu view, but first you need write yours credentials (previously created); 
and the last one is just a button for navigate to the menu without credentials.\
for the first and second button you must write a email and password, it could be anything and anylenght

### Menu
Here we have 3 buttons, but first at all: in background fetch all data from firestore and sync with the local data.\
The first button is to see all employees registered, the second button is for register a new employee and the las button is the log out,
it clear the authData to go to the login view. If you are logged you won't see the login view

### New Employees
You will need fill all the fields, for the coordinates you can select a random number from -180 to 180 to longitude and -90 to 90 for latitude.\
After fill the fields you have to click the add button (it save the employee local and remote) and will appear a dialog, this dialog has 2 options, 
add a new employee or back to the previous view (menu view)

### Employees
Here you will see all the employees in the remote database, you can select one employee to see his ubication in a new view or select "show all" 
to display all employee locations

### Map
In the map view you will see a marker (or a lot of marker if you selected "show all") and can click it to see the demployee's details

## Architecture
This demo has a clean architecture. View -> ViewModel -> UseCase -> Repository -> Service (firestoreSDK/retrofit/room)


## Tools
- Retrofit
- Gson
- Coroutines
- Dagger Hilt (android library)
- Room
- Firebase (Auth, Firestore)
- Navigation Component
- Maps
- ViewModel and LiveData

## TODO
- Handler internet errors
- UnitTest

## Directory
- data
- - database
- - model
- - network
- - repository
- di (modules)
- domain (useCase)
- ui (viewModel, adapter, viewHolder)
- - login
- - menu
- - - employee
- - - map
- util (extras)
