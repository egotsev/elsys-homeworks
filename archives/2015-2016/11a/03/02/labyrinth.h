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
	Point _startPos;
	Point _endPos;

	Cell* CellAt(int x, int y) const;
	Direction GetEntryDirection(Point position) const;
public:
	Labyrinth(int width, int height, Point cellSize,  CellTypes cellType);
	void Generate(Point start, Point end);
	void Drill(Cell *cell);
	virtual void Draw() const=0;
};

#endif //LABYRINTH_H