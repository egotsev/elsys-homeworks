#include "labyrinth.h"
#include <stdlib.h>
#include <time.h>

Labyrinth::Labyrinth(int width, int height, Point cellSize, CellTypes cellType):_cellFactory(cellType)
{
	_width = width;
	_height = height;

	for (int i = 0; i < _width; i++) 
	{
		for(int j = 0; j < _height; j++) 
		{
			_cells.push_back(_cellFactory.CreateCell(Point(j + 1, i + 1), cellSize));
		}
	}

	for (int i = 0; i < _width; i++) 
	{
		for(int j = 0; j < _height; j++) 
		{
			Cell* cell = CellAt(i, j);

			if (i < _width - 1)
				cell->SetNeighbour(RIGHT, CellAt(i + 1,j));

			if (i > 0)
				cell->SetNeighbour(LEFT, CellAt(i - 1, j));

			if (j < _height - 1)
				cell->SetNeighbour(DOWN, CellAt(i, j + 1));

			if (j > 0)
				cell->SetNeighbour(UP, CellAt(i, j - 1));
		}
	}
}

void Labyrinth::Generate(Point start, Point end)
{
	Direction entryDir = GetEntryDirection(start);
	if(entryDir == NONE)
		return;

	Direction exitDir = GetEntryDirection(end);
	if(exitDir == NONE)
		return;

	srand(time(NULL));
	Drill(CellAt(start.get_x(), start.get_y()));


	//Drill entry door
	CellAt(start.get_x(), start.get_y())->Drill(entryDir);

	// Drill exit door
	CellAt(end.get_x(), end.get_y())->Drill(exitDir);

	_startPos = start;
	_endPos = end;
}

void Labyrinth::Drill(Cell *cell)
{
	cell->Visit();

	while(cell->HasUnvisitedNeighbours())
	{
		Cell* drilledTo = cell->RandomDrill();
		Drill(drilledTo);
	}
}

Cell* Labyrinth::CellAt(int x, int y) const
{
	return _cells[y * _width + x];
}

Direction Labyrinth::GetEntryDirection(Point position) const
{
	if(position.get_x() == 0)
		return LEFT;
	else if(position.get_x() == _width - 1)
		return RIGHT;
	else if(position.get_y() == 0)
		return UP;
	else if(position.get_y() == _height - 1)
		return DOWN;
	else
		return NONE;
}