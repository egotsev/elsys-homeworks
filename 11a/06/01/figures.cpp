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
		cout << "<circle cx=\"" << center_.get_x() << "\" cy=\"" << center_.get_y() << "\" r=\"" << radius_ << "\"/>" << endl;
	}
};

class Ellipse : public Shape
{
	int radius_x;
	int radius_y;
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
	Point points_;

public:

	Rectangle(Point points, int width, int height) : points_(points), width_(width), height_(height) {}

	void draw() const
	{
		cout << "<rect x=\"" << points_.get_x() << "\" y=\"" << points_.get_y() << "\" width=\"" << width_ << "\" height=\"" << height_ << "\"/>" << endl;
	}
};

class Line : public Shape
{
	Point start_;
	Point end_;
	int stroke_;

public:

	Line(Point start, Point end, int stroke) : start_(start), end_(end), stroke_(stroke) {}

	void draw() const
	{
		cout << "<line x1=\"" << start_.get_x() << "\" y1=\"" << start_.get_y() << "\" x2=\"" << end_.get_x() << "\" y2=\"" << end_.get_y() << "\" style=\"stroke:rgb(0,0,0);stroke-width:" << stroke_ << "\"/>" << endl;
	}
};

class Polygon : public Shape
{
	Point points_1;
	Point points_2;
	Point points_3;
	int stroke_;

public:

	Polygon(Point points1, Point points2, Point points3, int stroke) : points_1(points1), points_2(points2), points_3(points3), stroke_(stroke) {}

	void draw() const
	{
		cout << "<polygon points=\"" << points_1.get_x() << "," << points_1.get_y() << " " << points_2.get_x() << "," << points_2.get_y() << " " << points_3.get_x() << "," << points_3.get_y() << "\" style=\"fill:black;stroke:red;stroke-width:" << stroke_ << "\"/>" << endl;
	}
};

class Polyline : public Shape
{
	Point points_1;
	Point points_2;
	Point points_3;
	Point points_4;
	Point points_5;
	int stroke_;

public:

	Polyline(Point points1, Point points2, Point points3, Point points4, Point points5, int stroke) : points_1(points1), points_2(points2), points_3(points3), points_4(points4), points_5(points5), stroke_(stroke) {}

	void draw() const
	{
		cout << "<polyline points=\"" << points_1.get_x() << "," << points_1.get_y() << " " << points_2.get_x() << "," << points_2.get_y() << " " << points_3.get_x() << "," << points_3.get_y() << " " << points_4.get_x() << "," << points_4.get_y() << " " << points_5.get_x() << "," << points_5.get_y() << "\" style=\"fill:none;stroke:black;stroke-width:" << stroke_ << "\"/>" << endl;
	}
};

class Path : public Shape
{
	string commands_;

public:

	Path(string commands) : commands_(commands) {}

	void draw() const
	{
		cout << "<path d=\"" << commands_ << "\"/>" << endl;
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
	Canvas c(1000, 1000);
	c.add(new Circle(Point(20, 20), 15));
	c.add(new Circle(Point(50, 100), 50));
	c.add(new Ellipse(Point(200, 55), 100, 50));
	c.add(new Rectangle(Point(325, 10), 50, 100));
	c.add(new Line(Point(400, 10), Point(480, 150), 3));
	c.add(new Polygon(Point(10, 150), Point(150, 240), Point(75, 350), 3));
	c.add(new Polyline(Point(170, 150), Point(220, 190), Point(250, 270), Point(300, 200), Point(310, 140), 3));
	c.add(new Path("M330 150 L415 200 L365 350 Z"));
	c.draw();

	return 0;
}