# App - Stock Manager

## Team structure and roles
+ Abhishek Chetri u6647717 - Team Leader
+ Mansoor Ali Halari u6621243 - team member
+ Dileep Vemula u6631257 - team member
+ Kruthi Senapathi u6601532 - team member 

## App Overview
The app is named Stock Manager. Stocks are primarily investment of an individual in a company. 
The stcoks/ shares are floated on stcok exchanges such as NYSE (New York Stock Exchange), NASDAQ, London Stock Exchange.
People use stocks as investment mechanism and put saving into it. 

The need and useability of app is real time management of stock list, display of current stock price & analysis and current news feed of the stocks.  

The app is desgined in four parts: Login, dashboard, analytics and newsfeed.
The app navigation includes both top and bottom.
The app features are connectivity to FireBase database, call to Alpha Vantage API, use of MPAndriodCharts, call to Stock News api.
The app also includes: About Us and Help

### Login
In the login screen, the user enters user email and password.
If the user is not registered, the app provides an option to register the 
user and it stores the user credentials and creates a database record to access and manage their 
stocks in a user friendly view. Existing user cannot register again. The Login page makes it very easy to 
access the app and go through it, as it has a effective and efficient interface that is connected to a real 
time database (Fire base) which is also easy to access and manage. This activity is the 1st page of our app 
and users needs to login to access the other features of this app. 

#### Dashboard
The user will move to dashboard screen once he/she gets logged in.
This view contains all the stocks that have been added to his profile. 
This also has a user friendly interface where one can always add/delete/edit his stocks into the users portfolio.
This activity allows users to edit stocks in realtime.

#### Analytics
An activity that can be accessed when user gets logged in and adds up the stocks. 
Its has real time analysed information that is obtained from a JSON file through web which is updated every day.
Its an activity that creates an analysed view of every particular stock that is added onto the users profile. 
It loads the stock details of every particular user from the dashboard activity and based on this it gives the user 
an access to analyse each and every stock that is been added. This activity has all the information such as open/close/high/low/volume
of every stock which is obtained by a realtime update. It also gives a visual overview of the stock by displaying the bar chart which makes
it easy for user to work on the stock.


#### News Feed
In news feed, a sentiment graph is displayed which tells about where the the stock has positive, neutral or negative news in the market. 
Its an activity which is also based on a realtime update of the stock its help user to get more updates about the particular stock.
This makes it easy for the user to predict the growth of the stock and whether to invest in it or opt not too.
#### About Us
This activity can be accessed from any activity of
the app such that it's easy for user to obtain information about the developers of this app. 
This activity aim is to acknowledge's the efforts of the developers. 

#### Help
Help includes detailed information about the app and its usage. 
It also has some predefined and answered frequently asked questions to help user get across the app with ease. 

## Design Documentation
+ [Design Summary](designsummary)
+ [Testing Summary](testingsummary)

## Minuted Meetings
+ [Meeting 1 - 21/04/2019 - decide team structure and app](meeting1.md)
+ [Meeting 2 - 23/04/2019 - divide up tasks](meeting2.md)
+ [Meeting 3 - 15/05/2019 - work on existing issues and add new functionality](meeting3.md)
+ [Meeting 4 - 22/05/2019 - review and work on documentation](meeting.md)

## Statement of Originality
I Abhishek Chetri declare that everything I have submitted in this
assignment is entirely my own work, with exceptions given below.

I Mansoor Ali Halari declare that everything I have submitted in this
assignment is entirely my own work, with exceptions given below.

I Dileep Vemula declare that everything I have submitted in this
assignment is entirely my own work, with exceptions given below.

I Kruthi Senapathi declare that everything I have submitted in this
assignment is entirely my own work, with exceptions given below.

### Inspiration
This is an utilitarian app based on users need for managing stocks list and portfolio. 

### Code
+ https://developer.android.com/guide/
+ https://firebase.google.com/docs/


### Assets
+ Credentials for Firebase: Username - softwareconstructionassignment@gmail.com Password - comp6442
+ Firebase: https://firebase.google.com/
+ Alpha Vantage: https://www.alphavantage.co/
+ Alpha Vantage API key - GF4EX3XKAFSY29GH
+ Stock News: https://stocknewsapi.com/
+ News API key: isto2i4cq7h369pzieouu5wxaw3n8bbjdgorkvaq