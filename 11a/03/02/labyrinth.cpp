#include "labyrinth.h"

Labyrinth::Labyrinth(int width, int height, CellTypes cellType):_cellFactory(cellType)
{
	_width = width;
	_height = height;

	for (int i = 0; i < _width * _height; ++i)
	{
		_cells.push_back(_cellFactory.CreateCell());
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

}

Cell* Labyrinth::CellAt(int x, int y) const
{
	return _cells[y * _width + x];
}