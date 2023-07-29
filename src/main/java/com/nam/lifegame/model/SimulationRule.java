package com.nam.lifegame.model;

public interface SimulationRule
{
    CellState getNextState(int x, int y, Board board);
}