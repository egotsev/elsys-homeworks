#include "cell.h"

Cell::Cell()
{
	_walls = ALL;
}

Cell* Cell::RandomDrill()
{
}

void Cell::SetNeighbour(Direction dir, Cell* neighbour)
{
	_neighbours.insert(std::pair<Direction,Cell*>(dir,neighbour));
}