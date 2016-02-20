#include "svglabyrinth.h"
#include "svgdrawer.h"

SVGLabyrinth::SVGLabyrinth(int width, int height)
:Labyrinth(width,height,SVG)
{
}

void SVGLabyrinth::Draw(Point cellSize) const
{
	Canvas labyrinthCanvas((_width + 2) * cellSize.get_x(), (_height + 2) * cellSize.get_y());
	labyrinthCanvas.BeginDraw();

	for (int i = 0; i < _width; ++i)
	{
		for (int j = 0; j < _height; ++j)
		{
			Point currCellPosition((i + 1) * cellSize.get_x(), (j + 1) * cellSize.get_y());
			CellAt(i,j)->Draw(currCellPosition, cellSize);
		}
	}

	labyrinthCanvas.EndDraw();
}