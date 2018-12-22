"Water Pipe Game":
===
Water Pipe Game is one of the best puzzle games. The main target of this game is to connect the pipes in the right order so the water reaches its destination. Touch the pipes to turn them and try to solve each level as soon as you can.
If you can’t find the solution you can always click on "solve" button and our genius algorithm will solve it for you.
In this game, the Server can automatically solve any level of (N X M) pipe game and provide a solution in a short time.

The client side was built using Model-View-ViewModel (MVVM) architecture and JavaFX.

If the client ask for a solution, the server will solve it for him and the solution will be saved in the local cache as a file. 
If the client provides a problem that the server has the solution for it the chaceManger will load the solution and provide the solution to the client. 
If the server will not have the solution to this problem the server will solve it and save the solution in the cache.

In this project we have implemented these algorithms:
- BFS
- DFS
- Best First Search
- Hill Climbing

This project was built to be generic in order to make it easy to use this code and change it to a different board game.

For more information contact us : idanshviro@gmail.com or barkazzaz@gmail.com.
