#include "svgcell.h"

Line* SVGCell::GetLine(Point position, Point size, Direction dir) const
{
	switch(dir)
	{
		case UP:
			return new Line(position, Point(position.get_x() + size.get_x(), position.get_y()));

		case RIGHT:
			return new Line(Point(position.get_x() + size.get_x(), position.get_y()),
						 Point(position.get_x() + size.get_x(), position.get_y() + size.get_y()));

		case DOWN:
			return new Line(Point(position.get_x(), position.get_y() + size.get_y()),
						 Point(position.get_x() + size.get_x(), position.get_y() + size.get_y()));

		case LEFT:
			return new Line(position, Point(position.get_x(), position.get_y() + size.get_y()));

		default:
			return new Line(position,position);
	}
}

void SVGCell::Draw(Point position, Point size) const
{
	CompositeFigure cellFigure;

	//For all directions
	for (int i = 0; i < 4; ++i)
	{
		Direction dir = (Direction)(UP << i);

		if(_walls & dir)
		{
			cellFigure.Add(GetLine(position, size, dir));
		}
	}

}