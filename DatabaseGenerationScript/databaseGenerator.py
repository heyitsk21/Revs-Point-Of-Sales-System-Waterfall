import psycopg2 as psy


class DbGenerator:    

    def Connect(self,databasename,username):
        psy.connect("dbname=" + databasename + " user=" + username)
        return

    