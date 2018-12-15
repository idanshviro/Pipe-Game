package clientHandler;

import java.io.*;
import java.util.List;
import solver.PipeGameSolution;
import solver.PipeGameSolver;
import solver.Solution;
import solver.Solver;
import state.Board;
import state.State;
import cacheManager.CacheManager;
import cacheManager.File;
import searcher.*;

public class MyCHandler implements ClientHandler{

	private CacheManager cacheManager;
	private Solver solver = new PipeGameSolver(new HillClimbing());

	@Override 
	public void handle(InputStream inFromClient, OutputStream outToClient,List<char[]> clientInput) {
		//Get input from Client
		PrintWriter outTC = new PrintWriter(outToClient);
		BufferedReader inFClient = new BufferedReader(new InputStreamReader(inFromClient));

		try {

			//Start handling the client input
			Board b = new Board(clientInput);
			State<Board> currentState = new State<Board>(b); // Create board from client input


			String problem = new String(currentState.getState().getBoard().get(0));
			for (int j=1;j<currentState.getState().getBoard().size();j++) {
				problem = problem + new String(currentState.getState().getBoard().get(j));
			} 

			//Save the problem into a problem.txt file
			cacheManager = new File();

			String solution;
			if (cacheManager.exists(problem)) {
				//				System.out.println("found in cache");
				solution = cacheManager.load(problem); 
			}
			else { 
				//TODO: f.close()
				Solution sol = solver.solve(b);
				solution = "solved";
				if(sol==null) {
					solution = "null";
					cacheManager.save(problem, sol);

				} 
				else {
					//					System.out.println("sol found");
					PipeGameSolution solut = new PipeGameSolution(sol);
					for(int i=0;i<solut.getActionList().size();i++){
						if(i==0)
							solution = solut.getActionList().get(i).toString();
						else {
							solution+= '\n' + solut.getActionList().get(i).toString();
						}
						cacheManager.save(problem, solut);
					}
				}
			}

			outTC.println(solution);
			outTC.println("done");
			outTC.flush();

			inFClient.close();
			outTC.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}