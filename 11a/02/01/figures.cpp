

#include <iostream>
#include <list>
#include <string>

using namespace std;

class Point 
{
	int x_, y_;

public:

	Point(int x = 0, int y = 0) : x_(x), y_(y) {}

	int get_x() const
	{
		return x_;
	}

	int get_y() const
	{
		return y_;
	}
};

class Shape
{
public:

	virtual void draw() const = 0;

};

class Circle : public Shape
{
	int radius_;
	Point center_;

public:

	Circle(Point center, int radius) : center_(center), radius_(radius) {}

	void draw() const
	{
		cout << "<circle cx=\"" << center_.get_x() << "\" cy=\"" << center_.get_y() << "\" r=\"" << radius_ << "\" />" << endl;
	}
};

class Ellipse : public Shape
 {
	int radius_y;
	int radius_x;
 	Point center_;
 
 public:
 
 	Ellipse(Point center, int rad_x, int rad_y) : center_(center), radius_x(rad_x), radius_y(rad_y) {}
 
 	void draw() const
 	{
 		cout << "<ellipse cx=\"" << center_.get_x() << "\" cy=\"" << center_.get_y() << "\" rx=\"" << radius_x << "\" ry=\"" << radius_y << "\"/>" << endl;
 	}
 };

class Rectangle : public Shape
{
	int width_;
	int height_;
	Point dots_;

public:
	Rectangle(Point dots, int height, int width) : dots_(dots), height_(height), width_(width) {}

	void draw () const
	{
		cout << "<rect x=\"" << dots_.get_x() << "\" y=\"" << dots_.get_y() << "\" width=\"" << width_ << "\" height=\"" << height_ <<"\"/>" << endl;
	}
};

class Line : public Shape
{
	Point start_;
	Point end_;
	int stroke_;

public:
	Line(Point start, Point end, int stroke) : start_(start), end_(end), stroke_(stroke) {}
	
	void draw () const
	{
		cout << "<line x1=\"" << start_.get_x() << "\" y1=\"" << start_.get_y() << "\" x2=\"" << end_.get_x() << "\" y2=\"" << end_.get_y() << "\" style=\"stroke:rgb(0,0,0);stroke-width:" << stroke_ << "\"/>" << endl;
	}	
};

class Polygon : public Shape
{
	Point point1_;
	Point point2_;
	Point point3_;
	Point point4_;
	int stroke_;

public:
	Polygon(Point point1, Point point2, Point point3, Point point4, int stroke) : point1_(point1), point2_(point2), point3_(point3), point4_(point4), stroke_(stroke) {}

	void draw () const
	{
		cout << "<polygon points=\"" << point1_.get_x() << "," << point1_.get_y() << " " << point2_.get_x() << "," << point2_.get_y() << " " << point3_.get_x() << "," << point3_.get_y() << " " << point4_.get_x() << "," << point4_.get_y() << "\" style=\"fill:black;stroke:red;stroke-width:" << stroke_ << "\"/>" << endl;
	}
};
class Polyline : public Shape
{
	Point point1_;
	Point point2_;
	Point point3_;
	Point point4_;
	Point point5_;
	Point point6_;
	int stroke_;
public:
	Polyline(Point point1, Point point2, Point point3, Point point4, Point point5, Point point6, int stroke) : point1_(point1), point2_(point2), point3_(point3), point4_(point4),point5_(point5), point6_(point6), stroke_(stroke) {}

	void draw () const
	{
		cout << "<polyline points=\"" << point1_.get_x() << "," << point1_.get_y() << " " << point2_.get_x() << "," << point2_.get_y() << " " << point3_.get_x() << "," << point3_.get_y() << " " << point4_.get_x() << "," << point4_.get_y() << " " << point5_.get_x() << "," << point5_.get_y() << " " << point6_.get_x() << "," << point6_.get_y() << " "  << "\" style=\"fill:none;stroke:black;stroke-width:" << stroke_ << "\"/>" << endl;
	}	
};
class Path : public Shape
{
 	string command_;
 
 public:
 
 	Path(string command) : command_(command) {}
 
 	void draw() const
 	{
 		cout << "<path d=\"" << command_ << "\"/>" << endl;
 	}
 };
 
class CompositeFigure : public Shape
{
	list<Shape*> content_;

public:

	~CompositeFigure()
	{
		for (list<Shape*>::const_iterator it = content_.begin(); it != content_.end(); it++)
		{
			delete *it;
		}
	}

	void add(Shape* shape)
	{
		content_.push_back(shape);
	}

	void draw() const
	{
		for (list<Shape*>::const_iterator it = content_.begin(); it != content_.end(); it++)
		{
			(*it)->draw();
		}
	}
};

class Canvas : public CompositeFigure
{
	int width_, height_;

public:

	Canvas(int width = 100, int height = 100) : width_(width), height_(height) {}

	void draw() const
	{
		cout << "<svg width=\"" << width_ << "\" height=\"" << height_ << "\" xmlns=\"http://www.w3.org/2000/svg\">" << endl;
		CompositeFigure::draw();
		cout << "</svg>" << endl;
	}
};

int main()
{
	Canvas c(4000, 5000);
	c.add(new Circle(Point(30, 30), 10));
 	c.add(new Ellipse(Point(300, 40), 200, 35));
 	c.add(new Rectangle(Point(200, 400), 100, 280));
 	c.add(new Line(Point(600, 20), Point(300, 120), 3));
 	c.add(new Polygon(Point(20, 200), Point(150, 350), Point(75, 150), Point(85, 325), 3));
 	c.add(new Polyline(Point(180, 125), Point(250, 200), Point(240, 250), Point(100, 100), Point(260, 200), Point(250,200), 3));
 	c.add(new Path("M220 100 L415 200 L365 350 Z"));
	c.draw();

	return 0;
}