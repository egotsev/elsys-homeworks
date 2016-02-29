#ifndef SVGDRAWER_H
#define SVGDRAWER_H

#include "point.h"
#include <map>
#include <list>
#include <string>

//Styleable

class Styleable
{
private:
	std::map<std::string, std::string> _styles;

public:
	Styleable();

	void SetStyleAttr(std::string attr, std::string value);

	std::string GetStyle() const;
};

//Drawable

class Drawable
{
public:
	virtual void Draw() const=0;
};

//Shape

class Shape : public Drawable, public Styleable
{
public:
	virtual ~Shape(){}
};

//Line

class Line : public Shape 
{
private:
	Point start_;
	Point end_;
	
public:
	
	Line(Point start, Point end);
	
	void Draw() const;
};

//Composite Figure

class CompositeFigure : public Drawable 
{
private:
	std::list<Shape*> _content;
	
public:
	~CompositeFigure();

	void Add(Shape* shape);
	
	void Draw() const;
};

// Canvas

class Canvas 
{
private:
	int _width, _height;
	CompositeFigure _content;
public:
	
	Canvas(int width=100, int height=100);
	void Add(Shape *shape);
	void Draw() const;
	
};

#endif //SVGDRAWER_H