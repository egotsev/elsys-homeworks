#include "svglabyrinth.h"
#include "svgdrawer.h"
#include "svgcell.h"

SVGLabyrinth::SVGLabyrinth(int width, int height, Point cellSize)
:Labyrinth(width, height, cellSize, SVG),
_canvas((width + 2) * cellSize.get_x(), (height + 2) * cellSize.get_y())
{
	for (int i = 0; i < width; ++i)
	{
		for (int j = 0; j < height; ++j)
		{
			SVGCell *svgCell = static_cast<SVGCell*>(CellAt(i,j));

			if(svgCell != NULL)
			{
				_canvas.Add(svgCell);
			}
		}
	}
}

void SVGLabyrinth::Draw() const
{
	_canvas.Draw();
}