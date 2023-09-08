/*
An interface of the simulation rule
Basically a helper interface
 */

package com.nam.lifegame.model;

public interface SimulationRule
{
    CellState getNextState(int x, int y, Board board);
}