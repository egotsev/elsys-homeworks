#include "cellfactory.h"
#include "svgcell.h"

CellFactory::CellFactory(CellTypes cellType)
{
	_cellType = cellType;
}


Cell* CellFactory::CreateCell()
{
	switch(_cellType)
	{
		case SVG:
			return new SVGCell();
		case PostScript:
			
			break;
	}
}