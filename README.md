Java Server side "Pipe Game":
===
A server side code for classic pipe board game. 
In this game, the Server can automatically solve any level of (N X M) pipe game and provide a solution in a short time (depends on the size of the board).
The main target of this game is to connect the pipes in the right order so the water reaches its destination.
After solving the level, the solution will be saved in the local cache as a file. 
If the client provides a problem that the server has the solution for it. 
The chaceManger will load the solution and provide it to the client. If the server will not have the solution to this problem the server will solve it and save the solution.

In this project I have implemented these algorithms:
- BFS
- DFS
- Best First Search
- Hill Climbing

This project was built to be generic in order to make it easy to use this code and change it to a different board game.



For more information contact me : idanshviro@gmail.com
