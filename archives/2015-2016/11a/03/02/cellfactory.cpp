#include "cellfactory.h"
#include "svgcell.h"

CellFactory::CellFactory(CellTypes cellType)
{
	_cellType = cellType;
}


Cell* CellFactory::CreateCell(Point position, Point size)
{
	Cell *cell;

	switch(_cellType)
	{
		case SVG:
			cell = new SVGCell(position, size);
			break;
		default:
			throw NotImplementedException();
	}

	return cell;
}