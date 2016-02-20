#ifndef SVGLABYRINTH_H
#define SVGLABYRINTH_H

#include "labyrinth.h"

class SVGLabyrinth : public Labyrinth
{
public:
	SVGLabyrinth(int width, int heigth);
	void Draw(Point position, Point cellSize) const;
};

#endif //SVGLABYRINTH_H