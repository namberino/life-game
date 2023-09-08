// helper class

package com.nam.lifegame;

import com.nam.lifegame.model.Board;
import com.nam.lifegame.model.CellState;
import com.nam.lifegame.model.SimulationRule;

public class Simulation
{
    private Board simulationBoard;
    private SimulationRule simulationRule;

    public Simulation(Board simulationBoard, SimulationRule simulationRule)
    {
        this.simulationBoard = simulationBoard;
        this.simulationRule = simulationRule;
    }

    public void step()
    {
        Board nextState = simulationBoard.copy();

        for (int y = 0; y < simulationBoard.getWidth(); y++)
        {
            for (int x = 0; x < simulationBoard.getHeight(); x++)
            {
                CellState newState = simulationRule.getNextState(x, y, simulationBoard);
                nextState.setState(x, y, newState);
            }
        }

        this.simulationBoard = nextState;
    }

    public Board getBoard()
    {
        return simulationBoard;
    }
}