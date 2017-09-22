# client-server-task

Before starting using the apps build and package them first.

In the root folder run the following in the command line: `mvn clean package`
`.jar` files will be created for server and client sub-projects.

## server running
Go to `server\target` folder and run `java -jar server.jar` to run server with default settings.
Use key `-port` to set port number (`3000` by default)
Use key `-data` to set folder where server data is saved (`{user.home}\serverdata` by default)
Use key `-proc_count` to set number of threads to handle request (`2` by default)


## client running
Go to `client\target` folder.
To run a client use `java -jar client.jar` command with optional `-serverPort` key and one of the following commands
* `-addbird` to add a bird on the server
* `-addsighting` to add a sighting for a bird on the server
* `-listsightings` to show sightings
* `-remove` to remove a bird
* `-quit` to shut down the server

## logs
Client logs can be found in `{user.home}\logs\noname\client.log` file.

Server logs are printed to the console. 
`CONSOLE_SIMPLE` appender is used to print just messages. 
If expanded log messages are required `CONSOLE_EXTENDED` appender can be used.