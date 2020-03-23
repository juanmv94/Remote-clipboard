# Remote-clipboard

A multiplatform shared clipboard "hack" developed in Java.

It allows you to share the same clipboard data between different machines. Once the clipboard data changes in any machine, clipboard data is sent to a server using AES encryption, and clipboard is updated for the rest of machines.

It has support for plain text, formated text (html), and pictures

**Watch demo here:**

[ ![Video link](https://img.youtube.com/vi/F4n_VSEsFGc/0.jpg) ](https://www.youtube.com/watch?v=F4n_VSEsFGc)


## How does it work?
This project consists of two executable java classes.
* One implements a general use tcp broadcast server that fowards a tcp packet sent by one of the clients to each of the remaining clients
* One for the client that connects to the broadcast server

## How to lauch
For the server:

`java -cp Clipboard.jar clipboard.Broadcast {Port}`

For the client

`java -cp Clipboard.jar clipboard.Client {Host} {Port}`

## Development
Some aspects to improve for future versions are:
* Add IP filtering to the broadcast server.
* If there are 3 or more clients connected to a server and 2 send clipboard data at the same time, tcp packets can interlace and the third client will receive an invalid message and crash.
* If any non *Remote-clipboard* client connects to a server with *Remote-clipboard* clients and sends data, the *Remote-clipboard* clients will receive invalid messages and crash.
* If any *Remote-clipboard*  client exits before sending a full clipboard data, clients will receive invalid messages and crash.
* ...

**Consider changing the generic tcp broadcast server with a specific *Remote-clipboard* server that blocks/delays other clipboard request until the previous clipboard data transmission is succesfully finished/timeout.**