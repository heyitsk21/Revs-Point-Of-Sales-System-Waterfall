import psycopg2 as psy

#basically a wrapper class bc why not 
class DbGenerator: 

    conn = None
    cur = None

    def Connect(self,databasename,username,password,host):
        try:
            self.conn = psy.connect("dbname=" + databasename + " user=" + username + " password=" + password + " host=" + host) 
            self.cur = self.conn.cursor()
            return True
        except:
            return False

    def __init__(self):
        pass
    