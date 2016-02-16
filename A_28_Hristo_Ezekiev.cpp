#include <iostream>
#include <list>
using namespace std;
class Point 
{
    int x_, y_;
public:
    Point(int x=0, int y=0) : x_(x), y_(y) {}
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
    virtual void draw() const=0;
};

class Circle : public Shape 
{
    int radius_;
    Point center_;
public:
    Circle(Point center, int radius): center_(center), radius_(radius) {}
    void draw() const 
    {
        cout << "<circle cx=\"" << center_.get_x()
        << "\" cy=\"" << center_.get_y()
        << "\" r=\"" << radius_ << "\" />"
        << endl;
    }
};

class Rect : public Shape
{
    int width_, height_;
    Point place_;
public:
    Rect(Point place, int width, int height): place_(place), width_(width), height_(height) {}
    void draw() const
    {
        cout << "<rect x=\"" << place_.get_x()
        << "\" y=\"" << place_.get_y()
        << "\" width=\"" << width_  
        << "\" height=\"" << height_ 
        << "\" />" << endl;
    }
};

class Ellipse : public Shape
{
	int width_, height_;
	Point center_;
public:
	Ellipse(Point center, int width, int height): center_(center), width_(width), height_(height) {}
    void draw() const
    {
        cout << "<ellipse cx=\"" << center_.get_x()
        << "\" cy=\"" << center_.get_y()
        << "\" rx=\"" << width_  
        << "\" ry=\"" << height_ 
        << "\" />" << endl;
    }
};

class Polygon : public Shape
{
	list<Point> points_;
public:
    Polygon(list<Point> points): points_(points) {}
    void draw()
    {
        cout << "polygon points=\"";
        for(list<Point>::iterator it = points_.begin(); it != points_.end(); ++it)
        {
            cout << it->get_x() << "," << it->get_y() << " "; 
        }
        cout << "\" />";
    }
};

class Polyline : public Shape
{
    list<Point> points_;
public:
    Polyline(list<Point> points): points_(points) {}
    void draw()
    {
        cout << "polyline points=\"";
        for(list<Point>::iterator it = points_.begin(); it != points_.end(); ++it)
        {
            cout << (*it).get_x() << "," << (*it).get_y() << " "; 
        }
        cout << "\" />";
    }
};

class Line : public Shape
{
	Point from_;
	Point to_;
public:
	Line(Point from, Point to): from_(from), to_(to) {}
	void draw() const
	{
		cout << "<line x1=\"" << from_.get_x()
		<< "\" y1=\"" << from_.get_y()
		<< "\" x2=\"" << to_.get_x()
		<< "\" y2=\"" << to_.get_y()
		<< "\" style=\"stroke:rgb(0,0,0);stroke-width:4\"/>" << endl;
	}
};

class Path : public Shape
{
	list<string> commands_;
public:
	Path(list<string> commands): commands_(commands) {}
	
    void draw() const
	{
        cout << "<path d=\"";
        for(list<string>::const_iterator it = commands_.begin(); it != commands_.end(); ++it)
        {
            cout << *it << " "; 
        }
        cout << "\" />";
	}
};

class CompositeFigure : public Shape 
{
    list<Shape*> content_;
public:
    ~CompositeFigure() 
    {
        for (list<Shape*>::iterator it = content_.begin(); it != content_.end(); it++) 
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
    Canvas(int width=100, int height=100) : width_(width), height_(height) {}
    void draw() const 
    {
        cout << "<svg width=\"" << width_
        << "\" height=\"" << height_
        << "\">" << endl;
        CompositeFigure::draw();
        cout << "</svg>" << endl;
    }
};

int main() 
{
    Canvas c(400, 600);
    c.add(new Circle(Point(270, 420), 15));
    c.add(new Circle(Point(300, 95), 50));
    c.add(new Rect(Point(240, 150), 100, 30));
	c.add(new Ellipse(Point(309, 300), 50, 100));
	c.add(new Line(Point(17,80), Point(163,250)));
    list<string> path;
    path.push_back("M 100,200 L 20,28 Z");
    c.add(new Path(path));
    c.draw();
    return 0;
}
