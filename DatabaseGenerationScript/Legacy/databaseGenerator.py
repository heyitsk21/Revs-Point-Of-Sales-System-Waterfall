import psycopg2 as psy

#basically a wrapper class bc why not 
class DbGenerator: 

    conn = None
    cur = None
    hasIdPk = None
    atrs = None
    types = None
    tablename = None
    #be careful when removing this try 
    def Connect(self, databasename, username, password, host):
        try:
            self.conn = psy.connect("dbname=" + databasename + " user=" + username + " password=" + password + " host=" + host) 
            self.cur = self.conn.cursor()
            return True
        except:
            return False
    
    #Takes in a list of attributes
    #IDpkey will auto create an IDpkey with the name given to it will not create the IDpkey if no name is given
    def InitTable(self, tablename, attributes, IDpkey = ""):
        
        if(self.cur == None):
            raise ConnectionError("Need to connect to the database before calling functions")
        self.tablename = tablename;    
        arg = ""
        if IDpkey != "":
            arg += IDpkey + " serial PRIMARY KEY, "
            self.hasIdPk = True

        for index,atr in enumerate(attributes):
           self.atrs += atr.split()[0]
           self.types.append(atr.split()[1])
           arg += atr
           if index != len(attributes) - 1:
               self.atrs += ", "
               arg += ", "
        self.cur.execute("DROP TABLE " + self.tablename)
        self.cur.execute("CREATE TABLE " + self.tablename + " (" + arg + ");")
        return
    
    def InsertElement(self, elem):

        if(self.cur == None):
            raise ConnectionError("Need to connect to the database before calling functions")
        arg = ""
        for index,atr in enumerate(elem):
           if self.types[index] == "varchar":
               arg += "'"
           arg += atr
           if self.types[index] == "varchar":
               arg += "'"
           if index != len(elem) - 1:
               arg += ", "

        self.cur.execute("INSERT INTO " + self.tablename + " (" + self.atrs + ") VALUES (" + arg + ");")  

    #constructor
    def __init__(self):
        self.hasIdPk = False
        self.atrs = ""
        self.types = []
        self.tablename = ""

    #destuctor 
    def __del__(self):
        self.conn.commit()
        self.cur.close()
        self.conn.close()