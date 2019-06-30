# Chat Application
A chat application written in Java with cool features! This app is made for second semester's final project.

## Features
* Text styling
* Git changes notification
* Server password protection
* Code discussion features

## Requirements
* macOS, Windows or Linux operating system
* Git CLI on PATH
* Java Runtime Environment 8 or higher (tested on version 8)

## Usage
### Client
#### Running a client
To run the app as client, just open the jar file by double clicking it on your file explorer. you will be greeted with a login screen that looks like this.
![](https://github.com/zefryuuko/pl-final-project/blob/master/documentation/client-login-window.png?raw=true)
You will need to enter this information to connect to the server:
* `Host` the server address that you want to connect to.
* `Password` the password required to the server. (optional, depends on the server)
* `Username` the name you will be seen as. Must be unique. Can only contain letters, numbers, underscore and period.

`Save to list` button will save the information filled on the text fields. The host must be unique to other saved information. `Remove from list` button simply removes the selected list item. If you accidentally pressed the button, just click save to list because the item you removed is autofilled when you select the item.

### Server
#### Running a server
To run as server, run this command in the terminal. Or make a shell script to run the server easily later on.
```
java -jar chat.jar servermode
```
You'll need to configure the server before running it. A configuration file will be made once you run the command for the first time and you need to configure it before running the server. The configuration file is located at `appdata/server-config.properties` on your current working directory. The configuration file looks like this
```
#Server properties
#Fri Jun 21 10:06:42 WIB 2019
server_name=A Chat Server
server_password=
server_description=A regular chat server
server_git_address=
max_saved_messages=50
```
Description:
* `server_name` the name of the server. Will be shown on the top bar. (required)
* `server_description` description of the server. Will be shown after server name. (optional)
* `server_password` a password used to log in to the server. (optional)
* `server_git_address` git address that will be used on the server for notifications and code discussion feature. (optional)
* `max_saved_messages` chat history buffer size. Changing this number to a higher value preserves more messages, but increases memory usage and user login time. Default is 50.

After setting up the configuration file, re-run the server using the same command as before. You will see this output
```
Starting in server mode...
[Server-5550] Server thread started.
```
This indicates that the server is configured properly and ready for users to connect into. You may see other text as actions are happening in the server.
#### Stopping the server
The server can be stopped by pressing `CTRL+C` on the terminal window.
#### Clearing chat history
To clear the server's chat history, stop the server and delete `saved-messages.msgcontainer` located on the `appdata` folder on your current working directory. Deleting messages when the server is still running has a chance of the file reappearing as sending a message (bot messages included) triggers the history saving method which saves all messages stored in memory.

## Troubleshooting
### Connection timed out
The connection will time out if the server takes too long to respond. This could happen when the connection between the server and the client is slow. This could also happen if you entered the wrong server address.
### Users not on the same network cannot connect to the server
This happened because the port is not open to the outside network. You can fix this by port forwarding TCP at port 5550. If port forwarding is not an option, use reverse SSH or ngrok.
### Git not detected on PATH
This happened because git is not installed on your computer or it is not set in the system environment variables or PATH. If you don't have git installed on your computer, download it from [here](https://gitforwindows.org/) if you are using Windows. macOS should have it installed by default. To install it on linux, use your preferred package manager to install git. If you have it installed but can't use git features, Follow [this](https://superuser.com/a/284351) tutorial to add git to system path.
