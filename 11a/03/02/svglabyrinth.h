#ifndef SVGLABYRINTH_H
#define SVGLABYRINTH_H

#include "labyrinth.h"
#include "svgdrawer.h"

class SVGLabyrinth : public Labyrinth
{
private:
	Canvas _canvas;
public:
	SVGLabyrinth(int width, int heigth, Point cellSize);
	void Draw() const;
};

#endif //SVGLABYRINTH_H