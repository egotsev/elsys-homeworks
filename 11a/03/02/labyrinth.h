#ifndef LABYRINTH_H
#define LABYRINTH_H

#include <vector>
#include <map>

#include "cell.h"
#include "cellfactory.h"

class Labyrinth
{
	std::vector<Cell *> _cells;
	CellFactory _cellFactory;

protected:
	int _width;
	int _height;
	Cell* CellAt(int x, int y) const;

public:
	Labyrinth(int width, int height,  CellTypes cellType);
	void Generate(Point start, Point end);
	virtual void Draw(Point position, Point cellSize) const=0;
};

#endif //LABYRINTH_H