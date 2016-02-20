#include "svglabyrinth.h"

int main()
{
	SVGLabyrinth lab(2,2);
	lab.Draw(Point(0,0), Point(100,100));
}