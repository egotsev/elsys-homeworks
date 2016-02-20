#ifndef SVG_CELL_H
#define SVG_CELL_H

#include "cell.h"
#include "svgdrawer.h"

class SVGCell : public Cell
{
private:
	Line* GetLine(Point position, Point size, Direction dir) const;
public:
	void Draw(Point position, Point size) const;
};

#endif //SVG_CELL_H