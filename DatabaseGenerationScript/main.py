
try:
    import databaseGenerator 
    import multiprocessing as mp
    import csv
except:
    raise RuntimeError("install the modules with pip ask Joseph how")

# edit this with all filepaths foreach csv, 1 csv for each table, in order to see how to format see test.csv or ask Joseph 
filepaths = ["test.csv","happy.csv"]

def CreateTable(filename,password):

    #connect to PSQL server
    db = databaseGenerator.DbGenerator()
    if not db.Connect("csce315_902_01_db","csce315_902_01_user",password,"csce-315-db.engr.tamu.edu"):
        raise ConnectionError("woah this means you are probably not on tamu wifi, do better")
        
    with open(filename, newline='') as csvfile:
        reader = csv.reader(csvfile)
        firstRow = next(reader)
        db.InitTable(filename.split(".")[0], firstRow,IDpkey="ID")

        for row in reader:
            db.InsertElement(row)


def Main():

    processes = []
    mp.set_start_method('spawn')
    password = input("Please enter the password: ")
    for f in filepaths:
        p = mp.Process(target=CreateTable, args=(f,password))
        p.start()
        processes.append(p)
    
    for p in processes:
        p.join()
    return

if __name__ == "__main__":
    Main()