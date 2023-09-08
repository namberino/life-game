/*
Rules of the game:
1. Any live cell with fewer than two live neighbours dies
2. Any live cell with two or three live neighbours lives on to the next generation
3. Any live cell with more than three live neighbours dies
4. Any dead cell with exactly three live neighbours becomes a live cell
 */

package com.nam.lifegame.model;

public class StandardRule implements SimulationRule
{
    @Override
    public CellState getNextState(int x, int y, Board board)
    {
        int aliveNeighbours = countAliveNeighbours(x, y, board);

        if (board.getState(x, y) == CellState.ALIVE)
        {
            if (aliveNeighbours < 2 || aliveNeighbours > 3)
            {
                return CellState.DEAD;
            }
            else
            {
                return CellState.ALIVE;
            }
        }
        else
        {
            if (aliveNeighbours == 3)
            {
                return CellState.ALIVE;
            }
        }

        return CellState.DEAD;
    }

    public int countAliveNeighbours(int x, int y, Board board)
    {
        int count = 0;

        count += countCell(x - 1, y - 1, board);
        count += countCell(x, y - 1, board);
        count += countCell(x + 1, y - 1, board);

        count += countCell(x - 1, y, board);
        count += countCell(x + 1, y, board);

        count += countCell(x - 1, y + 1, board);
        count += countCell(x, y + 1, board);
        count += countCell(x + 1, y + 1, board);

        return count;
    }

    private int countCell(int x, int y, Board board)
    {
        if (board.getState(x, y) == CellState.ALIVE)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}