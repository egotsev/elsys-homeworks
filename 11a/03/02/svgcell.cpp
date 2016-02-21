#include "svgcell.h"
#include <iostream>

SVGCell::SVGCell(Point _position, Point _size)
:Cell(_position, _size)
{}

void SVGCell::Draw() const
{
	CompositeFigure cellFigure;
	//For all directions
	for (int i = 0; i < 4; ++i)
	{
		Direction dir = (Direction)(UP << i);

		if(HasWall(dir))
		{
			cellFigure.Add(GetWallLine(dir));
		}
	}

	cellFigure.Draw();
}

Line* SVGCell::GetWallLine(Direction dir) const
{
	Line *line;
	switch(dir)
	{
		case UP:
			line = new Line(_position, Point(_position.get_x() + _size.get_x(), _position.get_y()));
			break;
		case RIGHT:
			line = new Line(Point(_position.get_x() + _size.get_x(), _position.get_y()),
						 Point(_position.get_x() + _size.get_x(), _position.get_y() + _size.get_y()));
			break;
		case DOWN:
			line = new Line(Point(_position.get_x(), _position.get_y() + _size.get_y()),
						 Point(_position.get_x() + _size.get_x(), _position.get_y() + _size.get_y()));
			break;
		case LEFT:
			line =  new Line(_position, Point(_position.get_x(), _position.get_y() + _size.get_y()));
			break;
		default:
			// This shouldn't execute 
			return new Line(_position,_position);
	}

	line->SetStyleAttr("stroke", "rgb(255,0,0)");
	line->SetStyleAttr("stroke-width", "2");

	return line;
}