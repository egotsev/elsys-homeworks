/*
	Homework:
	Implement the following figures:
	1. Ellipse
	2. Rectangle
	3. Line
	4. Polygon
	5. Polyline
	6. Path
	Deadline: 16.02.2016 17:00
	Place: https://github.com/egotsev/elsys-homeworks/11a/{number_in_class}/01
*/

#include <iostream>
#include <string>
#include <list>

using namespace std;

class Point {
	int x_, y_;
	
	public:
	
	Point(int x=0, int y=0) : x_(x), y_(y) {}
	
	int get_x() const {
		return x_;
	}
	
	int get_y() const {
		return y_;
	}
};

class Shape {
	public:
	
	virtual void draw() const=0;
};

class Circle : public Shape {
	int radius_;
	Point center_;
	
	public:
	
	Circle(Point center, int radius): center_(center), radius_(radius) {}
	
	void draw() const {
		cout << "<circle cx=\"" << center_.get_x()
			<< "\" cy=\"" << center_.get_y()
			<< "\" r=\"" << radius_ << "\" />"
			<< endl;
	}
};

class Ellipse : public Shape {
	Point center_;
	Point radius_;
	
	public:
	
	Ellipse(Point center, Point radius): center_(center), radius_(radius) {}
	Ellipse(Point center, int radiusX, int radiusY): center_(center), radius_(radiusX, radiusY) {}
	
	void draw() const {
		cout << "<ellipse cx=\"" << center_.get_x()
			<< "\" cy=\"" << center_.get_y()
			<< "\" rx=\"" << radius_.get_x()
			<< "\" ry=\"" << radius_.get_y() << "\" />"
			<< endl;
	}
};

class Rectangle : public Shape {
	Point corner_;
	Point size_;
	
	public:
	
	Rectangle(Point corner, Point size): corner_(corner), size_(size) {}
	Rectangle(Point corner, int width, int height): corner_(corner), size_(width, height) {}
	
	void draw() const {
		cout << "<rect x=\"" << corner_.get_x()
			<< "\" y=\"" << corner_.get_y()
			<< "\" width=\"" << size_.get_x()
			<< "\" height=\"" << size_.get_y() << "\" />"
			<< endl;
	}
};

class Line : public Shape {
	Point start_;
	Point end_;
	
	public:
	
	Line(Point start, Point end): start_(start), end_(end) {}
	
	void draw() const {
		cout << "<line x1=\"" << start_.get_x()
			<< "\" y1=\"" << start_.get_y()
			<< "\" x2=\"" << end_.get_x()
			<< "\" y2=\"" << end_.get_y() << " style=\"stroke:rgb(255,0,0);stroke-width:2\" />"
			<< endl;
	}
};

class Polygon : public Shape {
	list<Point> _vertices;
	
	public:
	
	Polygon(list<Point> vertices): _vertices(vertices) {}
	
	void draw() const {
		cout << "<polygon points=\"";

		for (list<Point>::const_iterator it = _vertices.begin(); it != _vertices.end(); ++it)
		{
			cout << it->get_x() << "," << it->get_y() << " ";
		}

		cout << "\" />" << endl;
	}
};

class Polyline : public Shape {
	list<Point> _vertices;
	
	public:
	
	Polyline(list<Point> vertices): _vertices(vertices) {}
	
	void draw() const {
		cout << "<polyline points=\"";

		for (list<Point>::const_iterator it = _vertices.begin(); it != _vertices.end(); ++it)
		{
			cout << it->get_x() << "," << it->get_y() << " ";
		}

		cout << "\" />" << endl;
	}
};


class Path : public Shape {
	list<string> _commands;
	
	public:
	
	Path(list<string> commands): _commands(commands) {}
	
	void draw() const {
		cout << "<path d=\"";

		for (list<string>::const_iterator it = _commands.begin(); it != _commands.end(); ++it)
		{
			cout << *it << " ";
		}

		cout << "\" />" << endl;
	}
};

class CompositeFigure : public Shape {
	list<Shape*> content_;
	
	public:
	
	~CompositeFigure() {
		for (list<Shape*>::iterator it = content_.begin(); it != content_.end(); it++) {
			delete *it;
		}
	}

	void add(Shape* shape) {
		content_.push_back(shape);
	}
	
	void draw() const {
		for (list<Shape*>::const_iterator it = content_.begin(); it != content_.end(); it++) {
			(*it)->draw();
		}
	}
};

class Canvas : public CompositeFigure {
	
	int width_, height_;
	
	public:
	
	Canvas(int width=100, int height=100) : width_(width), height_(height) {}
	
	void draw() const {
		cout << "<svg width=\"" << width_ 
			<< "\" height=\"" << height_
			<< "\">" << endl;
		CompositeFigure::draw();
		cout << "</svg>" << endl;
	}
	
};


int main() {
	Canvas c(400, 600);
	c.add(new Circle(Point(20, 20), 15));
	c.draw();
	return 0;
}
