
import random
import databaseGenerator
from datetime import datetime,date,time,timedelta
#add more 


NAMEPOOL = ["Josh","Jacob","Bart","Tom","Sydney","Ashton","Claire","Noah","James","Charles"
            "Amelia","Liam","Mia","Luca","Apollo","Hazel","Grover","George","John","Thomas","Marilyn"
            "Madison"]

#TODO Katelyn
EMPLOYEEIDPOOL = []

#TODO MENUITEMS pool for Joseph 
MENUITEMSPOOL = []


class OrderGenerator:
    
    db = None
    def CreateOrder(self,date):
        name = ""
        #Pick a random name with equal weight to all choices(Katelyn TODO)
        empID = 0
        #Pick a random employee ID with equal weight to all choices(Katelyn TODO)

        #Pick from the MENUITEMS POOL a menu item(s) Joseph can do this part and it requires the CSV 
        #Joseph TODO read from menu CSV and get items 
        
        totalPrice = 0
        #Calcuate Tax with function below  (Katelyn TODO)
        tax = self.CalculateTax(totalPrice)
        #Generate time can be equal wieght or proritize rush traffic but must be during hours that revs is open (Katelyn TODO)
        time = None
        #is an object of the time class https://docs.python.org/3/library/datetime.html#time-objects    
        dt= datetime.combine(date,time)

        self.db.cur.execute("INSERT INTO Orders (CustomerName, TaxPrice, OrderDateTime, EmployeeID) VALUES (%s, %s, %s, %s)",(name, tax, dt,empID))
        
        #insert into junction table between order and menu items 
        return

    def CalculateTax(self,price):
        #TODO (Katelyn)
        return price
    
    def __init__(self): 
        db = databaseGenerator.DbGenerator()
        password = input("Please enter the password: ")
        if not db.Connect("csce315_902_01_db","csce315_902_01_user",password,"csce-315-db.engr.tamu.edu"):
            raise ConnectionError("woah this means you are probably not on tamu wifi, do better")
        db.InitTable("Orders",["CustomerName varchar","TaxPrice decimal","OrderDateTime timestamp","EmployeeID integer"],IDpkey="OrderID")


def Main():
    og = OrderGenerator()
    day = date.fromisoformat('2019-12-04')
    delta = timedelta(days = 1)
    for i in range(0,365):
        # generate a random number of requests per day  (TODO Joseph)
        og.CreateOrder(day)
        day += delta
    
    return

if __name__ == "__main__":
    Main()