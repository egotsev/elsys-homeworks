#include <iostream>
#include <list>
using namespace std;

class Point
{
	int x_,y_;
public:
	Point(int x=0, int y=0) : x_(x), y_(y) {}

	int get_x() const {
		return x_;
	}
	int get_y() const {
		return y_;
	}
};
class Shape
{
public:


	virtual void draw() const=0;
};

class Ellipse: public Shape {
	int rx_;
	int ry_;
	Point center_;

public:
	Ellipse(Point center, int rx, int ry): center_(center) ,rx_(rx), ry_(ry) {}

	void draw() const {
		cout <<   "<ellipse cx=\"" << center_.get_x()
				 << "\" cy=\"" << center_.get_y()
				 << "\" rx=\"" << rx_ 
				 << "\" ry=\"" << ry_ 
				 << "\" />"
				 << endl;
	}
};

class Rectangle: public Shape{
	int height_;
	int width_;
public:
	Rectangle(int width, int height): width_(width), height_(height) {}

	void draw() const {
		cout <<   "<rect width=\"" << width_
				 << "\" height=\"" << height_
				 << "style=""fill:rgb(0,0,255);stroke-width:3;stroke:rgb(0,0,0) " "/> "
				 << endl;
	}
	

};

class CompositeFigure: public Shape {

	list<Shape*> content_;
public:

	~CompositeFigure()
	{
		for (list<Shape*>::const_iterator it = content_.begin();it !=content_.end(); it++)
		{
			delete *it;
		}

	}
	void add(Shape* shape)
	{
		content_.push_back(shape);
	}
	void draw() const {
		for (list<Shape*>::const_iterator it = content_.begin();it !=content_.end(); it++)
		{
			(*it)->draw();
		}
	}
};
class Canvas : public CompositeFigure {
	int width_, height_;
	public:
		Canvas(int width=100, int height = 100) : width_(width), height_(height) {}

		void draw() const {
			cout << "<svg width=\"" << width_
					<< "\" height=\"" << height_
					<< "\">" << endl;
				CompositeFigure::draw();
				cout << "</svg>" << endl;
		}



};
int main ()
{
	Canvas c(400,600);
	c.add(new Ellipse((20,20), 15, 15));
	c.draw();
	return 0;
}