#ifndef CELL_H
#define CELL_H

#include "point.h"
#include <map>

enum Direction{
	NONE = 0,
	UP = 1,
	RIGHT = UP << 1,
	DOWN = UP << 2,
	LEFT = UP << 3,
	ALL = UP | RIGHT | DOWN | LEFT,
};

class Cell
{
private:
	bool _visited;
	std::map<Direction, Cell*> _neighbours;
	
protected:
	Direction _walls;

public:
	Cell();
	void Drill(Direction dir);
	Cell* RandomDrill();
	void SetNeighbour(Direction dir, Cell* neighbour);
	virtual void Draw(Point position, Point size) const=0;
};	

#endif //CELL_H