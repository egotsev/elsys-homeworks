#ifndef CELL_FACTORY
#define CELL_FACTORY

#include <string>

#include "cell.h"
#include "point.h"

enum CellTypes
{
	SVG,
	PostScript
};

class NotImplementedException{};

class CellFactory
{
private:
	CellTypes _cellType;

public:
	CellFactory(CellTypes cellType);
	Cell* CreateCell(Point position, Point size);
};

#endif //CELL_FACTORY