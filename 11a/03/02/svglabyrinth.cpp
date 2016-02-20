#include "svglabyrinth.h"
#include "svgdrawer.h"

SVGLabyrinth::SVGLabyrinth(int width, int height)
:Labyrinth(width,height,SVG)
{
}

void SVGLabyrinth::Draw(Point position, Point cellSize) const
{
	Canvas labyrinthCanvas(_width * cellSize.get_x(), _height * cellSize.get_y());
	labyrinthCanvas.BeginDraw();

	for (int i = 0; i < _width; ++i)
	{
		for (int j = 0; j < _height; ++j)
		{
			Point currCellPosition(i * cellSize.get_x(), j * cellSize.get_y());
			CellAt(i,j)->Draw(currCellPosition, cellSize);
		}
	}

	labyrinthCanvas.EndDraw();
}