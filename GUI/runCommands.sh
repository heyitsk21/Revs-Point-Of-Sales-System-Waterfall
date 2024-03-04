javac -cp bin -d bin sqlCommands/*.java
javac -cp bin -d bin Manager/Panels/*.java
javac -cp bin -d bin Manager/*.java
javac -cp bin -d bin Employee/*.java
javac -cp bin -d bin Database.java
javac -cp bin -d bin LogInGUI.java
javac -cp bin -d bin GUI.java
java -cp "bin:postgresql-42.2.8.jar" GUI