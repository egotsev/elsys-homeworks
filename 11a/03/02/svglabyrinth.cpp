#include "svglabyrinth.h"
#include "svgdrawer.h"

SVGLabyrinth::SVGLabyrinth(int width, int height)
:Labyrinth(width,height,SVG)
{
}

void SVGLabyrinth::Draw(Point position, Point cellSize) const
{
	Canvas labyrinthCanvas(_width * cellSize.get_x(), _height * cellSize.get_y());
	labyrinthCanvas.Draw();
}