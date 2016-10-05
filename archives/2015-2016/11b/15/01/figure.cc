/*
	HW:
	1. Add Rectangle done
	2. Add Line done
	3. Add Ellipse done
	4. Add Polyline done
	5. Add Polygon done
	6. Add Path
*/

#include <iostream>
#include <list>
#include <string>
#include <sstream>
using namespace std;

class Point {
	int x_;
	int y_;
public:
	Point(int x=0, int y=0)
	: x_(x), y_(y)
	{}

	int get_x() const {
		return x_;
	}

	int get_y() const {
		return y_;
	}
};

class Styleable {
	string stroke_;
	string fill_;
	string style_;
public:
	Styleable()
	{}

	Styleable& set_stroke(string color) {
		stroke_=color;
		return *this;
	}

	Styleable& set_style(string style) {
		style_ = style;
		return *this;
	}

	string get_style() const {
		string result;
		if(!style_.empty()) {
			result = " style=\"" + style_ + "\" ";
			return result;
		}

		if(!stroke_.empty()) {
			result += " stroke=\"" + stroke_ + "\" ";
		}
		return result;
	}

};

class Drawable
{
public:
	virtual void draw() const = 0;
};
class Shape: public Styleable, public Drawable {
public:

};

class Path : public Shape
{
	string enter_;
public:
	Path(string enter)
	: enter_(enter)
	{}

	void draw() const
	{
		cout << "<path d=\"" << enter_
				 << "\" />" << endl;
	}
};

class Polyline : public Shape
{
	Point first_;
	Point second_;
	Point third_;
	Point fourth_;
	Point fifth_;
	Point sixth_;
public:
	Polyline(Point first, Point second, Point third,Point fourth, Point fifth, Point sixth)
	: first_(first),
		second_(second),
		third_(third),
		fourth_(fourth),
		fifth_(fifth),
		sixth_(sixth)
	{}

	void draw() const
	{
		cout << "<polyline points=\"" << first_.get_x()
			 << "," << first_.get_y()
			 << " " << second_.get_x()
	 		 << "," << second_.get_y()
			 << " " << third_.get_x()
	 		 << "," << third_.get_y()
			 << " " << fourth_.get_x()
	 		 << "," << fourth_.get_y()
			 << " " << fifth_.get_x()
	 		 << "," << fifth_.get_y()
			 << " " << sixth_.get_x()
	 		 << "," << sixth_.get_y()
			 << "\" " << get_style()
			 << "/>" << endl;
	}
};

class Polygon : public Shape
{
	Point first_;
	Point second_;
	Point third_;
public:
	Polygon(Point first, Point second, Point third)
	: first_(first),
		second_(second),
		third_(third)
	{}

	void draw() const
	{
		cout << "<polygon points=\"" << first_.get_x()
			 << "," << first_.get_y()
			 << " " << second_.get_x()
	 		 << "," << second_.get_y()
			 << " " << third_.get_x()
	 		 << "," << third_.get_y()
			 << "\" " << get_style()
			 << "/>" << endl;
	}
};

class Ellipse: public Shape {
	Point center_;
	int rx_;
	int ry_;
public:
	Ellipse(Point center, int rx, int ry)
	: center_(center),
	  rx_(rx),
		ry_(ry)
	{}

	void draw() const {
		cout << "<ellipse cx=\"" << center_.get_x()
			 << "\" cy=\"" << center_.get_y()
			 << "\" rx=\"" << rx_
			 << "\" ry=\"" << ry_
			 << "\" " << get_style()
			 << "/>" << endl;
	}
};

class Line : public Shape
{
	Point start_;
	Point end_;
public:
	Line(Point start, Point end)
	: start_(start),
		end_(end)
	{}

	void draw() const
	{
		cout << "<line x1=\"" << start_.get_x()
			 << "\" y1=\"" << start_.get_y()
			 << "\" x2=\"" << end_.get_x()
	 		 << "\" y2=\"" << end_.get_y()
			 << "\" " << get_style()
			 << "/>" << endl;
	}
};
class Rectangle : public Shape
{
	int width_;
	int height_;
public:
	Rectangle(int width, int height)
	: width_(width), height_(height)
	{}

	void draw() const
	{
			cout << "<rect width=\"" << width_
				 << "\" height=\"" << height_
				 << "\" " << get_style()
				 << "/>" << endl;
	}
};
class Circle: public Shape {
	Point center_;
	int r_;
public:
	Circle(Point center, int r)
	: center_(center),
	  r_(r)
	{}

	void draw() const {
		cout << "<circle cx=\"" << center_.get_x()
			 << "\" cy=\"" << center_.get_y()
			 << "\" r=\"" << r_
			 << "\" " << get_style()
			 << "/>" << endl;
	}
};

class CompositeFigure : public Shape
{
	list<Shape*> shapes_;

public:
	void add(Shape* shape) {
		shapes_.push_back(shape);
	}

	void draw() const {
		for(list<Shape*>::const_iterator it = shapes_.begin();
			it!=shapes_.end(); ++it) {

			(*it)->draw();
		}
	}

};
class Drawing : public Drawable {
	int width_, height_;
	CompositeFigure container_;
public:

	void add(Shape* shape)
	{
			container_.add(shape);
	}
	Drawing(int width, int height)
	: width_(width),
		height_(height)
	{}

	void draw() const {
		cout << "<svg width=\"" << width_
		<<"\" height=\"" << height_
		<< "\"" << ">" << endl;
		container_.draw();
		cout << "</svg>" << endl;
	}
};


int main() {

	Drawing d(400, 600);

	for(int i=0; i<50; ++i) {
		Path* pc = new Path("M150 0 L75 200 L225 200 Z");
		ostringstream style;
		style << "stroke: red;"
			  << "stroke-width: " << 0.1*i << "; "
			  << "fill: rgb(" << i << "," << i << ", " << 3*i << ");"
			  << "fill-opacity: " << 0.1 + i*.005 << ";";

		pc->set_style(style.str());
		d.add(pc);
	}


	d.draw();

	return 0;
}
