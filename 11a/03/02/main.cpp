#include "svglabyrinth.h"

int main()
{
	SVGLabyrinth lab(20,20);
	lab.Draw(Point(0,0), Point(10,10));
}