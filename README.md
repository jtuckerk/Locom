# Locom

WiP	- Code, description of work done, and to-do items 
- frontend 
  - LocomApp: combines LocationTest and LocomServerTestApp - code added
  - discussed UI/graphics design - slides added
- backend
- Server:
  - main thread: waits for connections 
  - serving thread: 
    - holds queue of messages to be sent out 
    - determines which user threads need to get each message 
    - holds mapping of threads to locations and tags?
  - user threads: one for each phone/app that connects 
    - communication
    - server queue
    - each thread has individual queue 

Message format: json object - dictionary 
	example- 
	message protocol: JSON 
	{“type”:“connect”, “username”:“user”, “location”: [long, lat], “tags”:[“food”, “party”]}
	{“type”:“update”, (same fields as connect)} 
	{“type”:“sendBroadcast”, “title”:“title”, “body”:“description”, “location”:[long, lat],	“radius”:(int) rad, “tags”:[“food”, “puppies”, “party”]}

Scenario: 
App							              	Server
phone connects					  -  new connection/thread
auto. sends location/tags				- saves info
  location changes
  tags updated 
waits for message					- receives message to send
receives message					- broadcasts to apps that match
                                tags/location
 


------------------------------------------------------------------------------------------------------------
Feasibility Study

LocationTest: prints to log the longitude and latitude of the device using Google Play Services Location API

locomServer283, LocomServerTestApp: creates server, connects to the server, dispatches thread for each connection 
