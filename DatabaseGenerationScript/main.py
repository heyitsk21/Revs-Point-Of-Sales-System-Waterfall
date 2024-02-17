import databaseGenerator 

def Main():

    db = databaseGenerator.DbGenerator()
    
    if not db.Connect("dbname","username"):
        raise ConnectionError("Failed to connect to the database")
        return -1
    
    return

if __name__ == "__main__":
    Main()