#include "svgcell.h"

Line* SVGCell::GetLine(Point position, Point size, Direction dir) const
{
	Line *line;
	switch(dir)
	{
		case UP:
			line = new Line(position, Point(position.get_x() + size.get_x(), position.get_y()));
			break;
		case RIGHT:
			line = new Line(Point(position.get_x() + size.get_x(), position.get_y()),
						 Point(position.get_x() + size.get_x(), position.get_y() + size.get_y()));
			break;
		case DOWN:
			line = new Line(Point(position.get_x(), position.get_y() + size.get_y()),
						 Point(position.get_x() + size.get_x(), position.get_y() + size.get_y()));
			break;
		case LEFT:
			line =  new Line(position, Point(position.get_x(), position.get_y() + size.get_y()));
			break;
		default:
			// This shouldn't execute 
			return new Line(position,position);
	}

	line->SetStyleAttr("stroke", "rgb(255,0,0)");
	line->SetStyleAttr("stroke-width", "2");

	return line;
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

	cellFigure.Draw();
}