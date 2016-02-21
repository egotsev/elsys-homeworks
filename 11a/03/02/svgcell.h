#ifndef SVG_CELL_H
#define SVG_CELL_H

#include "cell.h"
#include "svgdrawer.h"

class SVGCell : public Cell, public Shape
{
private:
	Line* GetWallLine(Direction dir) const;
public:
	SVGCell(Point position, Point size);
	void Draw() const;
};

#endif //SVG_CELL_H