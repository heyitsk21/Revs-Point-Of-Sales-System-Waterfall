import databaseGenerator 


def Main():


    db = databaseGenerator.DbGenerator()
    if not db.Connect("csce315_902_01_db","csce315_902_01_user","team21","csce-315-db.engr.tamu.edu"):
        raise ConnectionError("woah this means you are probably not on wifi, do better")

    
    return

if __name__ == "__main__":
    Main()