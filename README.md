# Locom

WiP	
Description of work done: 
- frontend 
  - LocomApp: combines LocationTest and LocomServerTestApp - code added
  - discussed UI/graphics design - slides added
- backend
  - discussed server (notes below) and communication between frontend and backend 

To-do items:
- finish frontend
- implement UI
- implement backend
- combine frontend and backend 

Other notes: 
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


------------------------------------------------------------------------------------------------------------
Feasibility Study

LocationTest: prints to log the longitude and latitude of the device using Google Play Services Location API

locomServer283, LocomServerTestApp: creates server, connects to the server, dispatches thread for each connection 
