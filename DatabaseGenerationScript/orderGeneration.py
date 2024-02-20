
import random
import databaseGenerator
from datetime import datetime,date,time,timedelta
#add more 


NAMEPOOL = ["Josh","Jacob","Bart","Tom","Sydney","Ashton","Claire","Noah","James","Charles"
            "Amelia","Liam","Mia","Luca","Apollo","Hazel","Grover","George","John","Thomas","Marilyn"
            "Madison"]

#TODO Katelyn
EMPLOYEEIDPOOL = [1, 2, 3, 4, 5, 6]

#TODO MENUITEMS pool for Joseph 
MENUITEMSPOOL = []


class OrderGenerator:
    
    db = None
    def CreateOrder(self,date):
        name = NAMEPOOL[random(0, len(NAMEPOOL))]
        #Pick a random name with equal weight to all choices(Katelyn TODO)

        empID = EMPLOYEEIDPOOL[random(0, len(EMPLOYEEIDPOOL))]
        #Pick a random employee ID with equal weight to all choices(Katelyn TODO)

        #Pick from the MENUITEMS POOL a menu item(s) Joseph can do this part and it requires the CSV 
        #Joseph TODO read from menu CSV and get items 
        
        totalPrice = 0
        #Calcuate Tax with function below  (Katelyn TODO)
        tax = self.CalculateTax(totalPrice)

        #Generate time can be equal weight or proritize rush traffic but must be during hours that revs is open (Katelyn TODO)
            #OPENED HOURS:
                #Mon-Thurs: 10a-9p = .weekday()=[0,3]
                #Fri: 10a-8p = .weekday() = [4]
                #Sat-Sun: 11a-8p = .weekday() = [5,6]
        dayOfTheWeek = date.weekday()
        if (dayOfTheWeek >= 0 and dayOfTheWeek <=3):
            openHours = list(range(10,22))
            openWeights = [1,4,4,2,1,1,1,3,4,5,3,1]
        elif (dayOfTheWeek == 4):
            openHours = list(range(10,21))
            openWeights = [1,4,4,2,1,1,1,3,4,3,1]
        else:
            openHours = list(range(11,21))
            openWeights = [1,2,4,2,1,1,1,3,3,2]

        t = time(hour = choices(openHours, openWeights, k=1)[0], minute = random(0, 59), second = random(0, 59))
        #is an object of the time class https://docs.python.org/3/library/datetime.html#time-objects    
        dt= datetime.combine(date,t)
        
        self.db.cur.execute("INSERT INTO Orders (CustomerName, TaxPrice, OrderDateTime, EmployeeID) VALUES (%s, %s, %s, %s)",(name, tax, dt,empID))
        
        #insert into junction table between order and menu items 
        return

    def CalculateTax(self,price):
        #TODO (Katelyn)
        return price
    
    def __init__(self): 
        self.db = databaseGenerator.DbGenerator()
        password = input("Please enter the password: ")
        if not self.db.Connect("csce315_902_01_db","csce315_902_01_user",password,"csce-315-db.engr.tamu.edu"):
            raise ConnectionError("woah this means you are probably not on tamu wifi, do better")
        self.db.InitTable("Orders",["CustomerName varchar","TaxPrice decimal","OrderDateTime timestamp","EmployeeID integer"],IDpkey="OrderID")


def Main():
    og = OrderGenerator()
    day = date.fromisoformat('2023-02-19')
    delta = timedelta(days = 1)
    for i in range(0,365):
        # generate a random number of requests per day  (TODO Joseph)
        for j in range(0,550):
            og.CreateOrder(day)
            #print(j,end=" ")
        if(day  == date.fromisoformat('2023-01-19') or  day  == date.fromisoformat('2023-08-19')):
            for j in range(0,550):
                og.CreateOrder(day)
        day += delta
        print(day.day)
    return

if __name__ == "__main__":
    Main()