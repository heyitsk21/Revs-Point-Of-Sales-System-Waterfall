
import random
import csv
from datetime import datetime,date,time,timedelta
#add more 


NAMEPOOL = ["Josh", "Jacob", "Bart", "Tom", "Sydney", "Ashton", "Claire", "Noah", "James", "Charles",
            "Amelia", "Liam", "Mia", "Luca", "Apollo", "Hazel", "Grover", "George", "John", "Thomas",
            "Marilyn", "Madison", "Nathan", "Lynda", "Ted", "Franklin", "Christian", "Danny", "Kasey", "Lillian",
            "Michelle","Alex","Alexander","Michael","Jenna","Izzy","Travis","Abe","Donna","Elmo","Grover","Oscar",
            "Count","Kermit","Bonzo","Gonzo","Scooter","Beaker","Rizzo","Animal","SamEagle","Statler","Waldorf",
            "Walter","Bobo","Clifford","Sweetums","SweedishChef","Edward","Bella","Charile","Alice","Emmet","Carlise",
            "Renee","Esme","Jasper","Karen","Rensemee","Jason","Katniss","Snow","Peeta","Gale","Haymitch","Effie",
            "Cinna","Prim","Tigris","Lucy","Ceaser","Plutarch","Seneca","Marvel","Glimmer","Cato","Foxface",
            "Thresh","Rue","Beete","Finnick","Johanna","Mags","Wirees","Gloss","Woof", "Emma", "Olivia", "Isabelle", 
            "Ava", "Sophie", "Emily", "Madison", "Mia", "Hannah", "Anna", "Abigail", "Chloe", "Ella", "Grace", "Charlotte", 
            "Avery", "Emery", "Sarah", "Sarahbeth", "Samantha", "Alexis", "Taylor", "Addison", "Natalie", "Hailey", 
            "Hailey", "Zoe", "Victoria", "Riley", "Harper", "Lauren", "Savannah", "Evelyn", "Ellie", "Kayla", "Sydney", 
            "Alyssa", "Julia", "Audrey", "Maya", "Maia", "Mackenzie", "Aubrey", "Zoey", "Morgan", "Claire", "Brianna", 
            "Brooke", "Brooklyn", "Leah", "Lillian", "Kaylee", "Ariana", "Gabriella", "Troy", "Ashley", "Lucy", "Kaitlyn", 
            "Kylie", "Madeline", "Allison", "Allyson", "Peyton", "Stella", "Megan", "Meghan", "Jasmine", "Ariel", "Aurora", 
            "Caroline", "Alexandra", "Eva", "Isabelle", "Aaliyah", "Paige", "Nora", "Kennedy", "Luna", "Jess", "Arianna", 
            "Violet", "Maria", "Skylar", "Rachel", "Abby", "Faith", "Sara", "Penelope", "Katie", "Bailey", "Camila", 
            "Nevaeh", "Alexa", "Sadie"]

#TODO Katelyn
EMPLOYEEIDPOOL = [1, 2, 3, 4, 5, 6]

#TODO MENUITEMS pool for Joseph can be generated by the csv but 
MENUITEMSPOOL = {
101 : 8.29,
102	: 8.38,
103 : 6.89,
104	: 7.59,
105 : 6.89,
201	: 8.39,
202	: 8.39,
203	: 8.39,
301	: 8.99,
401	: 4.49,
402	: 4.49,
403	: 4.49,
404	: 4.49,
405	: 4.69,
406	: 4.69,
407	: 5.49,
501	: 1.99,
502	: 1.79,
503	: 2.19,
504	: 1.99,
601	: 4.99,
602	: 4.99,
603	: 4.99,
701	: 6.00,
702	: 7.99}


class OrderGenerator:
    
    f1 = None
    f2 = None

    def CreateOrder(self, date, ID):
        name = NAMEPOOL[random.randrange(0, len(NAMEPOOL))]
        #Pick a random name with equal weight to all choices(Katelyn TODO)

        empID = EMPLOYEEIDPOOL[random.randrange(0, len(EMPLOYEEIDPOOL))]
        #Pick a random employee ID with equal weight to all choices(Katelyn TODO)

        #Pick from the MENUITEMS POOL a menu item(s) Joseph can do this part and it requires the CSV 
        #Joseph TODO read from menu CSV and get items 
        numberOfMenuItems = random.choices([1,2,3,4,5,6,7,8,9,10,50],[4000,5000,4300,200,100,100,100,100,100,100,1],k=1)[0]
        
        totalPrice = 0
        for i in range(numberOfMenuItems):
            item = random.choices(list(range(25)),
                [4,4,4,4,4,4,4,4,1,1,1,1,1,1,1,1,1,1,4,4,4,4,4,4,1])[0] 
            itemID = list(MENUITEMSPOOL.keys())[item]
            totalPrice += MENUITEMSPOOL[itemID]
            self.f2.write(str(ID) + ',' + str(itemID) +'\n')
            #insert into sql junction table
        

        #Calcuate Tax with function below  (Katelyn TODO)
        tax = self.CalculateTax(totalPrice)

        #Generate time can be equal weight or proritize rush traffic but must be during hours that revs is open (Katelyn TODO)
        dayOfTheWeek = date.weekday()
        if (dayOfTheWeek >= 0 and dayOfTheWeek <=3): #Mondays - Thursdays
            openHours = list(range(10, 22))          #open from 10am - 9pm
            openWeights = [1,4,4,2,1,1,1,3,4,5,3,1]
        elif (dayOfTheWeek == 4):                    #Fridays
            openHours = list(range(10, 21))          #open from 10am - 8pm
            openWeights = [1,4,4,2,1,1,1,3,4,3,1] 
        else:                                        #Saturdays - Sundays
            openHours = list(range(11, 21))          #open from 11am - 8pm
            openWeights = [1,2,4,2,1,1,1,3,3,2] 

        t = time(hour = random.choices(openHours, openWeights, k=1)[0], minute = random.randrange(0, 59), second = random.randrange(0, 59))
        #is an object of the time class https://docs.python.org/3/library/datetime.html#time-objects    
        dt= datetime.combine(date, t)
        self.f1.write(str(ID)+','+name + ',' + str(tax) + ','+ str(totalPrice) +',' + str(dt) + ',' + str(empID)+'\n')
        #insert into junction table between order and menu items 
        return

    def CalculateTax(self, price):
        #TODO (Katelyn)
        price *= 0.0825
        return price
    
    def __init__(self): 
        self.f1 = open("Orders.csv","w")
        self.f2 = open("JunctionOrdersMenu.csv","w")
        self.f1.write("OrderID,CustomerName,TaxPrice,BasePrice,OrderDateTime,EmployeeID\n")
        self.f2.write("OrderID,MenuID\n")

    def __del__(self):
        self.f1.close()
        self.f2.close()




def Main():
    og = OrderGenerator()
    day = date.fromisoformat('2023-02-19')
    delta = timedelta(days = 1)
    ID = 0
    for i in range(0,365):
        # generate a random number of requests per day  (TODO Joseph)
        orderMod = random.randrange(0,100)
        for j in range(0,500 + orderMod):
            og.CreateOrder(day,ID)
            ID += 1
            #print(j,end=" ")
        if(day  == date.fromisoformat('2024-01-19') or  day  == date.fromisoformat('2023-08-19')):
            for j in range(0,5500):
                og.CreateOrder(day,ID)
                ID += 1
        day += delta
        print(day)
    return

if __name__ == "__main__":
    Main()