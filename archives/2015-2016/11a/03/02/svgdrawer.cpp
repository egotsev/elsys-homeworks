#include "svgDrawer.h"
#include <iostream>

//Styleable

Styleable::Styleable(){}

void Styleable::SetStyleAttr(std::string attr, std::string value)
{
	_styles.insert(std::pair<std::string, std::string>(attr, value));
}

std::string Styleable::GetStyle() const
{
	std::string styleStr = " style=\"";

	for (std::map<std::string, std::string>::const_iterator it = _styles.begin(); it != _styles.end(); ++it)
	{
		styleStr += it->first + ":" + it->second + ";";
	}

	styleStr += "\" ";
	return styleStr;
}


//Line

Line::Line(Point start, Point end): start_(start), end_(end) {}
	
void Line::Draw() const 
{
	std::cout << "<line x1=\"" << start_.get_x()
		<< "\" y1=\"" << start_.get_y()
		<< "\" x2=\"" << end_.get_x()
		<< "\" y2=\"" << end_.get_y()
		<< "\""
		<< GetStyle()
		<< " />"
		<< std::endl;
}

//Composite Figure


CompositeFigure::~CompositeFigure() 
{
	for (std::list<Shape*>::iterator it = _content.begin(); it != _content.end(); it++) 
	{
		delete *it;
	}
}

void CompositeFigure::Add(Shape* shape) 
{
	_content.push_back(shape);
}

void CompositeFigure::Draw() const 
{
	for (std::list<Shape*>::const_iterator it = _content.begin(); it != _content.end(); it++) 
	{
		(*it)->Draw();
	}
}

//Canvas

Canvas::Canvas(int width, int height) : _width(width), _height(height) {}

void Canvas::Add(Shape *shape)
{
	_content.Add(shape);
}

void Canvas::Draw() const {
	std::cout << "<svg width=\"" << _width 
		<< "\" height=\"" << _height
		<< "\" xmlns=\"http://www.w3.org/2000/svg\">" << std::endl;
	_content.Draw();
	std::cout << "</svg>" << std::endl;
}